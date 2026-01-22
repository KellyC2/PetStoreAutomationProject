package api.utilities;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.junit.jupiter.api.extension.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manager de Extent Reports para JUnit 5
 * Genera reportes separados por clase de test.
 */
public class ExtentReportManager implements
        TestWatcher,
        BeforeAllCallback,
        AfterAllCallback {

    // Cada clase tiene su propio reporte
    private static final ConcurrentHashMap<String, ExtentReports> reportsMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, ExtentTest> testsMap = new ConcurrentHashMap<>();

    private ExtentReports extentReport;
    private ExtentSparkReporter sparkReporter;
    private String className;

    @Override
    public void beforeAll(ExtensionContext context) {
        className = context.getRequiredTestClass().getSimpleName();

        // Crear el reporte solo si no existe para esta clase
        reportsMap.computeIfAbsent(className, key -> {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String reportName = key + "-Report-" + timeStamp + ".html";

            sparkReporter = new ExtentSparkReporter("./reports/" + reportName);
            sparkReporter.config().setDocumentTitle("RestAssured Automation Project");
            sparkReporter.config().setReportName(key + " API Tests");
            sparkReporter.config().setTheme(Theme.DARK);

            extentReport = new ExtentReports();
            extentReport.attachReporter(sparkReporter);

            extentReport.setSystemInfo("Application", key + " API");
            extentReport.setSystemInfo("OS", System.getProperty("os.name"));
            extentReport.setSystemInfo("User", System.getProperty("user.name"));
            extentReport.setSystemInfo("Environment", "QA");

            return extentReport;
        });

        extentReport = reportsMap.get(className);
    }

    // Se ejecuta después de todos los tests de la clase
    @Override
    public void afterAll(ExtensionContext context) {
        ExtentReports report = reportsMap.get(className);
        if (report != null) {
            report.flush();
        }
    }

    // Cada test exitoso
    @Override
    public void testSuccessful(ExtensionContext context) {
        ExtentTest test = createTest(context);
        test.pass("Test Passed");
    }

    // Cada test fallido
    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        ExtentTest test = createTest(context);
        test.fail("Test Failed");
        test.fail(cause);
    }

    // Cada test deshabilitado
    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        ExtentTest test = createTest(context);
        test.skip("Test Skipped");
        reason.ifPresent(test::skip);
    }

    // Crear o recuperar un test del reporte
    private ExtentTest createTest(ExtensionContext context) {
        String testName = context.getDisplayName(); // Puede incluir datos parametrizados
        String key = className + "-" + testName;

        // Evitar duplicados para tests parametrizados
        return testsMap.computeIfAbsent(key, k -> {
            ExtentTest test = extentReport.createTest(testName);
            context.getTags().forEach(test::assignCategory);
            return test;
        });
    }
}

