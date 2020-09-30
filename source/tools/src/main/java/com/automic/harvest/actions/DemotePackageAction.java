/*******************************************************************************
 * Copyright (c) 2014 CA.  All rights reserved.
 * This software and all information contained therein is confidential and proprietary and shall
 * not be duplicated, used, disclosed or disseminated in any way except as authorized by the
 * applicable license agreement, without the express written permission of CA. All authorized
 * reproductions must be marked with this language.
 * 
 * EXCEPT AS SET FORTH IN THE APPLICABLE LICENSE AGREEMENT, TO THE EXTENT PERMITTED BY APPLICABLE
 * LAW, CA PROVIDES THIS SOFTWARE WITHOUT WARRANTY OF ANY KIND, INCLUDING WITHOUT LIMITATION, ANY
 * IMPLIED WARRANTIES OF MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.  IN NO EVENT WILL CA
 * BE LIABLE TO THE END USER OR ANY THIRD PARTY FOR ANY LOSS OR DAMAGE, DIRECT OR INDIRECT, FROM
 * THE USE OF THIS SOFTWARE, INCLUDING WITHOUT LIMITATION, LOST PROFITS, BUSINESS INTERRUPTION,
 * GOODWILL, OR LOST DATA, EVEN IF CA IS EXPRESSLY ADVISED OF SUCH LOSS OR DAMAGE.
 ******************************************************************************/
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
import com.ca.cmsdk.ChangePackageChooser;
import com.ca.cmsdk.ChangePackageGroup;
import com.ca.cmsdk.CmsdkException;
import com.ca.cmsdk.Filter;
import com.ca.cmsdk.State;
import com.ca.cmsdk.common.CmsdkConstants;
import com.ca.cmsdk.lists.ChangePackageGroupList;
import com.ca.cmsdk.lists.ChangePackageList;
import com.ca.cmsdk.lists.ExecuteResultList;
import com.ca.cmsdk.lists.ProcessList;
import com.ca.cmsdk.lists.StateList;
import com.ca.cmsdk.process.DemoteProcess;
import com.ca.cmsdk.process.ExecuteResult;
import com.ca.cmsdk.process.ProcessType;

/**
 * @author yogitadalal
 * 
 */

public class DemotePackageAction extends HarvestProjectAction {

    public static final String BOOL_DEFAULT = "false";

    protected State pkgToState = null;
    protected State pkgFromState = null;
    DemoteProcess demoteProcess = null;
    String projectToStateName;
    String projectFromStateName;

    private String processName;

    private String[] packageName;
    private String[] packageGroupsName;
    private String fromState;

    private String toState;

    private Boolean packageGroupBind = Boolean.valueOf(BOOL_DEFAULT);

    private Boolean packageDependencies = Boolean.valueOf(BOOL_DEFAULT);

    private String demoteStatus;

    /**
     * Initializes a newly created {@code DemotePackageAction}
     */
    public DemotePackageAction() {
        addOption("fromstate", true, "From State");
        addOption("tostate", true, "To State");
        addOption("packgroups", false, "Package Group Names");
        addOption("packnames", false, "Package Names");
        addOption("processname", false, "Demote Process Name");
        addOption("enforcebind", false, "Enforce Pacakge Bind");
        addOption("dependency", false, "Verify Package Dependency");

    }

