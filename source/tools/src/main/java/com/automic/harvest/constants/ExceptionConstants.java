package com.automic.harvest.constants;

/**
 * Exception constants used in the application
 * 
 * @author shrutinambiar
 *
 */
public class ExceptionConstants {

    public static final String UNABLE_TO_FLUSH_STREAM = "Error while flushing stream";
    public static final String INVALID_ARGS = "Improper Args. Possible cause : %s";

    public static final String INVALID_INPUT_PARAMETER = "Invalid value for parameter [%s] : [%s]";

    public static final String INVALID_FILE = "Invalid file [%s], possibly file doesn't exists";
    public static final String GENERIC_ERROR_MSG = "System Error occured.";

    // Certificate errors
    public static final String ERROR_SKIPPING_CERT = "Error skipping the certificate validation";
    public static final String INVALID_KEYSTORE = "Invalid KeyStore : %s";
    public static final String SSLCONTEXT_ERROR = "Unable to build secured context : %s";

    // promote package
    public static final String INVALID_PACKAGE_LIST = "As some of the user input packages are not in the provided state, hence Promote Package action did not execute";
    public static final String INVALID_FROM_STATE_PACKAGE_LIST = "From State does not have all the user input packages.";
    public static final String INVALID_PACKAGE_GROUPS = "As some of the user input packages groups are not in the provided state, hence Promote Package action did not execute";
    public static final String INVALID_FROM_STATE_PACKAGE_GROUP = "From State does not have all the user input Package Groups";
    public static final String PROMOTION_FAILURE = "Package promotion Failed as either no available process found that can promote packages from FromState to ToState or given process name is invalid process name for the defined states";
    public static final String TOSTATE_NOT_FOUND = "Unable to find the ToState";
    public static final String FROMSTATE_NOT_FOUND = "Unable to find the FromState";
    public static final String PKG_PROMOTION_FAILURE = "Package promotion failed";
    public static final String NOT_FOUND_FAILURE = "%s not found: %s";

    // demote package
    public static final String PACKAGE_GROUP_NOT_AVAILABLE = "As all of the provided package groups are not available in the provided State, hence Demote Package action did not execute.";
    public static final String PACKAGE_NOT_AVAILABLE = "As all of the provided packages are not available in the provided State, hence Demote Package action did not execute.";
    public static final String DEMOTE_PROCESS_NOT_FOUND = "Demote process was not found";
    public static final String INVALID_TO_STATE = "The state entered in the To State is not a valid state in the provided project.";
    public static final String INVALID_FROM_STATE = "The state entered in the From State is not a valid state in the provided project.";
    public static final String PACKAGE_DEMOTION_FAIL = "Demote Package Action execution failed.";
    public static final String PROCESS_STATE_MISMATCH = "The From State and To State of the provided process doesn't match with the From State and To State provided by user";
    public static final String INCORRECT_DEMOTE_PROCESS = "Either the user doesn't have access to the specified process or the Process name entered is not a valid process name";
    public static final String NO_DEMOTE_PROCESS_TYPE = "No process of type Demote is found in the provided 'From State'.";

    public static final String PROCESS_NOT_FOUND = "Process not found: %s%";
    public static final String STATE_NOT_FOUND = "State not found: %s%";

    public static final String PACKAGE_NOT_IN_STATE = "The Package Name is not available in the provided state.";
    public static final String CO_INITIALIZATION_FAILED = "CheckoutProcess initialization has failed.";
    public static final String CO_FILE_INCORRECT = "Either no file is found for checkout or the file name entered is incorrect.";
    public static final String CO_ACTION_RUN_FAIL_MSG = "Checkout Action run has failed.";
    public static final String NO_CLIENT_PATH = "The client path is not available and unable to create the client path.";
    public static final String NO_SUCH_FILE = "Checkout process did not complete. One of the filename could not be found in the View path";
    public static final String PROCESS_NO_SELECTED_MODE = "The current process does not have the selected Checkout Mode enabled.";
    public static final String PACKAGE_NAME_REQUIRED = "Package Name is must when the Checkout Process Mode is Update, Concurrent Update, Reserve";
    public static final String PACKAGE_NOT_FOUND_FAILURE = "Package [%s] not found in the given Harvest project. Please provide a valid package name";
    public static final String SCM_PATH_ERROR = "Can not find CA_SCM_HOME System environment variable. Action can not be executed!";
    public static final String CONNECT_FAILURE = "Cannot connect to the Broker";
    public static final String PASSWORD_FAILURE = "Password incorrect";
    public static final String INVALID_PORT = "Port is out of range,Valid range is: [ %d - %d ] . Port: [%d]";
    public static final String PATH_ERROR = "Can not find the harvest installed directory in System PATH. Action can not be executed!";
    public static final String STATE_NOT_FOUND_FAILURE = "State not found: %s";
    public static final String PROCESS_NOT_FOUND_FAILURE = "Process not found: %s";
    public static final String PROJECT_NOT_FOUND_FAILURE = "Project not found: %s";
    public static final String PKG_PROMOTE_FAILURE_TOSTATE = "Given Process cannot promote Package to the State %s";
    public static final String PKG_PROMOTE_FAILURE_FROMSTATE = "Given Process cannot promote Package from the State %s";
    public static final String PKG_NAME_GRPNAME_EXCLUSIVE = "Package Names and Package Group Names are mutually exclusive fields.";
    public static final String FILE_NOT_FOUND_FAILURE = "File %s not found in the given Harvest project. Please provide a valid file name";
    public static final String NO_CONTENTS_IN_CONTENT_LIST = "Failed to find any contents in the file/folder list";
    public static final String FAILURE_CHECK_IN_LOCAL = "Failed to check-in files. Error: %s";
    public static final String FAILURE_CHECK_IN_BY_AGENT = "Failed to check-in files by agent. Error: %s";
    public static final String NO_CONTENTS_IN_CLIENT_PATH = "No content found in client path %s";
    public static final String PROCESS_FAILURE = "Invalid process object";

    private ExceptionConstants() {
    }
}
