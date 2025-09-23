import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SafeAccount {
    public final Account account;
    public final ReentrantLock mtx;
    public final Condition cv;

    public SafeAccount(String userId, int initialBalance) {
        this.account = new Account();
        this.account.userId = userId;
        this.account.balance = new Money(initialBalance);
        this.mtx = new ReentrantLock();
        this.cv = this.mtx.newCondition();
    }
}