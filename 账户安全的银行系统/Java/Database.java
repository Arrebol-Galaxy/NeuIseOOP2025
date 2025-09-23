import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class Database {
    private final Map<String, SafeAccount> accounts = new HashMap<>();
    private final ReentrantLock dbMutex = new ReentrantLock();

    public boolean createAccount(String userId, int initialBalance) {
        dbMutex.lock();
        try {
            if (accounts.containsKey(userId)) {
                return false;
            }
            accounts.put(userId, new SafeAccount(userId, initialBalance));
            return true;
        } finally {
            dbMutex.unlock();
        }
    }

    public SafeAccount getAccount(String userId) {
        dbMutex.lock();
        try {
            return accounts.get(userId);
        } finally {
            dbMutex.unlock();
        }
    }
}