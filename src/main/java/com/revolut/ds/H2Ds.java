package com.revolut.ds;

import org.h2.jdbcx.JdbcDataSource;
import org.jvnet.hk2.annotations.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * H2 datastore
 */
@Service
public class H2Ds implements Ds {

    private DataSource dataSource;

    public H2Ds() throws IOException {
        Properties properties = new Properties();
        properties.load(H2Ds.class.getResourceAsStream("/config.properties"));

        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL(properties.getProperty("db.url"));
        jdbcDataSource.setUser(properties.getProperty("db.user"));
        jdbcDataSource.setPassword(properties.getProperty("db.pwd"));
        dataSource = jdbcDataSource;
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }
}
