package com.automic.harvest.actions;

import com.automic.harvest.actions.composite.CheckOutAction;
import com.automic.harvest.base.HarvestProcessAction;
import com.automic.harvest.exception.AutomicException;
import com.ca.cmsdk.ChangePackage;
import com.ca.cmsdk.CmsdkException;
import com.ca.cmsdk.process.CheckoutProcess;
import com.ca.cmsdk.process.ProcessType;

/**
 * This action Check-out files from a Harvest repository to local host.
 * 
 * 
 * @author vijendraparmar
 * 
 */
public class CheckOutLocalAction extends HarvestProcessAction {

    final CheckOutAction checkOutAction;

    /**
     * Initializes a newly created {@code CheckOutLocalAction}
     */
    public CheckOutLocalAction() {
        this.checkOutAction = new CheckOutAction(this);
    }

    @Override
    protected void executeSpecific() throws AutomicException {
        validateInputs();
        CheckoutProcess checkoutProcess = null;
        try {
            ChangePackage cp = null;
            checkoutProcess = (CheckoutProcess) process;
            checkOutAction.initCheckoutMode(checkoutProcess);
            cp = checkOutAction.findPackage(projectState);
            checkOutAction.executeCheckout(cp, checkoutProcess, projectState, checkOutAction.getLocalAgent());

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
