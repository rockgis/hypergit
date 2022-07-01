package com.goodmit.hypergit.global.config;

import com.goodmit.hypergit.global.security.authn.LoginAuditListener;
import com.goodmit.hypergit.sample.repository.MemberDSLRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.goodmit.hypergit.repository"},
        entityManagerFactoryRef = "authEntityMgrFactory",
        transactionManagerRef = "authTransactionMgr"
)
@Slf4j
public class AuthDBConfig {

    @Bean(name="authDatasource")
    @Primary
    @ConfigurationProperties(prefix = "authdb.hikari")
    public DataSource authDatasource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Primary
    @Bean(name = "authEntityMgrFactory")
    public LocalContainerEntityManagerFactoryBean authEntityMgrFactory(EntityManagerFactoryBuilder builder) {
        Map<String, Object> properties = hibernateProperties()
                .determineHibernateProperties(
                        authProperties().getProperties(),
                        new HibernateSettings());

        return builder.dataSource(authDatasource())
                .packages( "com.goodmit.hypergit.repository.entity")
                .properties(properties)
                .build();
    }

    @Primary
    @Bean(name = "authTransactionMgr")
    public PlatformTransactionManager authTransactionMgr(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(authEntityMgrFactory(builder).getObject());
    }

    @Bean(name = "authProperties")
    @Primary
    @ConfigurationProperties(prefix = "authdb.jpa")
    public JpaProperties authProperties() {
        return new JpaProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "authdb.jpa.hibernate")
    public HibernateProperties hibernateProperties() {
        return new HibernateProperties();
    }

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public MemberDSLRepository memberCustomRepository(JPAQueryFactory jpaQueryFactory) {
        return new MemberDSLRepository(jpaQueryFactory);
    }


}
