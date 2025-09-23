import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server {
    private final HttpServer server;
    private final Database db;
    private final Log log;

    public Server(String host, int port) throws IOException {
        this.db = new Database();
        this.log = new Log("transaction_log_java.txt");
        this.server = HttpServer.create(new InetSocketAddress(host, port), 0);
        setupRoutes();
        this.server.setExecutor(Executors.newCachedThreadPool());
    }

    public void start() {
        server.start();
    }

    public void stop() {
        log.close();
        server.stop(1); // 延迟1秒关闭
    }

    public Database getDatabase() {
        return db;
    }

    private void setupRoutes() {
        server.createContext("/deposit", new DepositHandler());
        server.createContext("/withdraw", new WithdrawHandler());
        server.createContext("/transfer", new TransferHandler());
    }
    
    // --- Handlers ---
    
    class DepositHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"POST".equals(exchange.getRequestMethod())) {
                sendResponse(exchange, 405, "{\"status\":\"error\", \"message\":\"Method Not Allowed\"}");
                return;
            }
            try {
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                Map<String, String> params = parseJsonBody(body, "account", "amount");
                String accountId = params.get("account");
                int amount = Integer.parseInt(params.get("amount"));

                if (amount <= 0) {
                    sendResponse(exchange, 400, "{\"status\":\"error\", \"message\":\"存款金额必须为正数\"}");
                    return;
                }

                SafeAccount safeAccount = db.getAccount(accountId);
                if (safeAccount == null) {
                    sendResponse(exchange, 404, "{\"status\":\"error\", \"message\":\"账户未找到\"}");
                    return;
                }

                safeAccount.mtx.lock();
                try {
                    safeAccount.account.balance.add(amount);
                    log.logTransaction("存款: 账户 " + accountId + ", 金额: " + amount + ", 余额: " + safeAccount.account.balance.getBalance());
                    safeAccount.cv.signalAll();
                } finally {
                    safeAccount.mtx.unlock();
                }

                String response = String.format("{\"status\":\"success\", \"account\":\"%s\", \"new_balance\":%d}", accountId, safeAccount.account.balance.getBalance());
                sendResponse(exchange, 200, response);
            } catch (Exception e) {
                sendResponse(exchange, 400, "{\"status\":\"error\", \"message\":\"请求格式错误或处理失败\"}");
            }
        }
    }

    class WithdrawHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
             if (!"POST".equals(exchange.getRequestMethod())) {
                sendResponse(exchange, 405, "{\"status\":\"error\", \"message\":\"Method Not Allowed\"}");
                return;
            }
            try {
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                Map<String, String> params = parseJsonBody(body, "account", "amount", "wait");
                String accountId = params.get("account");
                int amount = Integer.parseInt(params.get("amount"));
                boolean waitFlag = params.containsKey("wait") ? Boolean.parseBoolean(params.get("wait")) : true;

                if (amount <= 0) {
                    sendResponse(exchange, 400, "{\"status\":\"error\", \"message\":\"取款金额必须为正数\"}");
                    return;
                }

                SafeAccount safeAccount = db.getAccount(accountId);
                if (safeAccount == null) {
                    sendResponse(exchange, 404, "{\"status\":\"error\", \"message\":\"账户未找到\"}");
                    return;
                }

                safeAccount.mtx.lock();
                try {
                    if (safeAccount.account.balance.getBalance() < amount) {
                        if (!waitFlag) {
                            sendResponse(exchange, 400, "{\"status\":\"failure\", \"message\":\"余额不足且客户端选择不等待\"}");
                            log.logTransaction("取款失败: 账户 " + accountId + ", 金额: " + amount + ", 原因: 余额不足 (不等待)");
                            return;
                        }
                        log.logTransaction("取款等待: 账户 " + accountId + ", 金额: " + amount + ", 原因: 余额不足，正在等待...");
                        while (safeAccount.account.balance.getBalance() < amount) {
                            safeAccount.cv.await();
                        }
                    }

                    safeAccount.account.balance.add(-amount);
                    log.logTransaction("取款成功: 账户 " + accountId + ", 金额: " + amount + ", 余额: " + safeAccount.account.balance.getBalance());
                } finally {
                    safeAccount.mtx.unlock();
                }

                String response = String.format("{\"status\":\"success\", \"account\":\"%s\", \"new_balance\":%d}", accountId, safeAccount.account.balance.getBalance());
                sendResponse(exchange, 200, response);

            } catch (Exception e) {
                Thread.currentThread().interrupt();
                sendResponse(exchange, 400, "{\"status\":\"error\", \"message\":\"请求格式错误或处理失败\"}");
            }
        }
    }

    class TransferHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"POST".equals(exchange.getRequestMethod())) {
                sendResponse(exchange, 405, "{\"status\":\"error\", \"message\":\"Method Not Allowed\"}");
                return;
            }
            try {
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                Map<String, String> params = parseJsonBody(body, "from", "to", "amount");
                String fromId = params.get("from");
                String toId = params.get("to");
                int amount = Integer.parseInt(params.get("amount"));

                if (amount <= 0 || fromId.equals(toId)) {
                    sendResponse(exchange, 400, "{\"status\":\"error\", \"message\":\"转账金额或账户无效\"}");
                    return;
                }

                SafeAccount fromAccount = db.getAccount(fromId);
                SafeAccount toAccount = db.getAccount(toId);

                if (fromAccount == null || toAccount == null) {
                    sendResponse(exchange, 404, "{\"status\":\"error\", \"message\":\"一个或两个账户都未找到\"}");
                    return;
                }

                ReentrantLock firstLock = fromId.compareTo(toId) < 0 ? fromAccount.mtx : toAccount.mtx;
                ReentrantLock secondLock = fromId.compareTo(toId) < 0 ? toAccount.mtx : fromAccount.mtx;
                
                firstLock.lock();
                secondLock.lock();
                try {
                    if (fromAccount.account.balance.getBalance() < amount) {
                        sendResponse(exchange, 400, "{\"status\":\"error\", \"message\":\"转账余额不足\"}");
                        log.logTransaction("转账失败: 从 " + fromId + " 到 " + toId + ", 金额: " + amount + ", 原因: 余额不足");
                        return;
                    }

                    fromAccount.account.balance.add(-amount);
                    toAccount.account.balance.add(amount);

                    log.logTransaction("转账成功: 从 " + fromId + " 到 " + toId + ", 金额: " + amount);
                    toAccount.cv.signalAll();

                    String response = String.format("{\"status\":\"success\",\"from_account\":{\"id\":\"%s\",\"new_balance\":%d},\"to_account\":{\"id\":\"%s\",\"new_balance\":%d}}",
                        fromId, fromAccount.account.balance.getBalance(), toId, toAccount.account.balance.getBalance());
                    sendResponse(exchange, 200, response);
                } finally {
                    secondLock.unlock();
                    firstLock.unlock();
                }

            } catch (Exception e) {
                sendResponse(exchange, 400, "{\"status\":\"error\", \"message\":\"请求格式错误或处理失败\"}");
            }
        }
    }
    
    // --- 工具方法 ---

    private static void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, response.getBytes(StandardCharsets.UTF_8).length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }

    private static Map<String, String> parseJsonBody(String body, String... keys) {
        Map<String, String> result = new HashMap<>();
        for (String key : keys) {
            Pattern pattern = Pattern.compile("\"" + key + "\"\\s*:\\s*\"?(\\w+)\"?");
            Matcher matcher = pattern.matcher(body);
            if (matcher.find()) {
                result.put(key, matcher.group(1));
            }
        }
        return result;
    }
}