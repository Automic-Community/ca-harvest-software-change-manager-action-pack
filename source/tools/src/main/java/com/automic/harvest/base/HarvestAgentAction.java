package com.automic.harvest.base;

import com.automic.harvest.constants.Constants;
import com.automic.harvest.constants.Resource;
import com.automic.harvest.exception.AutomicException;
import com.automic.harvest.util.CommonUtil;
import com.automic.harvest.util.InputValidators;
import com.ca.cmsdk.CmsdkException;
import com.ca.cmsdk.fileAgent.FileAgent;

/**
 * This class create and initialize Harvest agent
 * 
 *
 */
public abstract class HarvestAgentAction extends HarvestProcessAction {

    protected FileAgent agent;
    private String agentMachineName;
    private int agentMachinePort;
    private String agentUserName;
    private String agentPassword;

    /**
     * Initializes a newly created {@code HarvestAgentAction}
     */
    public HarvestAgentAction() {
        addOption("agenthost", true, "Agent Machine Name");
        addOption("agentport", true, "Agent Port");
        addOption("agentusername", true, "Agent Username");
    }

    /**
     * Connect to harvest agent
     * @throws AutomicException
     */
    protected void connectToAgent() throws AutomicException {
        try {
            agent = new FileAgent(null, agentMachinePort, agentMachineName.trim(), agentUserName.trim(),
                    agentPassword.toString());
        } catch (CmsdkException e) {
            throw new AutomicException(e.getMessage());
        }
    }

    @Override
    public void validateInputs() throws AutomicException {
        super.validateInputs();
        agentMachineName = getOptionValue("agenthost");
        InputValidators.checkMandatoryParameter(agentMachineName, "Agent Machine Name");

        InputValidators.checkMandatoryParameter(getOptionValue("agentport"), "Agent Port");
        agentMachinePort = CommonUtil.parseStringValue(getOptionValue("agentport"), 0);
        InputValidators.checkPort(agentMachinePort, Resource.AGENT_PORT);

        agentUserName = getOptionValue("agentusername");
        InputValidators.checkMandatoryParameter(agentUserName, "Agent Username");

        agentPassword = System.getenv(Constants.AGENT_PASSWORD);
        InputValidators.checkMandatoryParameter(agentPassword, "Agent Password");

    }
}
