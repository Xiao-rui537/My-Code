package com.xiaorui.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * MyBatisConfig 配置类
 * 用于配置 MyBatis 相关的组件，整合 MyBatis 和 Spring。
 */
@Configuration // 声明为配置类
@MapperScan("com.xiaorui.mapper") // 指定 MyBatis Mapper 接口所在的包
public class MyBatisConfig {

    /**
     * 配置 SqlSessionFactory Bean
     * SqlSessionFactory 是 MyBatis 与数据库交互的核心对象。
     *
     * @param dataSource 数据源对象，由 Spring 管理
     * @return SqlSessionFactory 对象
     * @throws Exception 如果 SqlSessionFactoryBean 初始化失败，抛出异常
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean(); // 创建 SqlSessionFactoryBean 实例
        factoryBean.setDataSource(dataSource); // 设置数据源
        return factoryBean.getObject(); // 返回 SqlSessionFactory 实例
    }
}
