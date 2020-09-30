/*******************************************************************************
 * Copyright (c) 2014 CA.  All rights reserved.
 * This software and all information contained therein is confidential and proprietary and shall
 * not be duplicated, used, disclosed or disseminated in any way except as authorized by the
 * applicable license agreement, without the express written permission of CA. All authorized
 * reproductions must be marked with this language.
 * 
 * EXCEPT AS SET FORTH IN THE APPLICABLE LICENSE AGREEMENT, TO THE EXTENT PERMITTED BY APPLICABLE
 * LAW, CA PROVIDES THIS SOFTWARE WITHOUT WARRANTY OF ANY KIND, INCLUDING WITHOUT LIMITATION, ANY
 * IMPLIED WARRANTIES OF MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.  IN NO EVENT WILL CA
 * BE LIABLE TO THE END USER OR ANY THIRD PARTY FOR ANY LOSS OR DAMAGE, DIRECT OR INDIRECT, FROM
 * THE USE OF THIS SOFTWARE, INCLUDING WITHOUT LIMITATION, LOST PROFITS, BUSINESS INTERRUPTION,
 * GOODWILL, OR LOST DATA, EVEN IF CA IS EXPRESSLY ADVISED OF SUCH LOSS OR DAMAGE.
 ******************************************************************************/
package com.automic.harvest.constants;

/**
 * @author belaj01
 *
 */
public class Resource {

