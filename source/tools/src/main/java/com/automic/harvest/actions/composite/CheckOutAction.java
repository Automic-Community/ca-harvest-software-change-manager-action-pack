package com.automic.harvest.actions.composite;

import java.io.File;

import com.automic.harvest.base.AbstractAction;
import com.automic.harvest.constants.Constants;
import com.automic.harvest.constants.ExceptionConstants;
import com.automic.harvest.exception.AutomicException;
import com.automic.harvest.util.CommonUtil;
import com.automic.harvest.util.ConsoleWriter;
import com.automic.harvest.util.InputValidators;
import com.ca.cmsdk.ChangePackage;
import com.ca.cmsdk.CmsdkAuthorizationException;
import com.ca.cmsdk.CmsdkException;
import com.ca.cmsdk.CmsdkFileException;
import com.ca.cmsdk.Filter;
import com.ca.cmsdk.State;
import com.ca.cmsdk.VersionChooser;
import com.ca.cmsdk.WorkingView;
import com.ca.cmsdk.common.CmsdkConstants;
import com.ca.cmsdk.fileAgent.FileAgent;
import com.ca.cmsdk.lists.ChangePackageList;
import com.ca.cmsdk.lists.VersionList;
import com.ca.cmsdk.process.CheckoutProcess;

/**
 * 
 * @author vijendraparmar
 * 
 */
public class CheckOutAction {

    protected String COProcessMode = Constants.CHECK_OUT_FOR_BROWSE;
    final AbstractAction abstractAction;
    private String packageName;
    private String clientPath;
    private String viewPath;
    private int checkOutOption = 2;
    private String files;
    private boolean replaceFile = true;
    private boolean replaceWriteableFile = true;
    private boolean addEmptyDir = true;

    private int coResultTotal;
    private int coResultSuccess;
    private int coResultFailed;
    private int coResultNotProcessed;

    private String[] fileList;

    /**
     * Initializes a newly created {@code CheckOutAction}
     * 
     * @param abstractAction
     *            AbstractAction Object
     */
    public CheckOutAction(AbstractAction abstractAction) {
        abstractAction.addOption("pkgname", false, "Package Name");
        abstractAction.addOption("clientpath", true, "Client Path");
        abstractAction.addOption("viewpath", true, "View Path");
        abstractAction.addOption("coprocessmode", true, "Checkout Process Mode");
        abstractAction.addOption("cooption", true, "Checkout Option");
        abstractAction.addOption("files", false, "File Name");
        abstractAction.addOption("replacefile", true, "Replace File");
        abstractAction.addOption("replacewritablefile", true, "Replace Writable File");
        abstractAction.addOption("addemptydir", true, "Add Empty Directories");
        this.abstractAction = abstractAction;
    }

    /**
     * Initialize Checkout Based properties
     * 
     * @param checkoutProcess
     * @throws AutomicException
     * @throws CmsdkException
     */
    public void initCheckoutMode(CheckoutProcess checkoutProcess) throws AutomicException, CmsdkException {
        switch (COProcessMode) {
            case "CHECK_OUT_FOR_BROWSE":
                if (!checkoutProcess.getBrowseMode()) {
                    throw new AutomicException(ExceptionConstants.PROCESS_NO_SELECTED_MODE);
                }
                checkoutProcess.setBrowseMode(true);
                checkoutProcess.setCheckoutMode(CmsdkConstants.CO_MODE_BROWSE);
                break;
            case "CHECK_OUT_FOR_CONCURRENT_UPDATE":
                if (!checkoutProcess.getConcUpdateMode()) {
                    throw new AutomicException(ExceptionConstants.PROCESS_NO_SELECTED_MODE);
                }
                checkoutProcess.setConcUpdateMode(true);
                checkoutProcess.setCheckoutMode(CmsdkConstants.CO_MODE_CONCURRENT);
                break;
            case "CHECK_OUT_FOR_SYNCHRONIZE":
                if (!checkoutProcess.getSyncMode()) {
                    throw new AutomicException(ExceptionConstants.PROCESS_NO_SELECTED_MODE);
                }
                checkoutProcess.setSyncMode(true);
                checkoutProcess.setCheckoutMode(CmsdkConstants.CO_MODE_SYNCHRONIZE);
                break;
            case "CHECK_OUT_FOR_UPDATE":
                if (!checkoutProcess.getUpdateMode()) {
                    throw new AutomicException(ExceptionConstants.PROCESS_NO_SELECTED_MODE);
                }
                checkoutProcess.setUpdateMode(true);
                checkoutProcess.setCheckoutMode(CmsdkConstants.CO_MODE_UPDATE);
                break;
            case "CHECK_OUT_FOR_RESERVE_ONLY":
                if (!checkoutProcess.getReserveMode()) {
                    throw new AutomicException(ExceptionConstants.PROCESS_NO_SELECTED_MODE);
                }
                checkoutProcess.setReserveMode(true);
                checkoutProcess.setCheckoutMode(CmsdkConstants.CO_MODE_RESERVE_ONLY);
                break;
        }
    }

