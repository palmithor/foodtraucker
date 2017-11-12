package com.foodtraucker.serverless.dynamo;

/**
 * All constants here must map the environment variable set in serverless config
 *
 * @author palmithor
 * @since 2017-10-28
 */
public class TableEnvConstant {

    public static final String TRUCKS_TABLE = "TRUCKS_TABLE";
    public static final String CHECKINS_TABLE = "FOODTRUCK_CHECKINS_TABLE";
    public static final String FOODTRUCK_USERS_TABLE = "FOODTRUCK_USERS_TABLE";
    public static final String FOODTRUCK_MENUS_TABLE = "FOODTRUCK_MENUS_TABLE";

    private TableEnvConstant() {

    }
}
