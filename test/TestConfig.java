import java.io.IOException;
import java.util.Properties;

public class TestConfig {
    public static void main(String[] args) {
        Properties pros = new Properties();
        try {
            pros.load(TestConfig.class.getClassLoader().
                    getResourceAsStream("config"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String initTankCount = (String)pros.get("initTankCount");
        System.out.println(initTankCount);
    }
}
