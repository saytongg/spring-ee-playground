package com.saytongg.shared.database;

import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
public class DatabaseConfiguration {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	// Datasource properties
    @Value("${datasource.driver-class-name}")
    private String datasourceDriverClass;

    @Value("${datasource.url}")
    private String datasourceUrl;
    
    @Value("${datasource.username}")
    private String datasourceUsername;
    
    @Value("${datasource.password}")
    private String datasourcePassword;

    @Value("${datasource.max-total}")
    private int datasourceMaxTotal;


    // Hibernate properties
    @Value("${hibernate.dialect}")
    private String hibernateDialect;

    @Value("${hibernate.hbm2ddl.auto}")
    private String hibernateDDL;

    @Value("${hibernate.autocommit}")
    private String hibernateAutocommit;

    @Value("${hibernate.show_sql}")
    private String hibernateShowSql;
    
    @Value("${hibernate.generate_statistics}")
    private String hibernateGenerateStatistics;
    
    
    @Value("${hibernate.session.context.class}")
    private String hibernateSessionContextClass;

    @Value("${hibernate.transaction.factory}")
    private String hibernateTransactionFactory;

    @Value("${hibernate.transaction.coordinator_class}")
    private String hibernateTransactionCoordinator;
    

    @Value("${hibernate.cache.provider}")
    private String hibernateCacheProvider;

    @Value("${hibernate.cache.use_second_level_cache}")
    private String hibernateSecondLevelCache;

    @Value("${hibernate.cache.use_structured_entries}")
    private String hibernateStructuredEntries;

    @Value("${hibernate.validator.apply_to_ddl}")
    private String hibernateValidatorApplyToDDL;

    
    private final Properties hibernateProperties() {
        final Properties hibernateProperties = new Properties();

        hibernateProperties.setProperty("hibernate.dialect", hibernateDialect);
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", hibernateDDL);
        hibernateProperties.setProperty("hibernate.autocommit", hibernateAutocommit);
        hibernateProperties.setProperty("hibernate.show_sql", hibernateShowSql);
        hibernateProperties.setProperty("hibernate.generate_statistics", hibernateGenerateStatistics);

        hibernateProperties.setProperty("hibernate.cache.provider", hibernateCacheProvider);
        hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", hibernateSecondLevelCache);
        hibernateProperties.setProperty("hibernate.cache.use_structured_entries", hibernateStructuredEntries);

        hibernateProperties.setProperty("hibernate.session.context.class", hibernateSessionContextClass);
        hibernateProperties.setProperty("hibernate.transaction.factory", hibernateTransactionFactory);
        hibernateProperties.setProperty("hibernate.transaction.coordinator", hibernateTransactionCoordinator);

        hibernateProperties.setProperty("hibernate.validator.apply_to_ddl", hibernateValidatorApplyToDDL);

        return hibernateProperties;
    }

	@Bean
	public DataSource dataSource() {
		final HikariConfig config = new HikariConfig();
		config.setDriverClassName(datasourceDriverClass);
		config.setJdbcUrl(datasourceUrl);
		config.setUsername(datasourceUsername);
		config.setPassword(datasourcePassword);
		config.setMaximumPoolSize(datasourceMaxTotal);

		final HikariDataSource dataSource = new HikariDataSource(config);

		return dataSource;
	}
	
	@Bean
    public LocalSessionFactoryBean sessionFactory() {
        final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] { "com.saytongg" });
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

	@Bean
	public PlatformTransactionManager transactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());

		return transactionManager;
	}
}
