package com.example.practica5.application.job;
import com.example.practica5.application.model.ResumenDistritos;
import com.example.practica5.application.projection.ResumenDistritosView;
import com.example.practica5.application.repository.ResumenDistritosBatchRepository;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListener;

import java.util.ArrayList;
import java.util.List;

public class ResumenDistritosJobListener implements JobExecutionListener {
    private final ResumenDistritosBatchRepository resumenDistritosRepository;

    public ResumenDistritosJobListener(ResumenDistritosBatchRepository resumenDistritosRepository) {
        this.resumenDistritosRepository = resumenDistritosRepository;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (!jobExecution.getStatus().equals(BatchStatus.COMPLETED)) {
            return;
        }

        List<ResumenDistritosView> resultados = resumenDistritosRepository.countCallesPorDistrito();

        List<ResumenDistritos> resumenes = new ArrayList<>();

        for (ResumenDistritosView view : resultados) {
            ResumenDistritos resumen = new ResumenDistritos();
            resumen.setNombreCalle(view.getNombreCalle());
            resumen.setNumCasas(view.getNumCasas().intValue());
            resumenes.add(resumen);
        }

        resumenDistritosRepository.deleteAll();
        resumenDistritosRepository.saveAll(resumenes);
    }

}
