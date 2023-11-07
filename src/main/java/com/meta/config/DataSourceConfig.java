package com.meta.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import org.slf4j.Logger;

@Configuration
@MapperScan(value = "com.meta.dao", sqlSessionFactoryRef="sqlSessionFactoryMain")
public class DataSourceConfig {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@ConfigurationProperties(prefix = "spring.datasource.test1")
	@Primary
	@Bean
	public DataSource mysql1DataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name = "sqlSessionFactoryMain")
	@Primary
	public SqlSessionFactory sqlSessionFactoryMain(@Autowired DataSource dataSource) throws Exception {
		
		logger.info("SqlSessionFactory Main Start");

        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath:mappers/*.xml"));
        sessionFactory.setTypeAliasesPackage("com.meta.dao");
        
        Resource myBatisConfig = new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml");
        sessionFactory.setConfigLocation(myBatisConfig);

        return sessionFactory.getObject();
	}
	
	@Bean(name = "db1SqlSessionTemplate")
	@Primary
	public SqlSession sqlSessionMain(@Autowired @Qualifier("sqlSessionFactoryMain") SqlSessionFactory factory) {
		logger.info("main db Temp");
		return new SqlSessionTemplate(factory);
	}
	
}