    /**
     * Execute checkout
     * 
     * @param changePackage
     * @param checkoutProcess
     * @param projectState
     * @param agent
     * @throws AutomicException
     */
    public void executeCheckout(ChangePackage changePackage, CheckoutProcess checkoutProcess, State projectState,
            FileAgent agent) throws AutomicException {
        try {

            if (checkoutProcess != null) {
                WorkingView view = projectState.getView();
                VersionChooser verChooser = new VersionChooser(view);
                VersionList versions = new VersionList(CmsdkConstants.NULL_OBJID);

                if (null != fileList) {
                    for (int k = 0; k < fileList.length; k++) {
                        if (CommonUtil.checkNotEmpty(fileList[k])) {
                            fileList[k] = fileList[k].trim();
                        }
                    }

                    for (int i = 0; i < fileList.length; i++) {
                        if (CommonUtil.checkNotEmpty(fileList[i])) {
                            VersionList vList = getRequiredVersionList(view, fileList[i],
                                    CmsdkConstants.VERSION_FILTER_LATEST_IN_VIEW, viewPath.trim());
                            if (null == vList) {
                                throw new AutomicException(ExceptionConstants.NO_SUCH_FILE);
                            } else {
                                versions.addAll(vList);
                            }
                        }
                    }
                } else {
                    verChooser.setParentPath(viewPath.trim());
                    verChooser.setRecursive(true);
                    verChooser.setItemName("*");
                    verChooser.setVersionOption(CmsdkConstants.VERSION_FILTER_LATEST_IN_VIEW);
                    verChooser.setVersionStatusOption(CmsdkConstants.VERSION_FILTER_NORMAL_VERSION);
                    verChooser.setBranchOption(CmsdkConstants.BRANCH_FILTER_TRUNK_ONLY);
                    verChooser.setFileType(CmsdkConstants.FILETYPE_ANY);

                    if (changePackage != null) {
                        verChooser.setVersionOption(CmsdkConstants.VERSION_FILTER_LATEST_IN_PACKAGE);
                        verChooser.setItemPackageObjId(changePackage, 0);
                        verChooser.setBranchOption(CmsdkConstants.BRANCH_FILTER_TRUNK_AND_BRANCH);
                    }

                    verChooser.execute();
                    versions = verChooser.getVersionList();
                }

                if (versions.size() > 0) {
                    checkoutProcess.setSignatureOption(true);
                    checkoutProcess.setClientDir(clientPath.trim());
                    checkoutProcess.setViewPath(viewPath.trim());
                    checkoutProcess.setReplaceFile(replaceFile);
                    checkoutProcess.setAddEmptyDirOption(addEmptyDir);
                    checkoutProcess.setVersionstoFilter(CmsdkConstants.VERSION_STATUS_CO_FILTER_DELETED);
                    checkoutProcess.setReplaceWriteableFile(replaceWriteableFile);
                    checkoutProcess.setPathOption(checkOutOption);

                    int checkoutStatus;
                    if (null == changePackage) {
                        checkoutStatus = checkoutProcess.execute(versions, null, agent, null, null, true);
                    } else {
                        checkoutStatus = checkoutProcess.execute(versions, changePackage, agent, null, null, true);
                    }

                    String sMessage = checkoutProcess.getCheckoutSummaryMsg();

                    if (CommonUtil.checkNotEmpty(sMessage)) {
                        processOutput(sMessage);
                    } else {
                        ConsoleWriter.writeln("Checkout Summary Message is returned Empty!!!");
                    }

                    if (checkoutStatus == 0) {
                        ConsoleWriter.writeln(Constants.CO_ACTION_RUN_PASS_MSG);
                    } else {
                        throw new AutomicException(ExceptionConstants.CO_ACTION_RUN_FAIL_MSG);
                    }
                } else {
                    throw new AutomicException(ExceptionConstants.CO_FILE_INCORRECT);
                }
            } else {
                throw new AutomicException(ExceptionConstants.CO_INITIALIZATION_FAILED);
            }
        } catch (CmsdkException e) {
            throw new AutomicException(e.getMessage());
        }
    }

    private void processOutput(String outputMessage) {

        if (outputMessage.endsWith(".")) {
            outputMessage = outputMessage.substring(0, outputMessage.length() - 1);
        }
        String[] tokens = outputMessage.split(";");

        for (String token : tokens) {
            if (token.indexOf("Total:") > 0) {
                String totalStr = token.substring(token.indexOf("Total: ") + "Total: ".length()).trim();
                coResultTotal = Integer.parseInt(totalStr);
            } else if (token.indexOf("Success:") > 0) {
                String successStr = token.substring(token.indexOf("Success: ") + "Success: ".length()).trim();
                coResultSuccess = Integer.parseInt(successStr);
            } else if (token.indexOf("Failed:") > 0) {
                String failedStr = token.substring(token.indexOf("Failed: ") + "Failed: ".length()).trim();
                coResultFailed = Integer.parseInt(failedStr);
            } else if (token.indexOf("Not Processed:") > 0) {
                String notProcessedStr = token.substring(token.indexOf("Not Processed: ") + "Not Processed: ".length())
                        .trim();
                coResultNotProcessed = Integer.parseInt(notProcessedStr);
            }
        }
        ConsoleWriter.writeln("UC4RB_HRV_COUNT_TOTAL::=" + coResultTotal);
        ConsoleWriter.writeln("UC4RB_HRV_COUNT_SUCCESS::=" + coResultSuccess);
        ConsoleWriter.writeln("UC4RB_HRV_COUNT_FAILED::=" + coResultFailed);
        ConsoleWriter.writeln("UC4RB_HRV_COUNT_NOT_PROCESSED::=" + coResultNotProcessed);
    }

