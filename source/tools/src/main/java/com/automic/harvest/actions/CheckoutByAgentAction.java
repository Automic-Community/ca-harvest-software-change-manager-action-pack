package com.automic.harvest.actions;

import com.automic.harvest.actions.composite.CheckOutAction;
import com.automic.harvest.base.HarvestAgentAction;
import com.automic.harvest.exception.AutomicException;
import com.ca.cmsdk.ChangePackage;
import com.ca.cmsdk.CmsdkException;
import com.ca.cmsdk.process.CheckoutProcess;
import com.ca.cmsdk.process.ProcessType;

/**
 * This action Check-out files from a Harvest repository to remote agent provided by user.
 * 
 * @author asthasingh
 * 
 */

public class CheckoutByAgentAction extends HarvestAgentAction {

    final CheckOutAction checkOutAction;

    public CheckoutByAgentAction() {
        this.checkOutAction = new CheckOutAction(this);
    }

    @Override
    protected void executeSpecific() throws AutomicException {

        validateInputs();
        connectToAgent();
        CheckoutProcess checkoutProcess = null;
        ChangePackage cp = null;
        try {
            checkoutProcess = (CheckoutProcess) process;
            checkOutAction.initCheckoutMode(checkoutProcess);
            cp = checkOutAction.findPackage(projectState);
            checkOutAction.executeCheckout(cp, checkoutProcess, projectState, checkOutAction.getRemoteAgent(agent));
        } catch (CmsdkException e) {
            throw new AutomicException(e.getMessage());
        }
    }

    @Override
    public void validateInputs() throws AutomicException {
        super.validateInputs();
        checkOutAction.validateInputs();
    }

    @Override
    protected ProcessType getProcessType() {
        return ProcessType.CHECKOUT_PROCESS;
    }
}
