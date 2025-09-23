import java.util.HashMap;
import java.util.Map;

public class Account {
    public String userId;
    public Money balance;
    public Map<String, String> properties;

    public Account() {
        this.properties = new HashMap<>();
    }
}