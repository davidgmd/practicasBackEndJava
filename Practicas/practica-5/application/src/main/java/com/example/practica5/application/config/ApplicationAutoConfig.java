package com.example.practica5.application.config;

import com.example.practica5.application.job.JobTimeListener;
import com.example.practica5.application.job.ResumenBatchJobListener;
import com.example.practica5.application.job.ResumenDistritosJobListener;
import com.example.practica5.application.model.Calle;
import com.example.practica5.application.model.CalleCsv;
import com.example.practica5.application.model.ResumenDistritos;
import com.example.practica5.application.repository.CalleRepository;
import com.example.practica5.application.repository.ResumenBatchRepository;
import com.example.practica5.application.repository.ResumenDistritosBatchRepository;
import com.example.practica5.application.step.CalleCsvToCalleProcessor;
import com.example.practica5.application.step.CalleItemProcessor;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.data.RepositoryItemReader;
import org.springframework.batch.infrastructure.item.database.JpaItemWriter;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.FlatFileItemWriter;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.infrastructure.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.infrastructure.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.infrastructure.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.infrastructure.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.infrastructure.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.infrastructure.item.support.SynchronizedItemStreamReader;
import org.springframework.batch.infrastructure.item.support.builder.SynchronizedItemStreamReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.WritableResource;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.data.domain.Sort;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import java.util.HashMap;
import java.util.List;

@Configuration
@EnableAutoConfiguration
public class ApplicationAutoConfig {
    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public FlatFileItemReader<CalleCsv> reader() {
        return new FlatFileItemReaderBuilder<CalleCsv>()
                .name("calleItemReader")
                .resource(new ClassPathResource("tramos_calle_BarrioDismuni.csv"))
                .delimited()
                .names(
                        "codigoCalle",
                        "tipoVia",
                        "nombreCalle",
                        "primerNumTramo",
                        "ultimoNumTramo",
                        "barrio",
                        "codDistrito",
                        "nomDistrito"
                )
                .targetType(CalleCsv.class)
                .linesToSkip(1)
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<CalleCsv> bigReader() {
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setQuoteCharacter('"');
        tokenizer.setNames(
                "codigoCalle",
                "tipoVia",
                "nombreCalle",
                "primerNumTramo",
                "ultimoNumTramo",
                "barrio",
                "codDistrito",
                "nomDistrito"
        );

        BeanWrapperFieldSetMapper<CalleCsv> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(CalleCsv.class);

        DefaultLineMapper<CalleCsv> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return new FlatFileItemReaderBuilder<CalleCsv>()
                .name("bigCalleItemReader")
                .resource(new ClassPathResource("tramos_calle_big.csv"))
                .lineMapper(lineMapper)
                .linesToSkip(1)
                .build();
    }

    @Bean
    public CalleCsvToCalleProcessor bigFileProcessor() {
        return new CalleCsvToCalleProcessor();
    }

    @Bean
    public AsyncTaskExecutor batchTaskExecutor() {
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor("multi-thread-step-");
        executor.setConcurrencyLimit(4);
        return executor;
    }

    @Bean
    public RepositoryItemReader<Calle> databaseReader(
            CalleRepository calleRepository){

        HashMap<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("id", Sort.Direction.ASC);

        RepositoryItemReader<Calle> reader = new RepositoryItemReader<>(calleRepository, sorts);
        reader.setMethodName("findAll");
        return reader;
    }

    @Bean
    public RepositoryItemReader<ResumenDistritos> distritosDatabaseReader(ResumenDistritosBatchRepository resumenDistritosRepository){
        HashMap<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("id", Sort.Direction.ASC);

        RepositoryItemReader<ResumenDistritos> reader = new RepositoryItemReader<>(resumenDistritosRepository,sorts);
        reader.setMethodName("findAll");
        return reader;
    }

    @Bean
    @StepScope
    public CalleItemProcessor processor(@Value("#{jobParameters['distrito']}") String distrito) {
        return new CalleItemProcessor(distrito);
    }

    @Bean
    public JpaItemWriter<Calle> writer(EntityManagerFactory entityManagerFactory) {
        return new JpaItemWriter<>(entityManagerFactory);
    }

    @Bean
    public FlatFileItemWriter<Calle> csvWriter(){
        WritableResource resource = new FileSystemResource("tramosDb.csv");
        String[] columns = new String[]{
                "codigoCalle",
                "tipoVia",
                "nombreCalle",
                "primerNumTramo",
                "ultimoNumTramo",
                "barrio",
                "codDistrito",
                "nomDistrito"
        };

        BeanWrapperFieldExtractor<Calle> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(columns);

        DelimitedLineAggregator<Calle> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");
        lineAggregator.setFieldExtractor(fieldExtractor);

        FlatFileItemWriter<Calle> writer = new FlatFileItemWriter<>(resource, lineAggregator);
        writer.setHeaderCallback(headerWriter -> headerWriter.write("codigoCalle, " +
                "tipoVia, " +
                "nombreCalle, " +
                "primerNumTramo," +
                "ultimoNumTramo, " +
                "barrio, " +
                "codDistrito, " +
                "nomDistrito"));
        return writer;
    }

    @Bean
    public FlatFileItemWriter<ResumenDistritos> distritosCsvWriter(){
        WritableResource resource = new FileSystemResource("Distritos.csv");

        BeanWrapperFieldExtractor<ResumenDistritos> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[]{
                "id",
                "nombreCalle",
                "numCasas"
        });

        DelimitedLineAggregator<ResumenDistritos> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");
        lineAggregator.setFieldExtractor(fieldExtractor);

        FlatFileItemWriter<ResumenDistritos> writer = new FlatFileItemWriter<>(resource, lineAggregator);
        writer.setHeaderCallback(headerWriter -> headerWriter.write("id,nombreCalle,numCasas"));
        return writer;
    }

