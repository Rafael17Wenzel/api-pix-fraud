package com.api.pix_fraud.queue;

import com.api.pix_fraud.models.FraudReport;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class FraudReportProcessor {

    private final Queue<FraudReport> reportQueue = new ConcurrentLinkedQueue<>();

    public FraudReportProcessor() {
    }

    public void enqueue(FraudReport report) {
        reportQueue.offer(report);
    }

    public Queue<FraudReport> getQueue() {
        return reportQueue;
    }

    @Async
    public void processQueue() {
        reportQueue.poll();
    }
}
