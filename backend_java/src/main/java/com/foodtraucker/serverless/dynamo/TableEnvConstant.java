package com.foodtraucker.serverless.dynamo;

/**
 * All constants here must map the environment variable set in serverless config
 *
 * @author palmithor
 * @since 2017-10-28
 */
public class TableEnvConstant {

    public static final String TRUCKS_TABLE = "TRUCKS_TABLE";
    public static final String CHECKINS_TABLE = "TRUCK_CHECKINS_TABLE";

    private TableEnvConstant() {

    }
}
