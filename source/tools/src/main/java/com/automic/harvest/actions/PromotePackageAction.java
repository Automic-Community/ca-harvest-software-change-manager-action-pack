package com.automic.harvest.actions;

import java.util.ArrayList;
import java.util.Iterator;

import com.automic.harvest.base.HarvestProjectAction;
import com.automic.harvest.constants.ExceptionConstants;
import com.automic.harvest.exception.AutomicException;
import com.automic.harvest.util.CommonUtil;
import com.automic.harvest.util.ConsoleWriter;
import com.automic.harvest.util.InputValidators;
import com.ca.cmsdk.ChangePackage;
import com.ca.cmsdk.ChangePackageGroup;
import com.ca.cmsdk.CmsdkAuthorizationException;
import com.ca.cmsdk.CmsdkException;
import com.ca.cmsdk.Filter;
import com.ca.cmsdk.State;
import com.ca.cmsdk.common.CmsdkConstants;
import com.ca.cmsdk.internal.comm.CmsdkNonFatalCommException;
import com.ca.cmsdk.lists.ChangePackageGroupList;
import com.ca.cmsdk.lists.ChangePackageList;
import com.ca.cmsdk.lists.ExecuteResultList;
import com.ca.cmsdk.lists.ProcessList;
import com.ca.cmsdk.lists.StateList;
import com.ca.cmsdk.process.ExecuteResult;
import com.ca.cmsdk.process.ProcessType;
import com.ca.cmsdk.process.PromoteProcess;

/**
 * This action will promote the mentioned change package(s) from the given from-state to to-state.
 * 
 * @author yogitadalal
 *
 */
public class PromotePackageAction extends HarvestProjectAction {

    protected static long processId = CmsdkConstants.NULL_OBJID;
    PromoteProcess promotePrc = null;
    String promotePackageStatus = "";
    private State pkgFromState;
    private String promoteProcessName;
    private String[] promotePackageNames;
    private String[] promotePackageGroupNames;

    private String fromState;
    private String toState;
    private Boolean enforcePackageBind;
    private Boolean enforcePackageMerge;
    private Boolean verifyDepandantPackages;

    /**
     * Initializes a newly created {@code PromotePackageAction}
     */
    public PromotePackageAction() {
        addOption("fromstate", true, "From State");
        addOption("tostate", true, "To State");
        addOption("packgroups", false, "Package Group Names");
        addOption("packnames", false, "Package Names");
        addOption("processname", false, "Promote Process Name");
        addOption("enforcebind", true, "Enforce Pacakge Bind");
        addOption("enforcemerge", true, "Enforce Pacakge Merge");
        addOption("dependency", true, "Verify Package Dependency");

    }

