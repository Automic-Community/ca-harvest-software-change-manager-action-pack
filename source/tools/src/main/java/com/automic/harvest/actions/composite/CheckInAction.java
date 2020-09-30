package com.automic.harvest.actions.composite;

import com.automic.harvest.base.AbstractAction;
import com.automic.harvest.constants.Constants;
import com.automic.harvest.constants.ExceptionConstants;
import com.automic.harvest.exception.AutomicException;
import com.automic.harvest.util.CommonUtil;
import com.automic.harvest.util.ConsoleWriter;
import com.automic.harvest.util.Helper;
import com.automic.harvest.util.InputValidators;
import com.ca.cmsdk.ChangePackage;
import com.ca.cmsdk.CmsdkException;
import com.ca.cmsdk.CmsdkFileException;
import com.ca.cmsdk.State;
import com.ca.cmsdk.fileAgent.FileAgent;
import com.ca.cmsdk.lists.ChangePackageList;
import com.ca.cmsdk.lists.StringList;
import com.ca.cmsdk.process.CheckinProcess;
import com.ca.cmsdk.process.ProcessObject;

/**
 * 
 * @author vijendraparmar
 * 
 */
public class CheckInAction {

    final AbstractAction abstractAction;
    private String packageName;
    private String clientPath;
    private String viewPath;
    private int checkInMode = 0;
    private String files;
    private String description;
    private boolean recursive = false;
    private boolean deleteAfterCI = false;
    private int checkInPathOption = 2;
    private int checkInItemFilter = 3;

    private String status;

    private String[] fileList;

    /**
     * Initializes a newly created {@code CheckInAction}
     * 
     * @param abstractAction
     *            AbstractAction Object
     */
    public CheckInAction(AbstractAction abstractAction) {
        abstractAction.addOption("pkgname", true, "Package Name");
        abstractAction.addOption("clientpath", true, "Check-in Directory Path");
        abstractAction.addOption("viewpath", true, "View Path");
        abstractAction.addOption("cimode", true, "Check-in Mode");
        abstractAction.addOption("cipathoption", true, "Item Path Options");
        abstractAction.addOption("ciitemfilter", true, "Check-in Item Filter Option");
        abstractAction.addOption("files", false, "List Of Files or Folders to Check-in");
        abstractAction.addOption("desc", false, "Check-in Description");
        abstractAction.addOption("recursive", true, "Recursive");
        abstractAction.addOption("dltafterci", true, "Delete After Check-in");
        this.abstractAction = abstractAction;
    }

    /**
     * Execute Checkin @param process @param projectName @param projectState @param agent @throws
     * AutomicException @throws CmsdkException @throws CmsdkFatalCommException @throws
     */
    public void executeCheckin(ProcessObject process, String projectName, State projectState, FileAgent agent)
            throws AutomicException {

        CheckinProcess proc = null;

        try {
            proc = (CheckinProcess) process;
            String ciPath = clientPath.replace('/', '\\');
            proc.setClientDir(ciPath);
            proc.setCheckinMode(checkInMode);
            proc.setItemOption(checkInItemFilter);
            proc.setPathOption(checkInPathOption);
            proc.setDeleteAfterCI(deleteAfterCI);
            proc.setViewPath(viewPath.replace('/', '\\'));
            if (CommonUtil.checkNotEmpty(description)) {
                proc.setDescription(description);
            }
            proc.setNewOrExisting(true);

            // Get the package object
            ChangePackageList packageList = projectState.getChangePackageList();
            if (packageList == null) {
                throw new AutomicException(
                        "Project [" + projectName + "] and Stete [" + projectState + "] does not contain any package.");
            }
            ChangePackage pkgobj = (ChangePackage) packageList.find(packageName);
            if (pkgobj == null) {
                throw new AutomicException("Project [" + projectName + "] and State [" + projectState
                        + "] does not contain package [" + packageName + "].");
            }

            // Recursively get the list of files in the clientpath specified
            StringList fList = Helper.getFileListByAgent(agent, recursive, fileList);
            if (fList.isEmpty()) {
                if (fileList != null && fileList.length > 0) {
                    throw new AutomicException(ExceptionConstants.NO_CONTENTS_IN_CONTENT_LIST);
                } else {
                    throw new AutomicException(
                            String.format(ExceptionConstants.NO_CONTENTS_IN_CLIENT_PATH, clientPath));
                }
            }
            // Execute the Check-in operation
            proc.execute(fList, pkgobj, agent, false, null, null);

            String summaryMsg = proc.getExecuteSummaryMessage();
            String msg = null;
            if (summaryMsg != null) {
                msg = summaryMsg.substring(summaryMsg.indexOf(':') + 1);
            } else {
                msg = "Successful";
            }
            status = msg;
            ConsoleWriter.writeln(Constants.SUCCESS_CHECK_IN_LOCAL);
            ConsoleWriter.writeln("UC4RB_HRV_CHECKIN_STATUS::=" + status);

        } catch (AutomicException | CmsdkException e) {
            if (proc != null && proc.getLastExecuteStatus() < 0) {
                status = proc.getExecuteSummaryMessage();
                ConsoleWriter.writeln("UC4RB_HRV_CHECKIN_STATUS::=" + status);
            }
            throw new AutomicException(
                    String.format(ExceptionConstants.FAILURE_CHECK_IN_LOCAL, e.getLocalizedMessage()));
        }

    }

