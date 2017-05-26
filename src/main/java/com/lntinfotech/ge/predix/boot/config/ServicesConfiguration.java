/**
 * 
 */
package com.lntinfotech.ge.predix.boot.config;

import javax.persistence.EntityManagerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
//import com.lntinfotech.ge.service.CustomerService;

/**
 * @author 10620506
 *
 */
@Configuration
@EnableCaching
@EnableTransactionManagement
@ComponentScan(basePackageClasses = {})
public class ServicesConfiguration {

	@Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) throws Exception {
        return new JpaTransactionManager(entityManagerFactory);
    }
	
}
