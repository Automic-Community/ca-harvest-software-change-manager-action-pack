package com.automic.harvest.actions;

import com.automic.harvest.actions.composite.CheckInAction;
import com.automic.harvest.base.HarvestProcessAction;
import com.automic.harvest.exception.AutomicException;
import com.ca.cmsdk.CmsdkException;
import com.ca.cmsdk.process.ProcessType;

/**
 * This action will help to check-in local file(s) to Harvest repository.
 * 
 * 
 * @author vijendraparmar
 * 
 */
public class CheckInLocalAction extends HarvestProcessAction {

    final CheckInAction checkinAction;

    /**
     * Initializes a newly created {@code CheckInLocalAction}
     */
    public CheckInLocalAction() {
        this.checkinAction = new CheckInAction(this);
    }

    @Override
    protected void executeSpecific() throws AutomicException {
        validateInputs();
        try {
            checkinAction.executeCheckin(process, projectName, projectState, checkinAction.getLocalAgent());
        } catch (CmsdkException e) {
            throw new AutomicException(e.getMessage());
        }
    }

    public void validateInputs() throws AutomicException {
        super.validateInputs();
        checkinAction.validateInputs();
    }

    @Override
    protected ProcessType getProcessType() {
        return ProcessType.CHECKIN_PROCESS;
    }

}
