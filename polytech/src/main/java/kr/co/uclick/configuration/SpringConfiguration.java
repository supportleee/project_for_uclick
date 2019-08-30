package kr.co.uclick.configuration;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ignite.cache.hibernate.HibernateRegionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.MySQL5Dialect;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ImportResource(locations = "classpath:applicationContext-ignite.xml")
@ComponentScan({ "kr.co.uclick.service" })
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@EnableSpringConfigured
@EnableJpaRepositories(basePackages = "kr.co.uclick.repository")
public class SpringConfiguration {

	@Bean
	@Primary
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://3.13.15.154:3306/polytech");
		dataSource.setUsername("root");
		dataSource.setPassword("kopo24");
		return dataSource;
	}

	@Bean
	@DependsOn("igniteSystem")
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan("kr.co.uclick.entity");
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());
		return em;
	}

	@Bean
	@Primary
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	public Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty(AvailableSettings.HBM2DDL_AUTO, "update");
		properties.setProperty(AvailableSettings.FORMAT_SQL, Boolean.TRUE.toString());
		properties.setProperty(AvailableSettings.SHOW_SQL, Boolean.TRUE.toString());
		properties.setProperty(AvailableSettings.DIALECT, MySQL5Dialect.class.getName());

		properties.setProperty(AvailableSettings.STATEMENT_BATCH_SIZE, "1000");

		properties.setProperty(AvailableSettings.USE_SECOND_LEVEL_CACHE, Boolean.TRUE.toString());
		properties.setProperty(AvailableSettings.USE_QUERY_CACHE, Boolean.TRUE.toString());
		properties.setProperty(AvailableSettings.GENERATE_STATISTICS, Boolean.FALSE.toString());
		properties.setProperty(AvailableSettings.CACHE_REGION_FACTORY, HibernateRegionFactory.class.getName());

		properties.setProperty("org.apache.ignite.hibernate.ignite_instance_name", "cafe-grid");
		properties.setProperty("org.apache.ignite.hibernate.default_access_type", "NONSTRICT_READ_WRITE");

		properties.setProperty(AvailableSettings.PHYSICAL_NAMING_STRATEGY,
				CustomPhysicalNamingStrategyStandardImpl.class.getName());
		return properties;
	}

}
