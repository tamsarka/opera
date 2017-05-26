/**
 * 
 */
package com.lntinfotech.ge.predix.boot.config;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.PostgreSQL9Dialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 10620506
 *
 */
public class DatasourceConfiguration {
	
	/*@Configuration
	  @Profile("cloud")
	 	@EnableJpaRepositories
	 	@EnableTransactionManagement
	    static class PostgresCloudDbConfig extends AbstractCloudConfig {
	    	
	    	
		 @Bean
	    	public DataSource dataSource() {
	    			return connectionFactory().dataSource();
	        }
	    	
	    	
	    	@Bean
	        public LocalContainerEntityManagerFactoryBean entityManagerFactory( DataSource dataSource  ) throws Exception {
	            LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
	            AbstractJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	            vendorAdapter.setGenerateDdl(true);
	            vendorAdapter.setShowSql(false);
	          	vendorAdapter.setDatabase(Database.POSTGRESQL);
	           	vendorAdapter.setDatabasePlatform(PostgreSQL9Dialect.class.getName());
	            
	            em.setDataSource( dataSource );
	            //em.setPackagesToScan(Customer.class.getPackage().getName());
	            em.setPackagesToScan("com.lntinfotech.ge.predix.datasource.datagrid.entity");
	            em.setPersistenceProvider(vendorAdapter.getPersistenceProvider());
	            Map<String, String> p = new HashMap<String, String>();
	            p.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, "update");
	            p.put(org.hibernate.cfg.Environment.HBM2DDL_IMPORT_FILES, "initialDatabaseTables.sql");
	         //	p.put(org.hibernate.cfg.Environment.DIALECT, PostgreSQL9Dialect.class.getName());
	            p.put(org.hibernate.cfg.Environment.SHOW_SQL, "true");
	            em.setJpaPropertyMap(p);
	            return em;
	        }*/
	  

		@Profile("local")
	 	@Configuration
	 	@EnableJpaRepositories
	 	@EnableTransactionManagement
	 	static class LocalH2DbConfig {
	 		
	 		
	 		  @Bean
	 		  public DataSource dataSource() {
	 		    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	 		    dataSource.setDriverClassName("org.h2.Driver");
	 		    dataSource.setUrl("jdbc:h2:file:~/mydb;DB_CLOSE_ON_EXIT=FALSE");
	 		    dataSource.setUsername("sa");
	 		    dataSource.setPassword("");
	 		    return dataSource;
	 		  }

	 		
	 		  @Bean
	 		  public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws Exception {
	 		        
	 			  	LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
	 			  	em.setDataSource(dataSource);
	 		        
	 			  	HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	 		        vendorAdapter.setGenerateDdl(true);
	 		        vendorAdapter.setShowSql(true);
	 		        vendorAdapter.setDatabase(Database.H2);
	 		        vendorAdapter.setDatabasePlatform(H2Dialect.class.getName());
	 		        
	 		        
	 		        //em.setPackagesToScan(Customer.class.getPackage().getName());
	 		        em.setPackagesToScan("com.lntinfotech.ge.predix.datasource.datagrid.entity");
	 		        em.setPersistenceProvider(vendorAdapter.getPersistenceProvider());
	 		        
	 		        Map<String, String> p = new HashMap<String, String>();
	 		        p.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, "update");
	 		       p.put(org.hibernate.cfg.Environment.HBM2DDL_IMPORT_FILES, "initialDatabaseTables.sql");
	 		       // p.put(org.hibernate.cfg.Environment.DIALECT, PostgreSQL9Dialect.class.getName());
	 		        p.put(org.hibernate.cfg.Environment.DIALECT, H2Dialect.class.getName());
	 		        p.put(org.hibernate.cfg.Environment.SHOW_SQL, "true");
	 		        em.setJpaPropertyMap(p);
	 		        em.setJpaVendorAdapter(vendorAdapter);
	 		        return em;
	 		    }
	 		  
	 		 
	 		 
	 		  @Bean
	 		  public JpaTransactionManager transactionManager() {
	 		    JpaTransactionManager transactionManager = 
	 		        new JpaTransactionManager();
	 		    transactionManager.setEntityManagerFactory(
	 		        entityManagerFactory.getObject());
	 		    return transactionManager;
	 		  }
	 		  
	 		  /**
	 		   * PersistenceExceptionTranslationPostProcessor is a bean post processor
	 		   * which adds an advisor to any bean annotated with Repository so that any
	 		   * platform-specific exceptions are caught and then rethrown as one
	 		   * Spring's unchecked data access exceptions (i.e. a subclass of 
	 		   * DataAccessException).
	 		   */
	 		  @Bean
	 		  public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
	 		    return new PersistenceExceptionTranslationPostProcessor();
	 		  }
	 		  
	 		  @Autowired
	 		  private DataSource dataSource;

	 		  @Autowired
	 		  private LocalContainerEntityManagerFactoryBean entityManagerFactory;
	 	}
}
