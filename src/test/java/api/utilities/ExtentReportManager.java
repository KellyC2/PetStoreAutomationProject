package api.utilities;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.junit.jupiter.api.extension.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager implements
        BeforeAllCallback,
        AfterAllCallback,
        TestWatcher {

    private static ExtentReports extent;
    private static ExtentTest currentTest;

    @Override
    public void beforeAll(ExtensionContext context) {
        if (extent != null) return;

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportPath = "reports/TestExecution_" + timestamp + ".html";

        ExtentSparkReporter spark = new ExtentSparkReporter(new File(reportPath));
        spark.config().setDocumentTitle("API Automation Report");
        spark.config().setReportName("PetStore Automation");
        spark.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(spark);

        extent.setSystemInfo("Project", "PetStoreAutomation");
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java", System.getProperty("java.version"));
        extent.setSystemInfo("Executed By", System.getProperty("user.name"));
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        createTest(context).pass("Test Passed");
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        createTest(context).fail(cause);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        createTest(context).skip("Test Aborted");
    }

    @Override
    public void testDisabled(ExtensionContext context, java.util.Optional<String> reason) {
        createTest(context).skip("Test Disabled");
    }

    @Override
    public void afterAll(ExtensionContext context) {
        if (extent != null) {
            extent.flush();
            extent = null;
        }
    }

    private ExtentTest createTest(ExtensionContext context) {
        String className = context.getRequiredTestClass().getSimpleName();
        String methodName = context.getDisplayName();

        ExtentTest test = extent.createTest(className + " :: " + methodName);

        context.getTags().forEach(test::assignCategory);

        return test;
    }
}