    // Base Class constants
    public static final String BROKER_NAME = "Broker";
    public static final String BROKER_NAME_DESC = "Host Name of the Harvest Broker";
    public static final String BROKER_PORT = "Port Number";
    public static final String BROKER_PORT_DESC = "Port Number of the Harvest Broker";
    public static final String USER_NAME = "User Name";
    public static final String USER_NAME_DESC = "User Name for logging into the CA SCM Harvest Broker";
    public static final String PASSWORD = "Password";
    public static final String PASSWORD_DESC = "Harvest SCM Broker password";
    public static final String PROJECT_NAME = "Project";
    public static final String PROJECT_NAME_DESC = "CA Harvest working Project Name";
    public static final String STATE_NAME = "State Name";
    public static final String CURRENT_STATE_NAME = "Current State Name";
    public static final String STATE_NAME_DESC = "Current State to which the package belongs to";
    public static final String PROCESS_NAME = "Process Name";
    public static final String PROCESS_NAME_DESC = "CA Harvest Process Name. If it is left empty, then the system will automatically pick a process for the given type.";
    public static final String HARVEST_CREATE_CHANGE_PACKAGE = "Harvest - Create Package";
    public static final String HARVEST_CREATE_CHANGE_PACKAGE_DESC = "Creates a new change package in the given Harvest project";
    public static final String HARVEST_ACTION_CAT = "Source Control.Harvest";
    public static final String PACKAGE_NAME = "Package Name";
    public static final String PKG_NAME_DESC = "Name of the package to be created. Package will be created with default name defined in the create package process in case package name is empty";
    public static final String NEW_PACKAGE_DESC = "Package Description";
    public static final String NEW_PKG_DESC_DESCRPTION = "Description for the newly created package";
    public static final String PACKAGE_NAMES = "Package Name(s)";
    public static final String DEL_PKG_NAME_DESC = "Comma separated list of packages to be deleted";
    public static final String HARVEST_DELETE_CHANGE_PACKAGE = "Harvest - Delete Package";
    public static final String HARVEST_DELETE_CHANGE_PACKAGE_DESC = "Deletes the mentioned change package(s)";
    public static final String HARVEST_GET_CHANGE_PACKAGE = "Harvest - Get Package";
    public static final String HARVEST_GET_CHANGE_PACKAGE_DESC = "Retrieves the details of a given change package";
    public static final String GET_PKG_NAME_DESC = "Name of the package";
    public static final String GET_PKG_DESC = "Description of the package";
    public static final String HARVEST_GET_USER = "Harvest - Get User";
    public static final String HARVEST_GET_USER_DESC = "Retrieves the details of a given user";
    public static final String USER_REAL_NAME = "Real User Name";
    public static final String USER_REAL_NAME_DESC = "Real Name of the user";
    public static final String USER_EMAIL = "E-mail";
    public static final String USER_EMAIL_DESC = "User e-mail address";
    public static final String USER_GROUP_LIST = "User Groups";
    public static final String USER_GROUP_LIST_DESC = "List of user groups this user belongs to";
    public static final String USER_PASSWORD_EXPIRES = "Password expires in";
    public static final String USER_PASSWORD_EXPIRES_DESC = "Number of days before the current user password expires";
    public static final String VIEW_NAME = "View Name";
    public static final String VIEW_NAME_DESC = "Name of the view  this change package is currently in";
    public static final String ASSIGNED_USER_NAME = "Assigned User Name";
    public static final String ASSIGNED_USER_NAME_DESC = "User assigned to the package.";
    public static final String IS_APPROVED = "Is Approved";
    public static final String IS_APPROVED_DESC = "Has the package been approved?";
    public static final String STATUS = "Status";
    public static final String STATUS_DESC = "Status of the package";
    public static final String FORM_LIST = "Forms";
    public static final String FORM_LIST_DESC = "List of Forms associated with this package";
    public static final String GROUP_LIST = "Part of Package Groups";
    public static final String GROUP_LIST_DESC = "List of Package Groups that this Package belongs to";
    public static final String PACKAGE_NAMES_DELETED = "Packages Deleted";
    public static final String PACKAGE_NAMES_DELETED_DESC = "List of packages which are deleted successfully";
    public static final String PACKAGE_NAMES_UNAVAILABLE = "Unavailable Packages";
    public static final String PACKAGE_NAMES_UNAVAILABLE_DESC = "List of unavailable packages";
    public static final String PACKAGE_NAMES_FAILED = "Failed to Delete";
    public static final String PACKAGE_NAMES_FAILED_DESC = "List of packages which are failed to delete";
    public static final String HARVEST_GET_VERSION = "Harvest - Get Version";
    public static final String HARVEST_GET_VERSION_DESC = "Retrieves the version details of a Harvest item";
    public static final String FILE_NAME = "File Name";
    public static final String FILE_NAME_DESC = "Name of the harvest item (file name) for which the version details need to be retrieved";
    public static final String GET_FILE_DESC = "File Description";
    public static final String GET_FILE_ITEM_DESC = "Description for the file item";
    public static final String FILE_MAPPED_VERSION = "File Version";
    public static final String FILE_MAPPED_VERSION_DESC = "Mapped version of the file";
    public static final String CREATOR_NAME = "Creator";
    public static final String CREATOR_NAME_DESC = "User Name of the file creator";
    public static final String VERSION_STATUS = "Version Status";
    public static final String VERSION_STATUS_DESC = "Version tag of the file. Whether Normal, Reserved, Deleted";
    public static final String DATA_SIZE = "Data Size";
    public static final String DATA_SIZE_DESC = "Size of the file data";
    public static final String MODIFIER_NAME = "Modifier";
    public static final String MODIFIER_NAME_DESC = "User Name of the latest modifier";
    public static final String CREATE_DATE = "Created On";
    public static final String CREATE_DATE_DESC = "The date on which the file is created. Date will be displayed in the format yyyy-MM-dd HH:mm:ss";
    public static final String MODIFIED_DATE = "Modified On";
    public static final String MODIFIED_DATE_DESC = "Last modification date of the file. Date will be displayed in the format yyyy-MM-dd HH:mm:ss";
    public static final String AGENT_PORT = "Agent Port";
    public static final String AGENT_PORT_DESC = "CA Harvest agent port number";

