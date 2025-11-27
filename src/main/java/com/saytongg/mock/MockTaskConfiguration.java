package com.saytongg.mock;

import java.util.Properties;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class MockTaskConfiguration {

	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@Value("${quartz.mock.task.cron}")
	private String cronExpression;
	
	private final String prefix = "mock";
	
	private final String transactionTemplateName = prefix + "TaskTransactionTemplate";
	private final String taskBeanName = prefix + "Task";
	private final String jobName = prefix + "TaskJob";
	private final String triggerName = prefix + "TaskTrigger";
	private final String schedulerName = prefix + "TaskScheduler";
	

	@Bean(name = transactionTemplateName)
	public TransactionTemplate taskTransactionTemplate() {
		TransactionTemplate transactionTemplate = new TransactionTemplate();
		transactionTemplate.setTransactionManager(transactionManager);
		transactionTemplate.setPropagationBehaviorName("PROPAGATION_REQUIRED");
		transactionTemplate.setReadOnly(false);

		return transactionTemplate;
	}
	
	@Bean(name = jobName)
	public MethodInvokingJobDetailFactoryBean taskJob() {
		MethodInvokingJobDetailFactoryBean jobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
		jobDetailFactoryBean.setName(jobName);
		jobDetailFactoryBean.setTargetBeanName(taskBeanName);
		jobDetailFactoryBean.setTargetMethod("execute");
		jobDetailFactoryBean.setConcurrent(false);

		return jobDetailFactoryBean;
	}

	@Bean(name = triggerName)
	public CronTriggerFactoryBean taskTrigger(@Qualifier(jobName) JobDetail jobDetail) throws Exception {
		// CronExpression cron = new CronExpression(cronExpression);
		
		CronTriggerFactoryBean triggerFactoryBean = new CronTriggerFactoryBean();
		triggerFactoryBean.setName(triggerName);
		// triggerFactoryBean.setJobName(jobName);
		triggerFactoryBean.setJobDetail(jobDetail);
		triggerFactoryBean.setCronExpression(cronExpression);

		return triggerFactoryBean;
	}

	@Bean(name = schedulerName, destroyMethod = "destroy")
	public SchedulerFactoryBean taskScheduler(@Qualifier(triggerName) Trigger trigger) throws Exception {
		final Properties quartzProperties = new Properties();
		quartzProperties.setProperty("org.quartz.threadPool.threadCount", "5");

		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		schedulerFactoryBean.setSchedulerName(schedulerName);
		schedulerFactoryBean.setAutoStartup(true);
		schedulerFactoryBean.setStartupDelay(10);
		schedulerFactoryBean.setTriggers(new Trigger[] { trigger });
		schedulerFactoryBean.setQuartzProperties(quartzProperties);
		schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(true);

		return schedulerFactoryBean;
	}
}