    @Override
    protected void executeSpecific() throws AutomicException {
        validateInputs();
        try {
            if (findFromState(fromState.trim())) {
                if (findToState(toState.trim())) {
                    int pkgCnt = 0;

                    ChangePackageList fcp1 = pkgFromState.getChangePackageList();
                    ChangePackageList fcp11 = new ChangePackageList(CmsdkConstants.NULL_OBJID);
                    if (null != packageName) {
                        for (int k = 0; k < packageName.length; k++) {
                            if (CommonUtil.checkNotEmpty(packageName[k])) {
                                packageName[k] = packageName[k].trim();
                            }
                        }
                        if (!fcp1.isEmpty()) {
                            // Following loop verifies if the given packages in
                            // the package list are part of the given state
                            for (int i = 0; i < fcp1.size(); i++) {
                                for (int j = 0; j < packageName.length; j++) {
                                    if ((CommonUtil.checkNotEmpty(packageName[j]))
                                            && (((ChangePackage) fcp1.get(i)).getName().equals(packageName[j]))) {
                                        fcp11.add(fcp1.get(i));
                                        ++pkgCnt;

                                    }
                                }
                            }
                        }
                    }

                    int pkgGrpCnt = 0;

                    ChangePackageGroupList fcpg1 = pkgFromState.getChangePackageGroupList();
                    ChangePackageGroupList fcpg11 = new ChangePackageGroupList(CmsdkConstants.NULL_OBJID);

                    if (null != packageGroupsName) {
                        for (int k = 0; k < packageGroupsName.length; k++) {
                            if (CommonUtil.checkNotEmpty(packageGroupsName[k])) {
                                packageGroupsName[k] = packageGroupsName[k].trim();
                            }
                        }
                        if (!fcpg1.isEmpty()) {
                            // Following loop verifies if the given packages in
                            // the package list are part of the given state
                            for (int i = 0; i < fcpg1.size(); i++) {
                                for (int j = 0; j < packageGroupsName.length; j++) {
                                    if ((CommonUtil.checkNotEmpty(packageGroupsName[j]))
                                            && (((ChangePackageGroup) fcpg1.get(i)).getName()
                                                    .equals(packageGroupsName[j]))) {
                                        fcpg11.add(fcpg1.get(i));
                                        ++pkgGrpCnt;

                                    }
                                }
                            }
                        }
                    }

                    if (null != packageGroupsName && pkgGrpCnt != packageGroupsName.length) {
                        throw new AutomicException(ExceptionConstants.PACKAGE_GROUP_NOT_AVAILABLE);
                    }
                    if (null != packageName && pkgCnt != packageName.length) {
                        throw new AutomicException(ExceptionConstants.PACKAGE_NOT_AVAILABLE);
                    }

                    if ((null != fcp11) || (null != fcpg11)) {
                        // Demote process
                        if (findDemoteProcess(fromState.trim(), toState.trim())) {
                            if (demoteProcess != null) {
                                demoteProcess.execute(fcp11, fcpg11, null, null);
                                processResult();
                            }
                        } else {
                            throw new AutomicException(ExceptionConstants.DEMOTE_PROCESS_NOT_FOUND);
                        }
                    }
                } else {
                    throw new AutomicException(ExceptionConstants.INVALID_TO_STATE);
                }
            } else {
                throw new AutomicException(ExceptionConstants.INVALID_FROM_STATE);
            }
        } catch (CmsdkException e) {
            throw new AutomicException(e.getMessage() + ": " + e);
        }
        ConsoleWriter.writeln("Demote Action has executed successfully");
        ConsoleWriter.writeln("UC4RB_PACKAGE_STATUS::=" + demoteStatus);
    }

    private void processResult() throws AutomicException {
        ExecuteResultList results = demoteProcess.getExecuteResultList();
        ArrayList<String> result = new ArrayList<>();
        Iterator<?> iter = results.iterator();
        while (iter.hasNext()) {
            ExecuteResult er = (ExecuteResult) iter.next();
            if (!er.isSuccess()) {
                throw new AutomicException(ExceptionConstants.PACKAGE_DEMOTION_FAIL + ": " + er.getMessage());
            }
            result.add(er.getMessage().replace("I00020018:", "").replace(".", ""));
        }
        demoteStatus = result.toString();
    }

    public boolean findFromState(String stateName) {
        if (harvestProject != null) {
            try {
                StateList stateList = harvestProject.getStateList();
                for (int i = 0; i < stateList.size(); i++) {
                    State state = (State) stateList.get(i);
                    if (state.getName().equals(stateName)) {
                        pkgFromState = state;
                        projectFromStateName = stateName;
                        break;
                    }
                }
            } catch (Exception e) {
                ConsoleWriter.writeln(CommonUtil.formatErrorMessage(
                        "Unable to locate State \"" + stateName + "\" in project \"" + projectName + "\" : " + e));
            }
        }
        if (pkgFromState == null) {
            ConsoleWriter.writeln("WARNING| From State not found: " + stateName);
        }
        return (pkgFromState != null);
    }