    public static final String PACKAGE_NAME_DESC = "CA Harvest Package Name";
    public static final String CHECKIN_PATH = "Check-in Directory Path";
    public static final String CHECKIN_PATH_DESC = "Client file system directory path to be used for check-in";
    public static final String CONTENT_LIST = "List Of Files or Folders to Check-in";
    public static final String CONTENT_LIST_DESC = "Comma separated files or folder names to be checked-in from check-in directory path";
    public static final String CHECKIN_DESC = "Check-in Description";
    public static final String CHECKIN_DESC_DESC = "Description to be used for check-in";
    public static final String RECURSIVE = "Recursive";
    public static final String RECURSIVE_DESC = "Recursively find files in sub-directories of check-in directory path";
    public static final String CHECKIN_MODE = "Check-in Mode";
    public static final String CHECKIN_MODE_DESC = "Check-in mode to be used";
    public static final String ITEM_FILTER = "Check-in Item Filter Option";
    public static final String ITEM_FILTER_DESC = "Filters to be used for check-in items";
    public static final String PATH_OPTION = "Item Path Options";
    public static final String PATH_OPTION_DESC = "Options to be used for check-in item paths";
    public static final String CHECKIN_STATUS = "Check-in Execution Status";
    public static final String CHECKIN_STATUS_DESC = "Status of check-in execution";
    public static final String DELETE_AFTER_CI = "Delete After Check-in";
    public static final String DELETE_AFTER_CI_DESC = "Delete files from client directory after successful check-in";
    public static final String HARVEST_CHECK_IN_ACTION_NAME = "Harvest - Check-in Local";
    public static final String HARVEST_CHECK_IN_ACTION_DESC = "Check-in local file(s) to Harvest repository";
    public static final String HARVEST_CHECK_IN_BY_AGENT_ACTION_NAME = "Harvest - Check-in by Agent";
    public static final String HARVEST_CHECK_IN_BY_AGENT_ACTION_DESC = "Check-in file(s) to Harvest repository using a specified file agent";
    public static final String AGENT_NAME = "Harvest Agent Name";
    public static final String AGENT_NAME_DESC = "Name of the harvest file agent";
    public static final String AGENT_USERNAME = "Harvest Agent User name";
    public static final String AGENT_USERNAME_DESC = "Harvest file agent login user name";
    public static final String AGENT_PASSWORD = "Harvest Agent Password";
    public static final String AGENT_PASSWORD_DESC = "Harvest file agent login password";

    // Promote Change Package
    public static final String HARVEST_PROMOTE_CHANGE_PACKAGE = "Harvest - Promote Package";
    public static final String HARVEST_PROMOTE_CHANGE_PACKAGE_DESC = "Promotes the mentioned change package(s) from given from-state to to-state";
    public static final String PROMOTE_PACKAGE_NAMES = "Package Names";
    public static final String PROMOTE_PKG_NAMES_DESC = "Package Names which needs to be promoted to next level";
    public static final String PROMOTE_PROCESS_NAME = "Promote Process Name";
    public static final String PROMOTE_PROCESS_NAME_DESC = "Name of the Promote Process. If it is left empty, then the system will automatically pick a promote process which belongs to given from state and to state";
    public static final String PROMOTE_GROUP_PACKAGE_NAMES = "Package Group Names";
    public static final String PROMOTE_PKG_GROUP_NAMES_DESC = "Package groups which needs to be promoted to next level.";
    public static final String ENFORCE_PACKAGE_BIND = "Enforce Package Bind";
    public static final String ENFORCE_PACKAGE_BIND_DESC = "It enforces package Bind.";
    public static final String ENFORCE_PACKAGE_MERGE = "Enforce Package Merge";
    public static final String ENFORCE_PACKAGE_MERGE_DESC = "It enforces package merge.";
    public static final String VERIFY_DEPENDANT_PACKAGES = "Verify Package dependancy";
    public static final String VERIFY_DEPENDANT_PACKAGES_DESC = "It verifies package dependancies.";
    public static final String PROMOTE_PACKAGE_STATUS = "Promote Package Result";
    public static final String PROMOTE_PACKAGE_STATUS_DESC = "It gives the status of each package that is promoted.";
    public static final String FROM_STATE = "From State";
    public static final String FROM_STATE_DESC = "CA Harvest Project State where the package currently resides.";
    public static final String TO_STATE = "To State";
    public static final String TO_STATE_DESC = "The State where the package is to be promoted.";

