package pac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@SpringBootApplication
@EnableTransactionManagement
@EnableCaching      //кеширование информации
@EnableScheduling   //позволяет сделать задержку
@EnableAsync        //асинхронное выполнение методов например рассылка смс клиентам
public class DemoApplication {


	//if we use cache manager then must be use this bean and remove caffeine cache manager from spring.properties file
//	@Bean
//	public CacheManager cacheManager(){
////		ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager("greetings");
//// replase to guava
//		GuavaCacheManager cacheManager = new GuavaCacheManager("greetings");
//		return cacheManager ;
//	}




//	@Bean
//	public DriverManagerDataSource conferenceDatasource(){
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName(EmbeddedDatabase.class.getName());
//		dataSource.setUrl("jdbc:derby:conferences;create=false");
////        dataSource.setPassword();  //если нужно
////        dataSource.setPassword();
//		return dataSource;
//	}

//	@Bean
//	public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
//		LocalContainerEntityManagerFactoryBean emFactory = new LocalContainerEntityManagerFactoryBean();
//		emFactory.setDataSource(conferenceDatasource());
//		emFactory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
//		emFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//
//		Properties jpaProperties = new Properties();
//		jpaProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.DerbyTenSevenDialect");
//		jpaProperties.setProperty("hibernate.hbm2ddl.auto", "create");
//
//		emFactory.setJpaProperties(jpaProperties);
//		emFactory.setPackagesToScan("com.example");
//		return emFactory;
//	}

//	@Bean
//	public JpaTransactionManager transactionManager(){
//		JpaTransactionManager transactionManager = new JpaTransactionManager();
//		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
//		return transactionManager;
//	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
