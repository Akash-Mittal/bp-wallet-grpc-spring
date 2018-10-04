//package com.bp.wallet.server.config;
//
//import java.util.Properties;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.orm.hibernate5.HibernateTransactionManager;
//import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//
//public class HibernateConfig {
//
//	@Bean
//	public LocalSessionFactoryBean sessionFactory() {
//		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//		sessionFactory.setDataSource(dataSource());
////        sessionFactory.setPackagesToScan({"com.baeldung.hibernate.bootstrap.model" });
//		sessionFactory.setHibernateProperties(hibernateProperties());
//
//		return sessionFactory;
//	}
//
////	@Bean
////	public DataSource dataSource() {
//////        BasicDataSource dataSource = new BasicDataSource();
//////        dataSource.setDriverClassName("org.h2.Driver");
//////        dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
//////        dataSource.setUsername("sa");
//////        dataSource.setPassword("sa");
//////        return dataSource;
////		return DataSourceBuilder.create().driverClassName("org.h2.Driver").url("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1")
////				.username("sa").password("sa").build();
////
////	}
//
//	@Bean
//	public PlatformTransactionManager hibernateTransactionManager() {
//		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
//		transactionManager.setSessionFactory(sessionFactory().getObject());
//		return transactionManager;
//	}
//
//	private final Properties hibernateProperties() {
//		Properties hibernateProperties = new Properties();
//		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
//		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
//		return hibernateProperties;
//	}
//}