    public boolean findToState(String stateName) {

        if (harvestProject != null) {
            try {
                StateList stateList = harvestProject.getStateList();
                for (int i = 0; i < stateList.size(); i++) {
                    State state = (State) stateList.get(i);
                    if (state.getName().equals(stateName)) {
                        pkgToState = state;
                        projectToStateName = stateName;
                        break;
                    }
                }
            } catch (Exception e) {
                ConsoleWriter.writeln(CommonUtil.formatErrorMessage(
                        "Unable to locate State \"" + stateName + "\" in project \"" + projectName + "\" : " + e));
            }
        }
        if (pkgToState == null) {
            ConsoleWriter.writeln("WARNING| State not found: " + stateName);
        }
        return (pkgToState != null);
    }

    public ChangePackage findPacakge(String packageName) throws CmsdkException, AutomicException {
        ChangePackage changePackage = null;
        if (harvestProject != null) {
            ChangePackageChooser packageChooser = new ChangePackageChooser(harvestProject.getSession());
            packageChooser.setChangePackageName(packageName, 0);
            packageChooser.execute();
            ChangePackageList packagelist = packageChooser.getChangePackageList();
            if (packagelist != null && !packagelist.isEmpty()) {
                changePackage = (ChangePackage) packagelist.get(0);
                ConsoleWriter.writeln(packageName + " found");
            } else {
                throw new AutomicException("Unable to locate package \"" + packageName + "\"");
            }
        }
        return changePackage;
    }

    public boolean findDemoteProcess(String fromState, String toState) throws AutomicException {

        DemoteProcess process = null;

        try {
            if (pkgFromState != null && pkgToState != null) {
                Filter filter = new Filter();
                filter.setProcessFilter(ProcessType.DEMOTE_PROCESS);
                ProcessList processList = pkgFromState.getProcessList(filter);

                if (processList != null && !processList.isEmpty()) {
                    if (CommonUtil.checkNotEmpty(processName)) {

                        process = (DemoteProcess) processList.find(processName);
                        if (null != process) {
                            String toStateName = process.getToStateName();
                            String fromStateName = process.getState().getName();

                            if (fromStateName.equals(fromState) && toStateName.equals(toState)) {
                                demoteProcess = process;
                                ConsoleWriter.writeln("Demote Process found: " + process.getName());
                            } else {
                                throw new AutomicException(ExceptionConstants.PROCESS_STATE_MISMATCH);
                            }
                        } else {
                            throw new AutomicException(ExceptionConstants.INCORRECT_DEMOTE_PROCESS);
                        }
                    } else {
                        for (int i = 0; i < processList.size(); i++) {
                            process = (DemoteProcess) processList.get(i);

                            String tostatename = process.getToStateName();
                            String fromstatename = process.getState().getName();

                            if (fromstatename.equals(fromState) && tostatename.equals(toState)) {
                                demoteProcess = process;
                                ConsoleWriter
                                        .writeln("Demote Process name which is picked from the avialable process list: "
                                                + process.getName());
                                break;
                            }
                        }
                    }
                } else {
                    throw new AutomicException(ExceptionConstants.NO_DEMOTE_PROCESS_TYPE);
                }
                demoteProcess.setCheckChangePackageGroupBind(packageGroupBind);
                demoteProcess.setCheckChangePackageDependencies(packageDependencies);
            }
        } catch (Exception e) {
            throw new AutomicException(e.getMessage());
        }
        return true;
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

        String demotePackageGroups = getOptionValue("packgroups");
        String demotePackages = getOptionValue("packnames");
        if (!((CommonUtil.checkNotEmpty(demotePackageGroups)) ^ (CommonUtil.checkNotEmpty(demotePackages)))) {
            throw new AutomicException("Package Names and Package Group Names are mutually exclusive fields.");
        }

        if (CommonUtil.checkNotEmpty(demotePackageGroups)) {
            packageGroupsName = demotePackageGroups.split(",");
        }
        if (CommonUtil.checkNotEmpty(demotePackages)) {
            packageName = demotePackages.split(",");
        }
        processName = getOptionValue("processname");
        packageGroupBind = CommonUtil.convert2Bool(getOptionValue("enforcebind"));
        InputValidators.checkMandatoryParameter(packageGroupBind.toString(), "Enforce Pacakge Bind");

        packageDependencies = CommonUtil.convert2Bool(getOptionValue("dependency"));
        InputValidators.checkMandatoryParameter(packageDependencies.toString(), "Verify Package Dependency");

    }

}