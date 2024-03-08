package ru.javawebinar.topjava;

import org.junit.AssumptionViolatedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class MyStopWatch extends Stopwatch {
    private static final HashMap<String, Long> testsMap = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(MyStopWatch.class);

    public void logInfo(Description description, String status, long nanos) {
        String testName = description.getMethodName();
        log.info("{}: {} - {} ms", testName, status, nanos / 1000000);
    }

    @Override
    protected void succeeded(long nanos, Description description) {
        logInfo(description, "succeeded", nanos);
        testsMap.put(description.getMethodName(), nanos/ 1000000);
    }

    @Override
    protected void failed(long nanos, Throwable e, Description description) {
        logInfo(description, "failed", nanos);
        testsMap.put(description.getMethodName(), nanos / 1000000);

    }

    @Override
    protected void skipped(long nanos, AssumptionViolatedException e, Description description) {
        logInfo(description, "skipped", nanos);
        testsMap.put(description.getMethodName(), nanos / 1000000);

    }

    @Override
    protected void finished(long nanos, Description description) {
        logInfo(description, "finished", nanos);
        testsMap.put(description.getMethodName(), nanos / 1000000);
    }

    public static String result(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Long> entry : testsMap.entrySet()) {
            stringBuilder.append(entry.getKey());
            stringBuilder.append(" - ");
            stringBuilder.append(entry.getValue());
            stringBuilder.append(" ms\n");
        }
        return stringBuilder.toString();
    }
}
