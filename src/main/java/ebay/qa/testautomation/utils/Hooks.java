package ebay.qa.testautomation.utils;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Hooks {
    public static WebDriver driver = null;

    @Before
    public void initDriver(Scenario scenario) {
        try {
            DesiredCapabilities cap;
            cap = new DesiredCapabilities();
            Properties props = new Properties();
            props.load(Hooks.class.getClassLoader().getResourceAsStream("project.properties"));
            String browser = props.getProperty("browserName");
            if ((browser.equals(null))||(browser.isEmpty()||browser.equals("${browserName}"))) {
                browser="chrome";
            }
                if ("chrome".equals(browser)) {
                    Class<? extends WebDriver> driverClass = ChromeDriver.class;
                    ChromeDriverManager.getInstance(driverClass).setup();
                    driver = new ChromeDriver(cap);
                    driver.manage().window().maximize();
                } else if ("ie".equals(browser)) {
                    System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "\\jars\\IEDriverServer.exe");
                    cap.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
                    cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                    driver = new InternetExplorerDriver(cap);
                } else if ("firefox".equals(browser)) {
                    System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\jars\\geckodriver.exe");
                    driver = new FirefoxDriver(cap);
                }

                driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
                driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void TearDown() {
        try {
            if (!(driver == null)) {
                driver.close();
                driver.quit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
