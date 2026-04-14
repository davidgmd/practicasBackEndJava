package com.example.practica5.application.config;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class JobRunnerConfig {

    @Bean
    public CommandLineRunner runJobs(
            JobOperator jobOperator,
            @Qualifier("jobImportarDB") Job jobImportarDB,
            @Qualifier("jobExportarCSV") Job jobExportarCSV,
            @Qualifier("jobSingleThread") Job jobSingleThread,
            @Qualifier("jobMultiThread") Job jobMultiThread,
            @Qualifier("jobParallelSteps") Job jobParallelSteps){

        return args -> {
            if (args.length == 0) {
                throw new IllegalArgumentException("""
                        Debes indicar un comando.
                        Ejemplos:
                        jobImportarDB ESTE
                        jobExportarCSV
                        singleThread
                        multiThread
                        parallelThread
                        """);
            }

            String comando = args[0];

            if ("jobImportarDB".equals(comando)) {
                if (args.length < 2) {
                    throw new IllegalArgumentException("Falta el distrito. Ejemplo: jobImportarDB ESTE");
                }

                String distrito = args[1];

                var params = new JobParametersBuilder()
                        .addString("distrito", distrito)
                        .addLong("timestamp", System.currentTimeMillis())
                        .toJobParameters();

                jobOperator.start(jobImportarDB, params);
                return;
            }

            if ("jobExportarCSV".equals(comando)) {
                if (args.length < 1) {
                    throw new IllegalArgumentException("Falta el parametro jobExportarCSV");
                }

                var params = new JobParametersBuilder()
                        .addLong("timestamp", System.currentTimeMillis())
                        .toJobParameters();

                jobOperator.start(jobExportarCSV, params);
                return;
            }

            if ("singleThread".equals(comando)) {
                var params = new JobParametersBuilder()
                        .addLong("timestamp", System.currentTimeMillis())
                        .toJobParameters();

                jobOperator.start(jobSingleThread, params);
                return;
            }

            if ("multiThread".equals(comando)) {
                var params = new JobParametersBuilder()
                        .addLong("timestamp", System.currentTimeMillis())
                        .toJobParameters();

                jobOperator.start(jobMultiThread, params);
                return;
            }

            if("parallelThread".equals(comando)) {
                var params = new JobParametersBuilder()
                        .addLong("timestamp", System.currentTimeMillis())
                        .toJobParameters();

                jobOperator.start(jobParallelSteps, params);
                return;
            }

            throw new IllegalArgumentException("Comando no reconocido: " + comando);
        };
    }
}
