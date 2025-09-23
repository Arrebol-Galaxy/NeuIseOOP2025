import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.locks.ReentrantLock;

public class Log {
    private FileWriter logFile;
    private final ReentrantLock mtx = new ReentrantLock();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").withZone(ZoneId.systemDefault());

    public Log(String filename) {
        try {
            this.logFile = new FileWriter(filename, true);
        } catch (IOException e) {
            System.err.println("无法打开日志文件: " + filename);
            this.logFile = null;
        }
    }

    public void logTransaction(String message) {
        mtx.lock();
        try {
            if (logFile != null) {
                String timestamp = FORMATTER.format(Instant.now());
                logFile.write("[" + timestamp + "] " + message + System.lineSeparator());
                logFile.flush();
            }
        } catch (IOException e) {
            System.err.println("写入日志时出错: " + e.getMessage());
        } finally {
            mtx.unlock();
        }
    }

    public void close() {
        mtx.lock();
        try {
            if (logFile != null) {
                logFile.close();
            }
        } catch (IOException e) {
            System.err.println("关闭日志文件时出错: " + e.getMessage());
        } finally {
            mtx.unlock();
        }
    }
}