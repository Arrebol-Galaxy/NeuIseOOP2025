import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class User {
    private final HttpClient client;
    private final String serverUrl;

    public User(String host, int port) {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
        this.serverUrl = "http://" + host + ":" + port;
    }

    public void save(Money amount, String account) {
        String json = String.format("{\"account\":\"%s\", \"amount\":%d}", account, amount.getBalance());
        sendRequest("/deposit", json, "Save");
    }

    public void withdraw(Money amount, String account) {
        String json = String.format("{\"account\":\"%s\", \"amount\":%d}", account, amount.getBalance());
        sendRequest("/withdraw", json, "Withdraw");
    }

    public void transfer(Money amount, String accountOut, String accountIn) {
        String json = String.format("{\"from\":\"%s\", \"to\":\"%s\", \"amount\":%d}", accountOut, accountIn, amount.getBalance());
        sendRequest("/transfer", json, "Transfer");
    }

    private void sendRequest(String path, String jsonBody, String operation) {
         HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + path))
                .timeout(Duration.ofSeconds(30))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
              .thenAccept(res -> handleResponse(res, operation));
    }
    
    private void handleResponse(HttpResponse<String> res, String operation) {
        if (res.statusCode() == 200) {
            System.out.println(operation + " 操作成功。响应: " + res.body());
        } else {
            System.err.println("线程 " + Thread.currentThread().getId() + ": " + operation + " 操作失败。状态码: " + res.statusCode() + ", 响应体: " + res.body());
        }
    }
}