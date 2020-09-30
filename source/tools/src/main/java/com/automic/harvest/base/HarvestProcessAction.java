package com.automic.harvest.base;

import com.automic.harvest.constants.ExceptionConstants;
import com.automic.harvest.constants.Resource;
import com.automic.harvest.exception.AutomicException;
import com.automic.harvest.util.CommonUtil;
import com.automic.harvest.util.InputValidators;
import com.ca.cmsdk.CmsdkException;
import com.ca.cmsdk.Filter;
import com.ca.cmsdk.State;
import com.ca.cmsdk.lists.ProcessList;
import com.ca.cmsdk.lists.StateList;
import com.ca.cmsdk.process.ProcessObject;
import com.ca.cmsdk.process.ProcessType;

/**
 * This class is used find state and process then run process related actions.Child class will override
 * runHarvestProcessAction().
 * 
 * @author asthasingh
 *
 */
public abstract class HarvestProcessAction extends HarvestProjectAction {

    protected String stateName = "";
    protected State projectState;
    protected ProcessObject process;
    private String processName = "";

    /**
     * Initializes a newly created {@code CheckInAction}
     * 
     */
    public HarvestProcessAction() {
        addOption("statename", true, "State Name");
        addOption("processname", false, "Process Name");
    }

    /**
     * checks whether project with the given state exists or not
     * 
     * @return true/false
     * @throws CmsdkException
     */
    private boolean findState() throws CmsdkException {
        String sStateName = stateName;
        if (harvestProject != null) {
            StateList stateList = harvestProject.getStateList();
            for (int i = 0; i < stateList.size(); i++) {
                State state = (State) stateList.get(i);
                if (state.getName().equals(sStateName)) {
                    projectState = state;
                    break;
                }
            }
        }
        return (projectState != null);
    }

    /**
     * checks whether process exists or not
     * 
     * @return true/false
     * @throws CmsdkException
     */
    private boolean findProcess() throws CmsdkException {
        Filter filter = new Filter();
        filter.setProcessFilter(getProcessType());
        ProcessList procList = projectState.getProcessList(filter);

        String sProcessName = processName;
        if (procList != null && procList.size() > 0) {
            if (CommonUtil.checkNotEmpty(sProcessName)) {
                process = (ProcessObject) procList.find(sProcessName);
            } else {
                process = (ProcessObject) procList.get(0);
            }
        }
        return (process != null);
    }

    @Override
    public void validateInputs() throws AutomicException {
        super.validateInputs();
        stateName = getOptionValue("statename");
        processName = getOptionValue("processname");
        InputValidators.checkMandatoryParameter(stateName, Resource.STATE_NAME);
        try {
            if (!findState()) {
                throw new AutomicException(String.format(ExceptionConstants.STATE_NOT_FOUND_FAILURE, stateName));
            }
            if (!findProcess()) {
                throw new AutomicException(String.format(ExceptionConstants.PROCESS_NOT_FOUND_FAILURE, processName));
            }
        } catch (CmsdkException e) {
            throw new AutomicException(e.getMessage());
        }
    }

    /**
     * get the ProcessType
     * 
     * @return ProcessType
     */
    abstract protected ProcessType getProcessType();

}
