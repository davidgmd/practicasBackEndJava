package com.example.practica5.application.job;

import com.example.practica5.application.model.ResumenBatch;
import com.example.practica5.application.repository.CalleRepository;
import com.example.practica5.application.repository.ResumenBatchRepository;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListener;

import java.time.LocalDateTime;

public class ResumenBatchJobListener implements JobExecutionListener {

    private final CalleRepository calleRepository;
    private final ResumenBatchRepository resumenBatchRepository;

    public ResumenBatchJobListener(CalleRepository calleRepository,
                                   ResumenBatchRepository resumenBatchRepository) {
        this.calleRepository = calleRepository;
        this.resumenBatchRepository = resumenBatchRepository;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String distrito = jobExecution.getJobParameters().getString("distrito");

        ResumenBatch resumen = new ResumenBatch();
        resumen.setFiltroUsado(distrito);
        resumen.setNumRegistrosGuardados((int) calleRepository.count());
        resumen.setEstadoBatch(jobExecution.getStatus().toString());
        resumen.setTimestampOperacion(LocalDateTime.now());

        resumenBatchRepository.save(resumen);
    }
}
