import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTest {
        WebDriver driver;

        @BeforeEach
        void setUp() {
            driver = new ChromeDriver();
        }

        @AfterEach
        void tearDown() {
            driver.quit();
        }
    }
