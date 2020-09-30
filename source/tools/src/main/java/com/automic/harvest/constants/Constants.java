package com.automic.harvest.constants;

/**
 * Constants class contains all the constants.
 *
 */
public class Constants {

    public static final String ACTION = "action";
    public static final String HELP = "help";

    public static final String ENV_CONNECTION_TIMEOUT = "ENV_CONNECTION_TIMEOUT";
    public static final String ENV_READ_TIMEOUT = "ENV_READ_TIMEOUT";
    public static final String ENV_API_VERSION = "ENV_API_VERSION";
    public static final String ENV_PASSWORD = "UC4_DECRYPTED_PWD";

    public static final String AGENT_PASSWORD = "UC4_DECRYPTED_AGENT_PWD";

    public static final int CONNECTION_TIMEOUT = 30000;
    public static final int READ_TIMEOUT = 60000;
    public static final String API_VERSION = "v4";

    public static final String BASE_URL = "baseurl";
    public static final String HTTPS = "https";
    public static final String SKIP_CERT_VALIDATION = "ssl";

    public static final int MINUS_ONE = -1;
    public static final int ZERO = 0;

    public static final String YES = "YES";
    public static final String TRUE = "TRUE";
    public static final String ONE = "1";
    public static final String CHECK_OUT_FOR_BROWSE = "CHECK_OUT_FOR_BROWSE";
    public static final String CHECK_OUT_FOR_RESERVE_ONLY = "CHECK_OUT_FOR_RESERVE_ONLY";
    public static final String CHECK_OUT_FOR_UPDATE = "CHECK_OUT_FOR_UPDATE";
    public static final String CHECK_OUT_FOR_SYNCHRONIZE = "CHECK_OUT_FOR_SYNCHRONIZE";
    public static final String CHECK_OUT_FOR_CONCURRENT_UPDATE = "CHECK_OUT_FOR_CONCURRENT_UPDATE";
    public static final String PRESERVE_AND_CREATE_DIRECTORY_STRUCTURE = "PRESERVE_AND_CREATE_DIRECTORY_STRUCTURE";

    public static final String CO_ACTION_RUN_PASS_MSG = "Checkout Action has run successfully.";

    public static final String UPDATE_AND_RELEASE = null;
    public static final String ALL_FILES_TO_SAME_VIEW_PATH = "All_Files_to_Same_View_Path";
    public static final String NEW_OR_EXISTING_ITEMS = "New_Or_Existing_Items";
    public static final String RELEASE_ONLY = "Release_Only";
    public static final String UPDATE_AND_KEEP = "Update_And_Keep";
    public static final String NEW_ITEMS_ONLY = "New_Items_Only";
    public static final String PRESERVE_AND_CREATE_PATH_STRUCTURE = "Preserve_And_Create_Path_Structure";
    public static final String SUCCESS_CHECK_IN_LOCAL = "Successfully checked-in files";
    public static final String SUCCESS_CHECK_IN_BY_AGENT = "Successfully checked-in files by agent";

    private Constants() {
    }
}
