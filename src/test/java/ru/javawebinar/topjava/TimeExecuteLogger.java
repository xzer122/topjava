package ru.javawebinar.topjava;

import org.junit.AssumptionViolatedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TimeExecuteLogger extends Stopwatch {
    private static final HashMap<String, Long> testsMap = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(TimeExecuteLogger.class);

    @Override
    protected void succeeded(long nanos, Description description) {
        processStatus(description, "succeeded", nanos);
    }

    @Override
    protected void failed(long nanos, Throwable e, Description description) {
        processStatus(description, "failed", nanos);
    }

    @Override
    protected void skipped(long nanos, AssumptionViolatedException e, Description description) {
        processStatus(description, "skipped", nanos);
    }
    
    private void processStatus(Description description, String status, long nanos) {
        String testName = description.getMethodName();
        log.info("{}: {} - {} ms", testName, status, TimeUnit.NANOSECONDS.toMillis(nanos));
        testsMap.put(testName, TimeUnit.NANOSECONDS.toMillis(nanos));
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