    @Bean("singleThreadTimeListener")
    public JobTimeListener singleThreadTimeListener() {
        return new JobTimeListener();
    }

    @Bean("multiThreadTimeListener")
    public JobTimeListener multiThreadTimeListener() {
        return new JobTimeListener();
    }

    @Bean
    public SynchronizedItemStreamReader<CalleCsv> synchronizedBigReader(FlatFileItemReader<CalleCsv> bigReader) {
        return new SynchronizedItemStreamReaderBuilder<CalleCsv>()
                .delegate(bigReader)
                .build();
    }

    @Bean("filterListener")
    public ResumenBatchJobListener resumenBatchJobListener(CalleRepository calleRepository,
                                                           ResumenBatchRepository resumenBatchRepository) {
        return new ResumenBatchJobListener(calleRepository, resumenBatchRepository);
    }
    @Bean("createDistritosListener")
    public ResumenDistritosJobListener resumenDistritosBatchJobListener(ResumenDistritosBatchRepository resumenDistritosBatchRepository){
        return new ResumenDistritosJobListener(resumenDistritosBatchRepository);
    }

    @Bean("stepImport")
    public Step stepImport(JobRepository jobRepository,
                     JpaTransactionManager transactionManager,
                     FlatFileItemReader<CalleCsv> reader,
                     CalleItemProcessor processor,
                     JpaItemWriter<Calle> writer) {

        return new StepBuilder(jobRepository)
                .<CalleCsv, Calle>chunk(100)
                .transactionManager(transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean("stepExportCalles")
    public Step stepExportCalles(JobRepository jobRepository,
                     JpaTransactionManager transactionManager,
                           RepositoryItemReader<Calle> databaseReader,
                           FlatFileItemWriter<Calle> writerCsv) {

        return new StepBuilder(jobRepository)
                .<Calle, Calle>chunk(100)
                .transactionManager(transactionManager)
                .reader(databaseReader)
                .writer(writerCsv)
                .build();
    }

    @Bean("stepExportDistritos")
    public Step stepExportDistritos(JobRepository jobRepository,
                           JpaTransactionManager transactionManager,
                           RepositoryItemReader<ResumenDistritos> distritosDatabaseReader,
                           FlatFileItemWriter<ResumenDistritos> distritosCsvWriter) {

        return new StepBuilder(jobRepository)
                .<ResumenDistritos, ResumenDistritos>chunk(100)
                .transactionManager(transactionManager)
                .reader(distritosDatabaseReader)
                .writer(distritosCsvWriter)
                .build();
    }

    @Bean("stepBigFileSingleThread")
    public Step stepBigFileSingleThread(JobRepository jobRepository,
                                        JpaTransactionManager transactionManager,
                                        FlatFileItemReader<CalleCsv> bigReader,
                                        CalleCsvToCalleProcessor bigFileProcessor,
                                        JpaItemWriter<Calle> writer) {

        return new StepBuilder(jobRepository)
                .<CalleCsv, Calle>chunk(1_000_000)
                .transactionManager(transactionManager)
                .reader(bigReader)
                .processor(bigFileProcessor)
                .writer(writer)
                .build();
    }

    @Bean("stepBigFileMultiThread")
    public Step stepBigFileMultiThread(JobRepository jobRepository,
                                       JpaTransactionManager transactionManager,
                                       SynchronizedItemStreamReader<CalleCsv> synchronizedBigReader,
                                       CalleCsvToCalleProcessor bigFileProcessor,
                                       JpaItemWriter<Calle> writer,
                                       AsyncTaskExecutor batchTaskExecutor) {

        return new StepBuilder(jobRepository)
                .<CalleCsv, Calle>chunk(1_000_000)
                .transactionManager(transactionManager)
                .reader(synchronizedBigReader)
                .processor(bigFileProcessor)
                .writer(writer)
                .taskExecutor(batchTaskExecutor)
                .build();
    }

    //Si hubiera más pasos se harían con .next(step)
    //Si quisieramos que en caso de error fuera a algún step .on("FAILED").to(errorStep)
    @Bean
    public Job jobImportarDB(JobRepository jobRepository,
                             @Qualifier("stepImport") Step stepImport,
                             @Qualifier("filterListener") ResumenBatchJobListener listener,
                             @Qualifier("createDistritosListener") ResumenDistritosJobListener listenerDistritos) {
        return new JobBuilder("jobImportarDB", jobRepository)
                .listener(listener)
                .listener(listenerDistritos)
                .start(stepImport)
                .build();
    }

    @Bean
    public Job jobExportarCSV(JobRepository jobRepository,
                              @Qualifier("stepExportCalles") Step stepExportCalles,
                              @Qualifier("stepExportDistritos") Step stepExportDistritos) {
        return new JobBuilder("jobExportarCSV", jobRepository)
                .start(stepExportCalles)
                .next(stepExportDistritos)
                .build();
    }

    @Bean
    public Job jobSingleThread(JobRepository jobRepository,
                               @Qualifier("stepBigFileSingleThread") Step stepBigFileSingleThread,
                               @Qualifier("singleThreadTimeListener") JobTimeListener timeListener) {
        return new JobBuilder("jobSingleThread", jobRepository)
                .listener(timeListener)
                .start(stepBigFileSingleThread)
                .build();
    }

    @Bean
    public Job jobMultiThread(JobRepository jobRepository,
                              @Qualifier("stepBigFileMultiThread") Step stepBigFileMultiThread,
                              @Qualifier("multiThreadTimeListener") JobTimeListener timeListener) {
        return new JobBuilder("jobMultiThread", jobRepository)
                .listener(timeListener)
                .start(stepBigFileMultiThread)
                .build();
    }
}