    @Override
    protected void executeSpecific() throws AutomicException {
        validateInputs();

        try {

            if (findFromState(fromState.trim())) {
                if (findToState(toState.trim())) {
                    if (findPromoteProcess(fromState.trim(), toState.trim())) {

                        ChangePackageList cpl1 = null;

                        if (promotePackageNames != null) {

                            int pkgCnt = 0;

                            ChangePackageList cpl = pkgFromState.getChangePackageList();
                            cpl1 = new ChangePackageList(CmsdkConstants.NULL_OBJID);

                            if (!cpl.isEmpty()) {

                                for (int i = 0; i < cpl.size(); i++) {
                                    for (int j = 0; j < promotePackageNames.length; j++) {

                                        if (CommonUtil.checkNotEmpty(promotePackageNames[j])
                                                && (((ChangePackage) cpl.get(i)).getName()
                                                        .equals(promotePackageNames[j].trim()))) {
                                            cpl1.add(cpl.get(i));
                                            ++pkgCnt;
                                        }
                                    }
                                }

                                if (pkgCnt != promotePackageNames.length) {
                                    throw new AutomicException(ExceptionConstants.INVALID_PACKAGE_LIST);
                                }

                            } else {
                                throw new AutomicException(ExceptionConstants.INVALID_FROM_STATE_PACKAGE_LIST);
                            }

                        }

                        ChangePackageGroupList pglist1 = null;

                        if (promotePackageGroupNames != null) {

                            int pkgGrpCnt = 0;

                            ChangePackageGroupList pglist = pkgFromState.getChangePackageGroupList();
                            pglist1 = new ChangePackageGroupList(CmsdkConstants.NULL_OBJID);

                            if (!pglist.isEmpty()) {

                                for (int i = 0; i < pglist.size(); i++) {
                                    for (int j = 0; j < promotePackageGroupNames.length; j++) {

                                        if (CommonUtil.checkNotEmpty(promotePackageGroupNames[j])
                                                && (((ChangePackageGroup) pglist.get(i)).getName()
                                                        .equals(promotePackageGroupNames[j].trim()))) {
                                            pglist1.add(pglist.get(i));
                                            ++pkgGrpCnt;
                                        }
                                    }
                                }

                                if (pkgGrpCnt != promotePackageGroupNames.length) {
                                    throw new AutomicException(ExceptionConstants.INVALID_PACKAGE_GROUPS);
                                }
                            } else {
                                throw new AutomicException(ExceptionConstants.INVALID_FROM_STATE_PACKAGE_GROUP);
                            }
                        }
                        promotePackages(cpl1, pglist1);
                    } else {
                        throw new AutomicException(ExceptionConstants.PROMOTION_FAILURE);
                    }
                } else {
                    throw new AutomicException(ExceptionConstants.TOSTATE_NOT_FOUND);
                }
            } else {
                throw new AutomicException(ExceptionConstants.FROMSTATE_NOT_FOUND);
            }
        }

        catch (CmsdkException e) {
            throw new AutomicException(e.getMessage() + ": " + e);
        }

        ExecuteResultList results = promotePrc.getExecuteResultList();
        ArrayList<String> result = new ArrayList<>();

        Iterator<?> iter = results.iterator();

        while (iter.hasNext()) {

            ExecuteResult er = (ExecuteResult) iter.next();

            if (!er.isSuccess()) {
                throw new AutomicException(ExceptionConstants.PKG_PROMOTION_FAILURE + ": " + er);

            }
            if (!result.toString().isEmpty()) {

                result.add(er.getMessage().replace("I00020017:", "").replace(".", ""));
            }
        }

        promotePackageStatus = result.toString();
        ConsoleWriter.writeln("Packages are promoted successfully");
        ConsoleWriter.writeln("UC4RB_PACKAGE_STATUS::=" + promotePackageStatus);
    }

    public boolean findToState(String fromState) {

        State pkgToState = null;

        if (harvestProject != null) {
            try {
                StateList stateList = harvestProject.getStateList();

                for (int i = 0; i < stateList.size(); i++) {
                    State state = (State) stateList.get(i);
                    if (state.getName().equals(fromState)) {

                        pkgToState = state;
                        break;
                    }
                }
            } catch (Exception e) {
                ConsoleWriter.writeln(CommonUtil.formatErrorMessage("Unable to locate from state: " + e));
            }
        }
        if (pkgToState == null) {
            ConsoleWriter.writeln(CommonUtil.formatErrorMessage("State not found: " + fromState));
        }

        return (pkgToState != null);
    }

    public boolean findFromState(String fromState) {
        pkgFromState = null;

        if (harvestProject != null) {
            try {
                StateList stateList = harvestProject.getStateList();
                for (int i = 0; i < stateList.size(); i++) {
                    State state = (State) stateList.get(i);
                    if (state.getName().equals(fromState)) {
                        pkgFromState = state;
                        break;
                    }
                }
            } catch (Exception e) {
                ConsoleWriter.writeln(
                        CommonUtil.formatErrorMessage("Unable to locate State \"" + fromState + "\" in project:" + e));
            }
        }
        if (pkgFromState == null) {
            ConsoleWriter.writeln(CommonUtil.formatErrorMessage("State not found: " + fromState));
        }
        return (pkgFromState != null);
    }

