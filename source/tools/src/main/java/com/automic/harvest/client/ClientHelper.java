package com.automic.harvest.client;

import com.automic.harvest.base.AbstractAction;
import com.automic.harvest.cli.Cli;
import com.automic.harvest.cli.CliOptions;
import com.automic.harvest.constants.Constants;
import com.automic.harvest.constants.ExceptionConstants;
import com.automic.harvest.exception.AutomicException;
import com.automic.harvest.util.ConsoleWriter;
import com.ca.cmsdk.CmsdkException;

/**
 * Class to delegate the parameters to the respective classes based on the Action parameter.
 *
 */
public class ClientHelper {
    private static final String ABSOLUTEPATH = "com.automic.harvest.actions";

    private ClientHelper() {
    }

    /**
     * Method to delegate parameters to an instance of {@link AbstractAction} based on the value of Action parameter.
     *
     * @param actionParameters
     *            of options with key as option name and value is option value
     * @throws AutomicException
     * @throws HarvestException 
     * @throws CmsdkException 
     */

    public static void executeAction(String[] actionParameters) throws AutomicException, CmsdkException{
        String actionName = new Cli(new CliOptions(), actionParameters).getOptionValue(Constants.ACTION);

        AbstractAction action = null;
        try {
            Class<?> classDefinition = Class.forName(getCanonicalName(actionName));
            action = (AbstractAction) classDefinition.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            ConsoleWriter.writeln(e);
            String msg = String.format(ExceptionConstants.INVALID_INPUT_PARAMETER, Constants.ACTION, actionName);
            throw new AutomicException(msg);
        }
        action.executeAction(actionParameters);
    }

    private static String getCanonicalName(String clsName) {
        return ABSOLUTEPATH + "." + clsName;
    }
}
