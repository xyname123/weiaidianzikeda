/*
package com.vivedu.ckd.config;

import lombok.Data;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import com.alibaba.druid.pool.DruidDataSource;
import javax.sql.DataSource;

@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "datasource.local")
@MapperScan(basePackages =localDatasourceConfig.PACKAGE , sqlSessionFactoryRef = "localSqlSessionFactory")
public class localDatasourceConfig {

    static final String PACKAGE = "com.vivedu.ckd.dao.local";
    static final String MAPPER_LOCATION = "classpath:mappers/local/*.xml";

    @Value("${spring.datasource.local.url}")
    private String url;
    @Value("${spring.datasource.local.username}")
    private String user;
    @Value("${spring.datasource.local.password}")
    private String password;
    @Value("${spring.datasource.local.driver-class-name}")
    private String driverClass;

    @Bean(name = "localDataSource")
    public DataSource localDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "localTransactionManager")
    public DataSourceTransactionManager localTransactionManager() {
        return new DataSourceTransactionManager(localDataSource());
    }

    @Bean(name = "localSqlSessionFactory")
    public SqlSessionFactory localSqlSessionFactory(@Qualifier("localDataSource") DataSource localDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(localDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(localDatasourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}
*/
