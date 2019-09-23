package kr.co.uclick.configuration;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ignite.cache.hibernate.HibernateRegionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.MySQL5Dialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration // 설정파일 클래스임을 명시
@ImportResource(locations = "classpath:applicationContext-ignite.xml") // xml파일 import
@ComponentScan({ "kr.co.uclick.service" }) // Scan할 대상 설정
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ) // 한 클래스 내에서 다수의 Transaction을 사용할 수 있도록 설정
@EnableSpringConfigured // Spring 설정을 주입받도록 함
// 다수의 data modules(user, phone)을 사용하기 위해 선언
@EnableJpaRepositories(basePackages = "kr.co.uclick.repository")
@PropertySource({"classpath:databaseConnection.properties"})
// root-Context.xml과 같은 역할
public class SpringConfiguration {
	
	@Autowired
	private Environment env; // properties에 저장된 내용을 불러올 수 있는 Environment
	
	@Bean // bean으로 등록하기 위한 annotation
	@Primary // 다른 작업을 하기 전에 우선적으로 실행한다는 의미
	// DB connection을 위한 설정을 하는 메소드
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName")); // DB 연결 driver 설정
		dataSource.setUrl(env.getProperty("jdbc.url")); // 연결할 DB 주소
		dataSource.setUsername(env.getProperty("jdbc.user")); // DB 접속용 ID
		dataSource.setPassword(env.getProperty("jdbc.pass")); // DB 접속용 PW
		return dataSource; // connection용 dataSource return
	}

	@Bean
	@DependsOn("igniteSystem") // 이 bean이 등록될 때 igniteSystem이 빈으로 등록된 후에 등록되도록 설정
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		// entityManegerFactory를 생성하는 FactoryBean
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource()); // factoryBean에 dataSource 설정
		em.setPackagesToScan("kr.co.uclick.entity"); // factoryBean이 스캔할 Domain 설정
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter(); // JPA-Hibernate간의 Adapter
		em.setJpaVendorAdapter(vendorAdapter); // sessionFactory로 hibernate Adapter 사용
		em.setJpaProperties(additionalProperties()); // JPA 규약에 쓰일 속성 설정
		return em;
	}

	// Spring에서 Transaction을 관리하기 위해 실행하는 메소드
	@Bean
	@Primary
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf); // entityManagerFactory로 위에서 생성한 bean 객체 사용
		return transactionManager;
	}

	// Repository 클래스들에 대해서 자동으로 예외를 Spring의 DataAccessException으로 변환해주는 메소드
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
	

	// hibernate 세부 설정
	public Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty(AvailableSettings.HBM2DDL_AUTO, env.getProperty("hibernate.hbm2ddl.auto")); // Domain 변경 시 기존 테이블을 update하도록 설정
		properties.setProperty(AvailableSettings.FORMAT_SQL, env.getProperty("hibernate.format_sql")); // SQL 정렬하기
		properties.setProperty(AvailableSettings.SHOW_SQL, env.getProperty("hibernate.show_sql")); // SQL 보여주기
		properties.setProperty(AvailableSettings.DIALECT, env.getProperty("hibernate.dialect")); // 여러 RDBMS와 호환이 가능하도록 방언 설정
		
		properties.setProperty(AvailableSettings.STATEMENT_BATCH_SIZE, env.getProperty("hibernate.statement_batch_size")); // JDBC batch size를 1000으로 설정

		properties.setProperty(AvailableSettings.USE_SECOND_LEVEL_CACHE, env.getProperty("hibernate.use_second_level_cache")); // L2 Cache를 사용하도록 설정
		properties.setProperty(AvailableSettings.USE_QUERY_CACHE, env.getProperty("hibernate.use_query_cache")); // Query Cache를 사용하도록 설정
		properties.setProperty(AvailableSettings.GENERATE_STATISTICS, env.getProperty("hibernate.generate_statistics")); // 통계 Collection을 사용하도록 설정
		properties.setProperty(AvailableSettings.CACHE_REGION_FACTORY, env.getProperty("hibernate.cache_region_factory")); // L2 Cache를 사용할 region 설정

		properties.setProperty("org.apache.ignite.hibernate.ignite_instance_name", env.getProperty("hibernate.ignite_instance_name")); // ignite 이름을 cafe-grid로 지정
		properties.setProperty("org.apache.ignite.hibernate.default_access_type", env.getProperty("hibernate.default_access_type")); // L2 Cache Access 권한을 read, write 모두 줌

		properties.setProperty(AvailableSettings.PHYSICAL_NAMING_STRATEGY,env.getProperty("hibernate.physical_naming_strategy")); // 이름 규칙을 CustomPhysicalNamingStrategyStandardImpl으로 함
		
		return properties;
	}

}
