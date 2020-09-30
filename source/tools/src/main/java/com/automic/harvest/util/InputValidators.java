package com.automic.harvest.util;

import com.automic.harvest.constants.ExceptionConstants;
import com.automic.harvest.exception.AutomicException;

/**
 * 
 * @author vijendraparmar
 *
 */
public class InputValidators {

    /**
     * Port minimum value
     */
    private static final int PORT_MIN = 1;
    /**
     * Port maximum value
     */
    private static final int PORT_MAX = 65536;

    /**
     * validate port input
     *
     * @throws AutomicException
     */
    public static void checkPort(Integer port, String paramName) throws AutomicException {
        if (port < PORT_MIN || port > PORT_MAX) {
            throw new AutomicException(String.format(ExceptionConstants.INVALID_PORT, PORT_MIN, PORT_MAX, port));
        }
    }

    /**
     * This method check provided {@code String} object is not null and empty
     * 
     * @param parameter
     *            {@code String} object
     * @param parameterName
     *            {@code String} object
     * @throws AutomicException
     *             Invalid value for parameter
     */
    public static final void checkMandatoryParameter(String parameter, String parameterName) throws AutomicException {
        if (!CommonUtil.checkNotEmpty(parameter)) {
            throw new AutomicException(
                    String.format(ExceptionConstants.INVALID_INPUT_PARAMETER, parameterName, parameter));
        }
    }

    private InputValidators() {
    }
}
