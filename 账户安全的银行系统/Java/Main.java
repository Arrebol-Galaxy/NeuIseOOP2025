import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final int NUM_USERS = 10;
    private static final int TRANSACTIONS_PER_USER = 20;
    private static final String HOST = "localhost";
    private static final int PORT = 7070;
    private static final String ACCOUNT_ID_A = "acc_1001";
    private static final String ACCOUNT_ID_B = "acc_1002";

    // 用户任务，模拟单个用户的行为
    public static void userTask(User user) {
        Random random = new Random();
        for (int i = 0; i < TRANSACTIONS_PER_USER; i++) {
            int operation = random.nextInt(3); // 0: 存款, 1: 取款, 2: 转账
            int amountVal = random.nextInt(100) + 1; // 1 到 100
            Money amount = new Money(amountVal);

            try {
                switch (operation) {
                    case 0:
                        // System.out.println("线程 " + Thread.currentThread().getId() + " 尝试存入 " + amountVal);
                        user.save(amount, ACCOUNT_ID_A);
                        break;
                    case 1:
                        // System.out.println("线程 " + Thread.currentThread().getId() + " 尝试取出 " + amountVal);
                        user.withdraw(amount, ACCOUNT_ID_A);
                        break;
                    case 2:
                        // System.out.println("线程 " + Thread.currentThread().getId() + " 尝试转账 " + amountVal);
                        user.transfer(amount, ACCOUNT_ID_A, ACCOUNT_ID_B);
                        break;
                }
            } catch (Exception e) {
                System.err.println("线程 " + Thread.currentThread().getId() + " 遇到异常: " + e.getMessage());
            }

            try {
                // 随机暂停一小段时间，模拟真实用户操作间隔
                Thread.sleep(random.nextInt(91) + 10); // 10 到 100 毫秒
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        Server server = null;
        try {
            server = new Server(HOST, PORT);
            server.start();

            // 创建初始账户
            server.getDatabase().createAccount(ACCOUNT_ID_A, 5000);
            server.getDatabase().createAccount(ACCOUNT_ID_B, 5000);
            System.out.println("初始账户 A: 5000, B: 5000");
            
            // 使用线程池管理用户线程
            ExecutorService executor = Executors.newFixedThreadPool(NUM_USERS);
            List<User> users = new ArrayList<>();
            for (int i = 0; i < NUM_USERS; i++) {
                users.add(new User(HOST, PORT));
            }

            System.out.println("正在启动 " + NUM_USERS + " 个用户进行并发测试...");
            for (User user : users) {
                executor.submit(() -> userTask(user));
            }

            // 关闭线程池，并等待所有任务完成
            executor.shutdown();
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                System.err.println("用户任务执行超时");
                executor.shutdownNow();
            } else {
                System.out.println("所有用户线程已完成任务。");
            }

        } catch (IOException e) {
            System.err.println("无法启动服务器: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("主线程被中断。");
        } finally {
            if (server != null) {
                // 等一小会儿，确保最后的日志能被记录
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                server.stop();
                System.out.println("服务器已停止");
            }
        }
    }
}