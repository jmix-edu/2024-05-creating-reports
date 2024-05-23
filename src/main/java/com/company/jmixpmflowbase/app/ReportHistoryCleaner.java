package com.company.jmixpmflowbase.app;

import io.jmix.reports.ReportExecutionHistoryRecorder;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
public class ReportHistoryCleaner implements Job {

    private final ReportExecutionHistoryRecorder reportExecutionHistoryRecorder;

    public ReportHistoryCleaner(ReportExecutionHistoryRecorder reportExecutionHistoryRecorder) {
        this.reportExecutionHistoryRecorder = reportExecutionHistoryRecorder;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        reportExecutionHistoryRecorder.cleanupHistory();
    }
}