    /**
     * Validate and initialize inputs
     * 
     * @throws AutomicException
     */
    public void validateInputs() throws AutomicException {
        String pckName = abstractAction.getOptionValue("pkgname");
        InputValidators.checkMandatoryParameter(pckName, "Package Name");
        packageName = pckName;

        clientPath = abstractAction.getOptionValue("clientpath");
        InputValidators.checkMandatoryParameter(clientPath, "Check-in Directory Path");

        viewPath = abstractAction.getOptionValue("viewpath");
        InputValidators.checkMandatoryParameter(viewPath, "View Path");

        String ciMode = abstractAction.getOptionValue("cimode");
        InputValidators.checkMandatoryParameter(ciMode, "Check-in Mode");
        checkInMode = Integer.parseInt(ciMode);

        String ciOptn = abstractAction.getOptionValue("cipathoption");
        InputValidators.checkMandatoryParameter(ciOptn, "Item Path Options");
        checkInPathOption = Integer.parseInt(ciOptn);

        files = abstractAction.getOptionValue("files");
        if (CommonUtil.checkNotEmpty(files)) {
            fileList = files.split(",");
        }

        String reCurse = abstractAction.getOptionValue("recursive");
        InputValidators.checkMandatoryParameter(reCurse, "Recursive");
        recursive = CommonUtil.convert2Bool(reCurse);

        String dltCin = abstractAction.getOptionValue("dltafterci");
        InputValidators.checkMandatoryParameter(dltCin, "Delete After Check-in");
        deleteAfterCI = CommonUtil.convert2Bool(dltCin);

        description = abstractAction.getOptionValue("desc");

        String itemFilter = abstractAction.getOptionValue("ciitemfilter");
        InputValidators.checkMandatoryParameter(itemFilter, "Check-in Item Filter Option");
        checkInItemFilter = Integer.parseInt(itemFilter);
    }

    /**
     * Get local file agent
     * 
     * @return
     * @throws CmsdkException
     */
    public FileAgent getLocalAgent() throws CmsdkException {
        // Set local file agent
        FileAgent fAgent = new FileAgent();
        fAgent.setGenerateSignature(false);
        fAgent.setCurrentDir(clientPath);
        return fAgent;
    }

    /**
     * Get remote file agent
     * 
     * @param agent
     * @return
     * @throws CmsdkFileException
     */
    public FileAgent getRemoteAgent(FileAgent agent) throws CmsdkFileException {
        agent.setGenerateSignature(false);
        agent.setCurrentDir(clientPath);
        return agent;
    }
}