    public static final String HARVEST_GET_SNAPSHOT_ACTION_NAME = "Harvest - Get Snapshot";
    public static final String HARVEST_GET_SNAPSHOT_ACTION_DESC = "Retrieves the properties of a given snapshot";
    public static final String HARVEST_TAKE_SNAPSHOT_ACTION_NAME = "Harvest - Take Snapshot";
    public static final String HARVEST_TAKE_SNAPSHOT_ACTION_DESC = "Takes a snapshot for a given project with latest versions in the view";
    public static final String SNAPSHOT_NAME = "Snapshot Name";
    public static final String SNAPSHOT_NAME_DESC = "CA Harvest Snapshot Name";
    public static final String VISIBLE_TO_OTHER_PROJECTS_NAME = "Visible to other Projects";
    public static final String VISIBLE_TO_OTHER_PROJECTS_NAME_DESC = "Visible to other Projects set to true then the "
            + "created snapshot will be visible to other projects as well";
    public static final String VIEW_PATH = "View Path";
    public static final String VIEW_PATH_DESC = "CA Harvest Snapshot View Path";
    public static final String UPDATE_ACCESS = "Update Access";
    public static final String UPDATE_ACCESS_DESC = "CA Harvest Snapshot Update Access";
    public static final String VIEW_ACCESS = "View Access";
    public static final String VIEW_ACCESS_DESC = "CA Harvest Snapshot View Access";
    public static final String SNAPSHOT_TAKEN_TIME = "Snapshot Taken Time";
    public static final String SNAPSHOT_TAKEN_TIME_DESC = "Time at which the given Snapshot is taken. Time format is yyyy-MM-dd HH:mm:ss";
    public static final String FILE_COUNT = "Total Files";
    public static final String FILE_COUNT_DESC = "Total number of files available in given Snapshot";
    public static final String FOLDER_COUNT = "Total Folders";
    public static final String FOLDER_COUNT_DESC = "Total number of folders available in given Snapshot";
    public static final String FILE_TYPE = "Stored As";
    public static final String FILE_TYPE_DESC = "The format in which file stored in harvest project. Valid values are Binary, Text";
    public static final String HARVEST_GET_USER_LIST = "Harvest - Get User List";
    public static final String HARVEST_GET_USER_LIST_DESC = "Retrieves the list of Harvest users for a given broker";
    public static final String USER_LIST = "List of Users";
    public static final String USER_LIST_DESC = "List of users of a given harvest broker";

    // Harvest Checkout Action
    public static final String HARVEST_CHECKOUT_ACTION = "Harvest - Check-out Local";
    public static final String HARVEST_CHECKOUT_ACTION_DESC = "Check-out files from a Harvest repository to local host";
    public static final String CO_FILE_LIST = "File Name";
    public static final String CO_FILE_LIST_DESC = "List of the file/files that needs to be checkout.";
    public static final String CLIENT_PATH = "Client Path";
    public static final String CLIENT_PATH_DESC = "Location on the Nolio Agent machine where the file needs to be checked out.";
    public static final String CO_VIEW_PATH = "View Path";
    public static final String CO_VIEW_PATH_DESC = "The repository path needs to be specified here.";
    public static final String CHECKOUT_PROCESS_TYPE = "Checkout Process Type";
    public static final String CHECKOUT_PROCESS_TYPE_DESC = "Type of Checkout Process";
    public static final String CHECKOUT_PROCESS_NAME = "Checkout Process Name";
    public static final String CHECKOUT_PROCESS_NAME_DESC = "Name of the Checkout Process";
    public static final String CHECKOUT_PROCESS_MODE = "Checkout Process Mode";
    public static final String CHECKOUT_PROCESS_MODE_DESC = "Name of the Checkout Process Mode";
    public static final String CHECKOUT_OPTIONS = "Checkout Option";
    public static final String CHECKOUT_OPTIONS_DESC = "Specify the Checkout Option";
    public static final String SET_REPLACE_FILE = "Set Replace file";
    public static final String SET_REPLACE_FILE_DESC = "Set Replace file";
    public static final String SET_REPLACE_WRITEABLE_FILE = "Set Replace Writable File";
    public static final String SET_REPLACE_WRITEABLE_FILE_DESC = "Set Replace Writable File";
    public static final String SET_RECURSIVE = "Set Recursive";
    public static final String SET_RECURSIVE_DESC = "Set Recursive";
    public static final String SET_ADD_EMPTY_DIR_OPTION = "Set Add Empty Dir Option";
    public static final String SET_ADD_EMPTY_DIR_OPTION_DESC = "Set Add Empty Dir Option";
    public static final String CHECKOUT_STATUS = "Checkout Status";
    public static final String CHECKOUT_STATUS_DESC = "Checkout Status";
    public static final String TOTAL_CO_STATUS = "Total";
    public static final String TOTAL_CO_STATUS_DESC = "Total number of files that were considered for checkout.";
    public static final String SUCCESS_CO_STATUS = "Success";
    public static final String SUCCESS_CO_STATUS_DESC = "Total number of files that have successfully checkedout";
    public static final String FAILED_CO_STATUS = "Failed";
    public static final String FAILED_CO_STATUS_DESC = "Total number of files that have failed to get checkedout";
    public static final String NOT_PROCESSED_CO_STATUS = "Not Processed";
    public static final String NOT_PROCESSED_CO_STATUS_DESC = "Total number of files that have not got processed for checkedout";

