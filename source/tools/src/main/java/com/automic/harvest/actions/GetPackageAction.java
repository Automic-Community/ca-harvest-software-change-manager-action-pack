package com.automic.harvest.actions;

import com.automic.harvest.base.HarvestProjectAction;
import com.automic.harvest.constants.ExceptionConstants;
import com.automic.harvest.constants.Resource;
import com.automic.harvest.exception.AutomicException;
import com.automic.harvest.util.CommonUtil;
import com.automic.harvest.util.ConsoleWriter;
import com.automic.harvest.util.InputValidators;
import com.ca.cmsdk.ChangePackage;
import com.ca.cmsdk.ChangePackageChooser;
import com.ca.cmsdk.CmsdkException;
import com.ca.cmsdk.lists.ChangePackageList;

/**
 * @author asthasingh
 *
 */
public class GetPackageAction extends HarvestProjectAction {

    ChangePackage getPkg;
    private String packageName;
    private String pkgDescription;
    private String outAssigned;
    private String outStateName;
    private String[] formList;
    private String[] groupList;
    private String viewName;
    private String outApproved;
    private String status;

    /**
     * Initializes a newly created {@code GetPackageAction}
     */
    public GetPackageAction() {
        addOption("packagename", true, "Package Name");
    }

    @Override
    protected void executeSpecific() throws AutomicException {

        validateInputs();
        ChangePackageChooser packageChooser = new ChangePackageChooser(session);
        packageChooser.setChangePackageName(packageName, 0);
        try {
            packageChooser.execute();
            ChangePackageList packagelist = packageChooser.getChangePackageList();
            if (packagelist == null || packagelist.isEmpty()) {
                throw new AutomicException(CommonUtil
                        .formatErrorMessage(String.format(ExceptionConstants.PACKAGE_NOT_FOUND_FAILURE, packageName)));

            }
            getPkg = (ChangePackage) packagelist.get(0);
            if (getPkg != null) {
                getPkg.get();
                status = getPkg.getStatus();
                viewName = getPkg.getState().getViewName();
                outStateName = getPkg.getStateName();
                outApproved = getPkg.getApproved();
                outAssigned = getPkg.getAssignedUserName();
                pkgDescription = getPkg.getDescription();
                groupList = getPkg.getChangePackageGroupList().getNames().trim().split("\n");
                formList = getPkg.getFormList().getNames().trim().split("\n");
            } else {
                throw new AutomicException(CommonUtil
                        .formatErrorMessage(String.format(ExceptionConstants.PACKAGE_NOT_FOUND_FAILURE, packageName)));
            }
        } catch (CmsdkException e) {
            throw new AutomicException(e.getMessage());
        }
        prepareOutput();
        ConsoleWriter.writeln("Package details retrieved successfully");

    }

    private void prepareOutput() {
        StringBuilder forms = new StringBuilder();
        StringBuilder grps = new StringBuilder();
        for (String s : formList) {
            forms.append(s.trim()).append(",");
        }
        forms.deleteCharAt(forms.lastIndexOf(","));
        for (String s : groupList) {
            grps.append(s.trim()).append(",");
        }
        grps.deleteCharAt(grps.lastIndexOf(","));
        ConsoleWriter.writeln("UC4RB_HRV_PCK_DESC::=" + pkgDescription);
        ConsoleWriter.writeln("UC4RB_HRV_USER::=" + outAssigned);
        ConsoleWriter.writeln("UC4RB_HRV_STATE::=" + outStateName);
        ConsoleWriter.writeln("UC4RB_HRV_FORMS::=" + forms);
        ConsoleWriter.writeln("UC4RB_HRV_PCK_GRP::=" + grps);
        ConsoleWriter.writeln("UC4RB_HRV_VIEW::=" + viewName);
        ConsoleWriter.writeln("UC4RB_HRV_IS_APPROVED::=" + outApproved);
        ConsoleWriter.writeln("UC4RB_HRV_STATUS::=" + status);
    }

    @Override
    public void validateInputs() throws AutomicException {
        super.validateInputs();
        packageName = getOptionValue("packagename");
        InputValidators.checkMandatoryParameter(packageName, Resource.PACKAGE_NAME);
    }
}
