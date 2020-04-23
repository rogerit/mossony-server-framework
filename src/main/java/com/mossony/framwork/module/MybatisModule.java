package com.mossony.framwork.module;

import lombok.AllArgsConstructor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.configuration.settings.ConfigurationSetting;
import org.mybatis.guice.datasource.druid.DruidDataSourceProvider;

@AllArgsConstructor
public class MybatisModule extends MyBatisModule {

    private String packageName;

    @Override
    protected void initialize() {
        bindDataSourceProviderType(DruidDataSourceProvider.class);
        bindTransactionFactoryType(JdbcTransactionFactory.class);
        addMapperClasses(packageName);
    }

}
