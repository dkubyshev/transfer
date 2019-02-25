package com.revolut.ds;


import org.jvnet.hk2.annotations.Contract;

import javax.sql.DataSource;

/**
 * Datastore interface
 */
@Contract
public interface Ds {

    DataSource getDataSource();

}
