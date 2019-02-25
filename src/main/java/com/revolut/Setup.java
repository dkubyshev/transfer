package com.revolut;

import com.revolut.ds.H2Ds;
import org.flywaydb.core.Flyway;

import java.io.IOException;

/**
 * Init DB
 */
public class Setup {

    public void run() throws IOException {
        H2Ds ds = new H2Ds();
        Flyway flyway = Flyway.configure().dataSource(ds.getDataSource()).load();
        flyway.migrate();
    }
}