    // Harvest Demote Package Action
    public static final String HARVEST_DEMOTE_PACKAGE_ACTION = "Harvest - Demote Package";
    public static final String HARVEST_DEMOTE_PACKAGE_ACTION_DESC = "Demotes the mentioned change package(s) from given from-state to to-state";
    public static final String DEMOTE_FROM_STATE_NAME = "From State";
    public static final String DEMOTE_FROM_STATE_NAME_DESC = "Demote From State";
    public static final String DEMOTE_TO_STATE_NAME = "To State";
    public static final String DEMOTE_TO_STATE_NAME_DESC = "Demote To State";
    public static final String DEMOTE_APPROVE_PACKAGE = "Approve Package first ?";
    public static final String DEMOTE_APPROVE_PACKAGE_DESC = "Approve the package before promoting it?";
    public static final String SET_CHECK_PACKAGE_DEPENDENCIES = "Verify Package Dependency";
    public static final String SET_CHECK_PACKAGE_DEPENDENCIES_DESC = "Sets the Verify Package Dependency";
    public static final String SET_PACKAGE_GROUP_BIND = "Enforce Package Bind";
    public static final String SET_PACKAGE_GROUP_BIND_DESC = "Sets the Enforce Package Bind";
    public static final String DEMOTE_STATUS = "Demote Status";
    public static final String DEMOTE_STATUS_DESC = "Demote Status";
    public static final String DEMOTE_PROCESS_NAME = "Demote Process Name";
    public static final String DEMOTE_PROCESS_NAME_DESC = "Name of the Demote Process. If it is left empty, then the system will automatically pick a demote process which belongs to given from state and to state";
    public static final String PACKAGE_GROUPS_NAME = "Package Group Names";
    public static final String PACKAGE_GROUPS_NAME_DESC = "One or more names of the Package groups";

    // Remove Items
    public static final String HARVEST_REMOVE_ITEMS = "Harvest - Remove Item";
    public static final String HARVEST_REMOVE_ITEMS_DESC = "Removes a list of items (only files) and places them in the selected package with D-tag";
    public static final String ITEM_PATH = "Item Path";
    public static final String ITEM_PATH_DESC = "Complete path from the repository to the folder which contains the items.<br>"
            + "<b> Example :TestRepo\\com\\ca where 'TestRepo' is the repository name and 'ca' contains the list of items";
    public static final String PLACEMENT = "Placement";
    public static final String PLACEMENT_DESC = "Placement options for the items to be removed.It can be either branch or trunk";
    public static final String ITEM_LIST = "Item List";
    public static final String ITEM_LIST_DESC = "List of Items to be removed.<b>Items should be comma separated.";
    public static final String ITEMS_REMOVED = "Removed Items";
    public static final String ITEMS_REMOVED_DESC = "List of removed items";
    public static final String ITEMS_UNAVAILABLE = "Unavailable Items";
    public static final String ITEMS_UNAVAILABLE_DESC = "List of unavailable items";
    public static final String ITEMS_FAILED = "Failed Items";
    public static final String ITEMS_FAILED_DESC = "List of failed items";