    public boolean findPromoteProcess(String fromState, String toState) throws AutomicException {
        promotePrc = null;
        PromoteProcess process = null;

        try {
            Filter filter = new Filter();
            filter.setProcessFilter(ProcessType.PROMOTE_PROCESS);
            ProcessList processList = pkgFromState.getProcessList(filter);

            if (CommonUtil.checkNotEmpty(promoteProcessName)) {
                process = (PromoteProcess) processList.find(promoteProcessName);
                if (process != null) {

                    String tostatename = process.getToStateName();
                    String fromstatename = process.getState().getName();

                    if (fromstatename.equals(fromState) && tostatename.equals(toState)) {
                        promotePrc = process;
                        promotePrc.setCheckChangePackageGroupBind(enforcePackageBind);
                        promotePrc.setCheckChangePackageDependencies(verifyDepandantPackages);
                        promotePrc.setCheckChangePackageDependencies(enforcePackageMerge);
                    } else {

                        throw new AutomicException(
                                String.format(ExceptionConstants.PKG_PROMOTE_FAILURE_TOSTATE, toState));
                    }
                } else {

                    throw new AutomicException(
                            String.format(ExceptionConstants.PKG_PROMOTE_FAILURE_FROMSTATE, toState));

                }
            } else {

                for (int i = 0; i < processList.size(); i++) {
                    process = (PromoteProcess) processList.get(i);

                    String tostatename = process.getToStateName();
                    String fromstatename = process.getState().getName();

                    if (fromstatename.equals(fromState) && tostatename.equals(toState)) {

                        promotePrc = process;
                        promotePrc.setCheckChangePackageGroupBind(enforcePackageBind);
                        promotePrc.setCheckChangePackageDependencies(verifyDepandantPackages);
                        promotePrc.setCheckChangePackageDependencies(enforcePackageMerge);
                        ConsoleWriter.writeln("Promote Process name which is picked from the avialable process list: "
                                + process.getName());

                        break;
                    }
                }
            }

        } catch (CmsdkNonFatalCommException e) {
            promotePrc = null;
            ConsoleWriter.writeln(CommonUtil.formatErrorMessage(e.getMessage()));

        } catch (Exception ex) {
            promotePrc = null;
            ConsoleWriter.writeln(CommonUtil.formatErrorMessage(ex.getMessage()));

        }

        return (promotePrc != null);
    }

    public void promotePackages(ChangePackageList packages, ChangePackageGroupList packageGroup) {

        try {

            promotePrc.execute(packages, packageGroup, null, null);
        } catch (CmsdkAuthorizationException e) {
            ConsoleWriter.writeln(CommonUtil.formatErrorMessage(e.getMessage()));
        } catch (CmsdkException ex) {
            ConsoleWriter.writeln(CommonUtil.formatErrorMessage(ex.getMessage()));
        }
    }

    /**
     * Validate Inputs
     * 
     * @throws HarvestException
     */
    @Override
    public void validateInputs() throws AutomicException {
        super.validateInputs();
        fromState = getOptionValue("fromstate");
        InputValidators.checkMandatoryParameter(fromState, "From State");
        toState = getOptionValue("tostate");
        InputValidators.checkMandatoryParameter(toState, "To State");

        String promotePackageGroups = getOptionValue("packgroups");
        String promotePackages = getOptionValue("packnames");
        if (!((CommonUtil.checkNotEmpty(promotePackageGroups)) ^ (CommonUtil.checkNotEmpty(promotePackages)))) {
            throw new AutomicException(ExceptionConstants.PKG_NAME_GRPNAME_EXCLUSIVE);
        }
        if (CommonUtil.checkNotEmpty(promotePackageGroups)) {
            promotePackageGroupNames = promotePackageGroups.split(",");
        }
        if (CommonUtil.checkNotEmpty(promotePackages)) {
            promotePackageNames = promotePackages.split(",");
        }
        promoteProcessName = getOptionValue("processname");
        enforcePackageBind = CommonUtil.convert2Bool(getOptionValue("enforcebind"));
        InputValidators.checkMandatoryParameter(enforcePackageBind.toString(), "Enforce Pacakge Bind");

        enforcePackageMerge = CommonUtil.convert2Bool(getOptionValue("enforcemerge"));
        InputValidators.checkMandatoryParameter(enforcePackageMerge.toString(), "Enforce Pacakge Merge");

        verifyDepandantPackages = CommonUtil.convert2Bool(getOptionValue("dependency"));
        InputValidators.checkMandatoryParameter(verifyDepandantPackages.toString(), "Verify Package Dependency");

    }
}
