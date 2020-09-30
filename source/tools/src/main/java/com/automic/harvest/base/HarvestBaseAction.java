package com.automic.harvest.base;

import java.io.File;
import java.lang.reflect.Field;

import com.automic.harvest.constants.Constants;
import com.automic.harvest.constants.ExceptionConstants;
import com.automic.harvest.constants.Resource;
import com.automic.harvest.exception.AutomicException;
import com.automic.harvest.util.CommonUtil;
import com.automic.harvest.util.ConsoleWriter;
import com.automic.harvest.util.InputValidators;
import com.ca.cmsdk.CmsdkAuthorizationException;
import com.ca.cmsdk.CmsdkException;
import com.ca.cmsdk.session.Application;
import com.ca.cmsdk.session.Broker;
import com.ca.cmsdk.session.Session;
import com.ca.cmsdk.session.SessionOptions;

/**
 * This class is used to establish connection with broker.It provides some initializations and validations on common
 * inputs .
 * 
 * @author asthasingh
 *
 */

public abstract class HarvestBaseAction extends AbstractAction {

    protected Session session = null;
    private Application scmApp = null;

    private String broker;

    private int brokerPort = 5101;

    private String username;

    private String password;

    /**
     * Initializes a newly created {@code HarvestBaseAction}
     */
    public HarvestBaseAction() {
        addOption("broker", true, "Broker Name");
        addOption("brokerport", true, "Broker Port");
        addOption("username", true, "UserName");
    }

    @Override
    public void execute() throws AutomicException {
        boolean libraryPathUpdated = false;
        try {

        	validateBaseInputs();
            String harvestPath = System.getenv("CA_SCM_HOME");
            
            File file = null;
            if (CommonUtil.checkNotEmpty(harvestPath)) {
            	file = new File(harvestPath);
            }
            
            if (file== null ||  !(file.exists() && file.isDirectory())) {
                throw new AutomicException(ExceptionConstants.SCM_PATH_ERROR);
            }
            
            if (File.pathSeparatorChar == ':') {
            	harvestPath += File.separator + "lib";
            }
            
            ConsoleWriter.writeln("CA SCM Home is set as : "+harvestPath);
            
            String libraryPath = System.getProperty("java.library.path");
            ConsoleWriter.writeln("Java Library Path is set as : "+libraryPath);
            String[] _paths = libraryPath.split(File.pathSeparator);
            for (String string : _paths) {
                if (string.equalsIgnoreCase(harvestPath)) {
                	libraryPathUpdated = true;
                    break;
                }
            }
            
            // This enables the java.library.path to be modified at runtime
            // From a Sun engineer at
            // http://forums.sun.com/thread.jspa?threadID=707176
        	if(!libraryPathUpdated) {
        		Field field;
                try {
                	field = ClassLoader.class.getDeclaredField("usr_paths");
                    field.setAccessible(true);
                    String[] paths = (String[]) field.get(null);
                    for (int i = 0; i < paths.length; i++) {
                    	if (harvestPath.equals(paths[i])) {
                    		return;
                    	}
                    }
                    String[] tmp = new String[paths.length + 1];
                    System.arraycopy(paths, 0, tmp, 0, paths.length);
                    tmp[paths.length] = harvestPath;
                    field.set(null, tmp);
                    
                    System.setProperty("java.library.path",System.getProperty("java.library.path") + File.pathSeparator + harvestPath);
                    } catch (NoSuchFieldException | SecurityException | IllegalAccessException | IllegalArgumentException e) {
                        throw new AutomicException(e.getMessage());
                    }
        	}
        	
        	if (connectBroker()) {
                executeSpecific();
            } else {
                throw new AutomicException(ExceptionConstants.CONNECT_FAILURE);
            }
        	
        } finally {
            disconnectBroker();
        }

    }

    /**
     * runs the harvest action and provide the result
     * 
     * @return
     * @throws CmsdkAuthorizationException
     * @throws CmsdkException
     * @throws AutomicException
     */

    protected abstract void executeSpecific() throws AutomicException;

    /**
     * Validate Inputs
     * 
     * @throws AutomicException
     */
    public void validateBaseInputs() throws AutomicException {
        broker = getOptionValue("broker");
        InputValidators.checkMandatoryParameter(broker, Resource.BROKER_NAME);
        InputValidators.checkMandatoryParameter(getOptionValue("brokerport"), Resource.BROKER_PORT);
        brokerPort = CommonUtil.parseStringValue(getOptionValue("brokerport"), 5101);
        InputValidators.checkPort(brokerPort, Resource.BROKER_PORT);
        username = getOptionValue("username");
        InputValidators.checkMandatoryParameter(username, Resource.USER_NAME);
        password = System.getenv(Constants.ENV_PASSWORD);
        if (!CommonUtil.checkNotEmpty(password)) {
            throw new AutomicException(ExceptionConstants.PASSWORD_FAILURE);
        }
    }

    /**
     * establishes the connection to the harvest broker with the given broker, username, password and port
     * 
     * @return true/false based on session creation
     * @throws CmsdkException
     * @throws AutomicException
     */

    public boolean connectBroker() throws AutomicException {

        if (scmApp == null) {
            try {
                scmApp = new Application();
            } catch (CmsdkException e) {
                throw new AutomicException(e.getMessage());
            }
        }

        try {
            session = scmApp.createSession(username, password, new Broker(broker, broker, brokerPort),
                    new SessionOptions());
        } catch (CmsdkException e) {
            session = null;
            throw new AutomicException(e.getMessage());
        }

        return (session != null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ca.usm.ucf.SCMServer#DisconnectBroker()
     */
    public void disconnectBroker() {

        if (session != null) {
            session.getParentApplication().closeSession(session);
        }
    }
}
