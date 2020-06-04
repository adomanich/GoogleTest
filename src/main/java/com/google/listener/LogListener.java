package com.google.listener;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;
import com.google.webdriver.DriverRepository;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public class LogListener implements ITestListener {

    public static Logger logger;

    @Override
    public void onStart(ITestContext context) {
        Class testClass = context.getAllTestMethods()[0].getRealClass();
        logger = (Logger) LoggerFactory.getLogger(testClass);
    }

    @Override
    public void onTestStart(ITestResult result) {
        String fileName = "target/logs/" + result.getName() + "THREAD" + Thread.currentThread().getId() + ".log";

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

        PatternLayoutEncoder patternLayout = new PatternLayoutEncoder();
        patternLayout.setPattern("%d{\"HH:mm:ss,SSS\"} %5p %C{1.}:%M:%L -\t%m%n");
        patternLayout.setContext(loggerContext);
        patternLayout.start();

        FileAppender<ILoggingEvent> fileAppender = new FileAppender<>();
        fileAppender.setFile(fileName);
        fileAppender.setEncoder(patternLayout);
        fileAppender.setContext(loggerContext);
        fileAppender.setAppend(true);
        fileAppender.start();

        Logger logbackLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logbackLogger.addAppender(fileAppender);
        logger.info("\n\n" + "*********** Test started " + result.getMethod().getMethodName() + "***********" + " \n\n");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info(("\n\n " + "*********** Test succeeded " + result.getMethod().getMethodName() + "*********** \n\n"));
        String fileName = "target/logs/" + result.getName() + "THREAD" + Thread.currentThread().getId() + ".log";
        attachTextLog(fileName);
        logger.detachAppender("allureAppender" + result.getName() + "THREAD" + Thread.currentThread().getId());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String fileName = "target/logs/" + result.getName() + "THREAD" + Thread.currentThread().getId() + ".log";

        if (result.getMethod().getRetryAnalyzer(result) != null) {
            RetryAnalyser retryAnalyser = (RetryAnalyser) result.getMethod().getRetryAnalyzer(result);
            if (retryAnalyser.isRetryAvailable()) {
                result.setAttribute("retried", true);
                result.setStatus(ITestResult.SKIP);
            } else {
                result.setStatus(ITestResult.FAILURE);
            }
        }
        Reporter.setCurrentTestResult(result);

        logger.info(("\n\n " + "*********** Test failed " + result.getMethod().getMethodName() + "***********" + " \n\n"));
        var sw = new StringWriter();
        var pw = new PrintWriter(sw);
        result.getThrowable().printStackTrace(pw);
        String stackTrace = sw.toString();
        logger.error(stackTrace);
        saveScreenshotPNG();
        attachTextLog(fileName);
        logger.detachAppender("allureAppender" + result.getName() + "THREAD" + Thread.currentThread().getId());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String fileName = "target/logs/" + result.getName() + "THREAD" + Thread.currentThread().getId() + ".log";

        logger.info(("\n\n " + "*********** Test skipped " + result.getMethod().getMethodName() + "***********" + " \n\n"));
        var sw = new StringWriter();
        var pw = new PrintWriter(sw);
        result.getThrowable().printStackTrace(pw);
        String stackTrace = sw.toString();
        logger.error(stackTrace);

        attachTextLog(fileName);
        logger.detachAppender("allureAppender" + result.getName() + "THREAD" + Thread.currentThread().getId());
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshotPNG() {
        return ((TakesScreenshot) DriverRepository.DRIVERS.get()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "log", type = "text/plain")
    public String attachTextLog(String fileName) {
        try {
            File file = new File(fileName);
            return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            LogListener.logger.error("Can not to read bytes from file");
        }
        return StringUtils.EMPTY;
    }
}
