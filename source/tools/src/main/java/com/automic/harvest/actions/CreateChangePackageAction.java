
package com.automic.harvest.actions;

import com.automic.harvest.base.HarvestProcessAction;
import com.automic.harvest.constants.Resource;
import com.automic.harvest.exception.AutomicException;
import com.automic.harvest.util.CommonUtil;
import com.automic.harvest.util.InputValidators;
import com.ca.cmsdk.ChangePackage;
import com.ca.cmsdk.CmsdkException;
import com.ca.cmsdk.common.CmsdkConstants;
import com.ca.cmsdk.process.CreateChangePackageProcess;
import com.ca.cmsdk.process.ProcessType;

/**
 * This action is used to create a new change package in the given Harvest project.
 * 
 * @author asthasingh
 *
 */

public class CreateChangePackageAction extends HarvestProcessAction {

    CreateChangePackageProcess createPkgProcess = null;

    private String packageName;

    private String packageDesc;

    /**
     * Initializes a newly created {@code CreateChangePackageAction}
     */
    public CreateChangePackageAction() {
        addOption("packagename", false, "Package Name");
        addOption("packagedesc", false, "Package Description");

    }

    @Override
    protected void executeSpecific() throws AutomicException {
        try {
            validateInputs();

            ChangePackage newPkg = new ChangePackage(CmsdkConstants.NULL_OBJID, projectState);
            createPkgProcess = (CreateChangePackageProcess) process;
            if (!createPkgProcess.getDisableNameChange()) {
                if (CommonUtil.checkNotEmpty(packageName)) {
                    newPkg.setName(packageName);
                } else if (!CommonUtil.checkNotEmpty(createPkgProcess.getDefaultName())) {
                    InputValidators.checkMandatoryParameter(packageName, Resource.PACKAGE_NAME);
                }
            }
            if (CommonUtil.checkNotEmpty(packageDesc)) {
                newPkg.setDescription(packageDesc);
            }

            createPkgProcess.execute(newPkg);
        } catch (CmsdkException e) {
            throw new AutomicException(e.getMessage());
        }

    }

    @Override
    public void validateInputs() throws AutomicException {
        super.validateInputs();
        packageName = getOptionValue("packagename");
        packageDesc = getOptionValue("packagedesc");

    }

    @Override
    protected ProcessType getProcessType() {
        return ProcessType.CREATEPACKAGE_PROCESS;
    }

}
