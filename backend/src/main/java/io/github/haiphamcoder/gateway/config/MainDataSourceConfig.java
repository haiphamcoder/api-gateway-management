package io.github.haiphamcoder.gateway.config;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * MainDataSourceConfig class to configure the main data source
 * 
 * @author Hai Pham Ngoc
 * @version 1.0.0
 * @since 1.0.0
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "io.github.haiphamcoder.gateway.layer.infrastructure.persistence.mysql.repository.jpa", entityManagerFactoryRef = "mainEntityManagerFactory", transactionManagerRef = "mainTransactionManager")
public class MainDataSourceConfig {

    /**
     * DataSourceProperties bean to configure the main data source
     * 
     * @return DataSourceProperties bean to configure the main data source
     */
    @Bean(name = "mainDataSourceProperties")
    @ConfigurationProperties(prefix = "datasource.main")
    @Primary
    DataSourceProperties mainDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * DataSource bean to configure the main data source
     * 
     * @param mainDataSourceProperties DataSourceProperties bean to configure the
     *                                 main data source
     * @param poolSize                 The maximum number of connections that can
     *                                 be established with the database
     * @param poolName                 The name of the pool
     * @return DataSource bean to configure the main data source
     */
    @Bean(name = "mainDataSource")
    @Primary
    DataSource mainDataSource(@Qualifier("mainDataSourceProperties") DataSourceProperties mainDataSourceProperties,
            @Value("${datasource.main.pool.size}") int poolSize,
            @Value("${datasource.main.pool.name}") String poolName) {
        HikariDataSource dataSource = mainDataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
        dataSource.setMaximumPoolSize(poolSize);
        dataSource.setPoolName(poolName);
        return dataSource;
    }

    /**
     * EntityManagerFactory bean to configure the main data source
     * 
     * @param mainDataSource              DataSource bean to configure the main data
     *                                    source
     * @param entityManagerFactoryBuilder EntityManagerFactoryBuilder bean to
     *                                    configure the main data source
     * @return EntityManagerFactory bean to configure the main data source
     */
    @Bean(name = "mainEntityManagerFactory")
    @Primary
    LocalContainerEntityManagerFactoryBean mainEntityManagerFactory(
            @Qualifier("mainDataSource") DataSource mainDataSource,
            EntityManagerFactoryBuilder entityManagerFactoryBuilder) {
        return entityManagerFactoryBuilder.dataSource(mainDataSource)
                .packages("io.github.haiphamcoder.gateway.layer.infrastructure.persistence.mysql.entity")
                .persistenceUnit("main")
                .build();
    }

    /**
     * TransactionManager bean to configure the main data source
     * 
     * @param mainEntityManagerFactory EntityManagerFactory bean to configure the
     *                                 main data source
     * @return TransactionManager bean to configure the main data source
     */
    @Bean(name = "mainTransactionManager")
    @Primary
    PlatformTransactionManager mainTransactionManager(
            @Qualifier("mainEntityManagerFactory") LocalContainerEntityManagerFactoryBean mainEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(mainEntityManagerFactory.getObject());
        return transactionManager;
    }

}