    private VersionList getRequiredVersionList(WorkingView view, String fileName, int versionOption, String viewPath)
            throws AutomicException {

        VersionList pobj = null;
        fileName = fileName.trim();
        try {
            VersionChooser vc = new VersionChooser(view);
            vc.setRecursive(false);
            vc.setVersionOption(versionOption);
            vc.setExcludePaths(false);
            vc.setParentPath(viewPath);
            vc.setIncludeEmptyPaths(true);
            Filter vcfilter = vc.getVersionChooserFilter();
            vcfilter.setItemName(fileName);
            vc.execute();
            pobj = vc.getVersionList();
            if (pobj.size() == 0) {
                return null;
            }
        } catch (CmsdkAuthorizationException e) {
            throw new AutomicException(e.getMessage());
        } catch (CmsdkException e) {
            throw new AutomicException(e.getMessage());
        }
        return pobj;
    }

    /**
     * Find specific package
     * @param projectState State Object
     * @return ChangePackage object
     * @throws CmsdkException 
     * @throws AutomicException
     */
    public ChangePackage findPackage(State projectState) throws CmsdkException, AutomicException {
        ChangePackage changePackage = null;
        if (CommonUtil.checkNotEmpty(packageName)) {
            ChangePackageList cpList = projectState.getChangePackageList();
            for (Object object : cpList) {
                ChangePackage cp = (ChangePackage) object;
                if (cp.getName().equals(packageName.trim())) {
                    changePackage = cp;
                    break;
                }
            }
            if (null == changePackage) {
                throw new AutomicException(ExceptionConstants.PACKAGE_NOT_IN_STATE);
            }
        }
        return changePackage;
    }

    /**
     * Validate and initialize input
     * @throws AutomicException
     */
    public void validateInputs() throws AutomicException {

        packageName = abstractAction.getOptionValue("pkgname");

        clientPath = abstractAction.getOptionValue("clientpath");
        InputValidators.checkMandatoryParameter(clientPath, "Client Path");

        viewPath = abstractAction.getOptionValue("viewpath");
        InputValidators.checkMandatoryParameter(viewPath, "View Path");

        String coPrcsMode = abstractAction.getOptionValue("coprocessmode");
        InputValidators.checkMandatoryParameter(coPrcsMode, "Checkout Process Mode");
        COProcessMode = coPrcsMode;

        String coOptn = abstractAction.getOptionValue("cooption");
        InputValidators.checkMandatoryParameter(coOptn, "Checkout Option");
        checkOutOption = Integer.parseInt(coOptn);

        files = abstractAction.getOptionValue("files");
        if (CommonUtil.checkNotEmpty(files)) {
            fileList = files.split(",");
        }

        String rFile = abstractAction.getOptionValue("replacefile");
        InputValidators.checkMandatoryParameter(rFile, "Replace File");
        replaceFile = CommonUtil.convert2Bool(rFile);

        String rWFile = abstractAction.getOptionValue("replacewritablefile");
        InputValidators.checkMandatoryParameter(rWFile, "Replace Writable File");
        replaceWriteableFile = CommonUtil.convert2Bool(rWFile);

        String addEdir = abstractAction.getOptionValue("addemptydir");
        InputValidators.checkMandatoryParameter(addEdir, "Add Empty Directories");
        addEmptyDir = CommonUtil.convert2Bool(addEdir);

        if ((COProcessMode.equals(Constants.CHECK_OUT_FOR_CONCURRENT_UPDATE)
                || COProcessMode.equals(Constants.CHECK_OUT_FOR_UPDATE)
                || COProcessMode.equals(Constants.CHECK_OUT_FOR_RESERVE_ONLY))
                && (!CommonUtil.checkNotEmpty(packageName))) {
            throw new AutomicException(ExceptionConstants.PACKAGE_NAME_REQUIRED);
        }
    }

    public FileAgent getLocalAgent() throws AutomicException, CmsdkException {
        File checkoutDir = new File(clientPath.trim());
        if (!checkoutDir.exists()) {
            if (!checkoutDir.mkdirs()) {
                throw new AutomicException(ExceptionConstants.NO_CLIENT_PATH);
            }
        }

        // set up file agent
        FileAgent agent = new FileAgent();
        agent.setGenerateSignature(true);
        agent.setCurrentDir(clientPath.trim());
        return agent;
    }

    public FileAgent getRemoteAgent(FileAgent agent) throws CmsdkFileException {
        agent.setGenerateSignature(true);
        agent.mkDir(clientPath.trim());
        agent.setCurrentDir(clientPath.trim());
        return agent;
    }

}
