package com.meta.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(value = "com.sensor.dao", sqlSessionFactoryRef="sqlSessionFactory")
public class TwoDataSourceConfig {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ConfigurationProperties(prefix = "spring.datasource.test2")
	@Bean(name = "mysql2DataSource")
	public DataSource mysql2DataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory(@Autowired @Qualifier("mysql2DataSource") DataSource dataSource) throws Exception {
		
		logger.info("SqlSessionFactory SUB Start");

        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath:mappers/*.xml"));
        sessionFactory.setTypeAliasesPackage("com.sensor.dao");
        
        Resource myBatisConfig = new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml");
        sessionFactory.setConfigLocation(myBatisConfig);

        return sessionFactory.getObject();
	}
	
	@Bean(name = "db2SqlSessionTemplate")
	public SqlSession sqlSessionSub(@Autowired @Qualifier("sqlSessionFactory") SqlSessionFactory factory) {
		logger.info("sub db Temp");
		return new SqlSessionTemplate(factory);
	}
}
