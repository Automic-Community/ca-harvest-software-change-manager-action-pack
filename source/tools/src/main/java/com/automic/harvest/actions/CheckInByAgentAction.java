package com.automic.harvest.actions;

import com.automic.harvest.actions.composite.CheckInAction;
import com.automic.harvest.base.HarvestAgentAction;
import com.automic.harvest.exception.AutomicException;
import com.ca.cmsdk.CmsdkException;
import com.ca.cmsdk.process.ProcessType;

/**
 * This action Check-in file(s) to Harvest repository using a specified file agent.
 * 
 * @author asthasingh
 *
 */
public class CheckInByAgentAction extends HarvestAgentAction {

    final CheckInAction checkInAction;

    /**
     * Initializes a newly created {@code CheckInByAgentAction}
     */
    public CheckInByAgentAction() {
        this.checkInAction = new CheckInAction(this);
    }

    @Override
    protected void executeSpecific() throws AutomicException {

        validateInputs();
        connectToAgent();
        try {
            checkInAction.executeCheckin(process, projectName, projectState, checkInAction.getRemoteAgent(agent));
        } catch (CmsdkException e) {
            throw new AutomicException(e.getMessage());
        }
    }

    @Override
    public void validateInputs() throws AutomicException {
        super.validateInputs();
        checkInAction.validateInputs();
    }

    @Override
    protected ProcessType getProcessType() {
        return ProcessType.CHECKIN_PROCESS;
    }

}
