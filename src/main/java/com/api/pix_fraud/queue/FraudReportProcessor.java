package com.api.pix_fraud.queue;

import com.api.pix_fraud.models.FraudReport;
import com.api.pix_fraud.models.FraudReportStatus;
import com.api.pix_fraud.repositories.FraudReportRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class FraudReportProcessor {

    private final Queue<FraudReport> reportQueue = new ConcurrentLinkedQueue<>();
    private final FraudReportRepository reportRepository;

    public FraudReportProcessor(FraudReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public void enqueue(FraudReport report) {
        reportQueue.offer(report);
    }

    public Queue<FraudReport> getQueue() {
        return reportQueue;
    }

    @Async
    public void processQueue() {
        while (!reportQueue.isEmpty()) {
            FraudReport report = reportQueue.poll();
            if (report != null) {
                simulateAnalysis(report);
            }
        }
    }

    private void simulateAnalysis(FraudReport report) {
        try {
            System.out.println("Analisando relatório de fraude: " + report.getId());
            Thread.sleep(3000);

            report.setStatus(FraudReportStatus.INVESTIGATED);
            reportRepository.save(report);

            System.out.println("Relatório " + report.getId() + " processado.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
