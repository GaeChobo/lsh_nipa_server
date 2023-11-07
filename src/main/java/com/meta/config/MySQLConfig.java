/*
package com.meta.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
public class MySQLConfig {
	
	@Autowired
	@Qualifier(value = "OnedataSource")
	private DataSource OnedataSource;
	
	@Autowired
	@Qualifier(value = "TwodataSource")
	private DataSource TwodataSource;
	
    @Bean
    @Primary
    public SqlSessionFactory OnesqlSessionFactory() throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(OnedataSource);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath:mappers/*.xml"));
        sessionFactory.setTypeAliasesPackage("com.meta.dao");
        
        Resource myBatisConfig = new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml");
        sessionFactory.setConfigLocation(myBatisConfig);

        return sessionFactory.getObject();
    }
    
    @Bean
    public SqlSessionFactory TwosqlSessionFactory() throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(TwodataSource);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath:mappers/*.xml"));
        sessionFactory.setTypeAliasesPackage("com.meta.dao.sensor");

        Resource myBatisConfig = new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml");
        sessionFactory.setConfigLocation(myBatisConfig);

        return sessionFactory.getObject();
    }
    
    @Bean
    public SqlSession OneSqlSession() throws Exception {
    	
    	return new SqlSessionTemplate(OnesqlSessionFactory());
    }
    
    @Bean
    public SqlSession TwoSqlSession() throws Exception {
    	
    	return new SqlSessionTemplate(TwosqlSessionFactory());
    }
}
*/