    // Delete Version
    public static final String HARVEST_DELETE_VERSIONS = "Harvest - Delete Version";
    public static final String HARVEST_DELETE_VESIONS_DESC = "Deletes the specified version(s)";
    public static final String VERSION_LIST = "Version List";
    public static final String VERSION_LIST_DESC = "List of versions to be deleted. <b>Versions should be comma seperated";
    public static final String VERSIONS_DELETED = "Deleted Versions";
    public static final String VERSIONS_DELETED_DESC = "List of deleted versions";
    public static final String VERSIONS_UNAVAILABLE = "Unavailable Versions";
    public static final String VERSIONS_UNAVAILABLE_DESC = "List of unavailable versions";
    public static final String VERSIONS_FAILED = "Failed Versions";
    public static final String VERSIONS_FAILED_DESC = "List of failed versions";

    public static final String BRANCH = "Branch";
    public static final String BRANCH_DESC = "Placement of the version";
    public static final String VERSION_VIEW = "Version View";
    public static final String VERSION_VIEW_DESC = "Versions based on its creation time";
    public static final String VERSION_TAG = "Version Tag";
    public static final String VERSION_TAG_DESC = "Tags associated with versions";
    public static final String ITEM_TYPE = "Item Type";
    public static final String ITEM_TYPE_DESC = "Type of item";
    public static final String STORAGE_TYPE = "Storage Type";
    public static final String STORAGE_TYPE_DESC = "Type of Storage";
    public static final String VERSION_VIEW_PATH_DESC = "Complete path from the repository to the folder which contains the versions.<br>"
            + "<b> Example :TestRepo\\com\\ca where 'TestRepo' is the repository name and 'ca' contains the list of versions";;
    public static final String VERSION_PACKAGE_NAME_DESC = "CA Harvest Package Name.<br>"
            + "<b>Only to be provided when 'Both' or 'Modified' option in Item type is choosen";

    // Create Item Path

    public static final String HARVEST_CREATE_ITEM_PATH = "Harvest - Create Path";
    public static final String HARVEST_CREATE_ITEM_PATH_DESC = "Creates a new Item path in mentioned Harvest repository";
    public static final String CREATE_ITEM_PATH_NAMES = "Path Names";
    public static final String CREATE_ITEM_PATH_NAMES_DESC = "List of new paths to be created.<br>"
            + "<b> For e.g path1\\subdirpath1 <br>";
    public static final String CREATE_ITEM_PARENT_PATH_NAME = "Parent Path";
    public static final String CREATE_ITEM_PARENT_PATH_NAME_DESC = "Complete path from the repository to the parent folder where path need to be created. <br>"
            + "<b> For e.g TestRepo\\com\\ca where TestRepo is repository name";
    public static final String CREATE_ITEM_CHANGE_PACKAGE_NAME = "Package Name";
    public static final String CREATE_ITEM_CHANGE_PACKAGE_NAME_DESC = "Package in the current state to associate with the new paths.";
    public static final String CREATE_ITEM_PLACEMENT_OPTION = "Placement Option";
    public static final String CREATE_ITEM_PLACEMENT_OPTION_DESC = "Choose between Trunk or Branch placement options for refactoring changes.";
    public static final String CREATE_ITEM_PATH_RESULT = "Create Item Path Result";
    public static final String CREATE_ITEM_PATH_RESULT_DESC = "Prints the result of the item path creation.";

    // Harvest Checkout by Agent Action
    public static final String HARVEST_CHECKOUT_BY_AGENT = "Harvest - Check-out by Agent";
    public static final String HARVEST_CHECKOUT_BY_AGENT_DESC = "Check-out files from a Harvest repository to a host accessed by the specified agent";
    public static final String AGENT_CLIENT_PATH = "Agent Client Path";
    public static final String AGENT_CLIENT_PATH_DESC = "Specifies the client path on the Agent machine";

}
