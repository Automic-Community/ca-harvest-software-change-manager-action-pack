package com.automic.harvest.actions;

import java.text.SimpleDateFormat;

import com.automic.harvest.base.HarvestProjectAction;
import com.automic.harvest.constants.ExceptionConstants;
import com.automic.harvest.constants.Resource;
import com.automic.harvest.exception.AutomicException;
import com.automic.harvest.util.CommonUtil;
import com.automic.harvest.util.ConsoleWriter;
import com.automic.harvest.util.InputValidators;
import com.ca.cmsdk.ChangePackage;
import com.ca.cmsdk.CmsdkException;
import com.ca.cmsdk.State;
import com.ca.cmsdk.Version;
import com.ca.cmsdk.VersionChooser;
import com.ca.cmsdk.WorkingView;
import com.ca.cmsdk.common.CmsdkConstants;
import com.ca.cmsdk.lists.VersionList;

/**
 * 
 * Retrieves the version details of a Harvest item
 * 
 * @author vijendraparmar
 *
 */
public class GetVersionAction extends HarvestProjectAction {

    protected State projectState;
    private String projectStateName;
    private String packageName;
    private String viewPath;
    private String fileName;

    /**
     * Initializes a newly created {@code GetVersionAction}
     */
    public GetVersionAction() {
        addOption("statename", true, "State Name");
        addOption("packagename", false, "Package Name");
        addOption("viewpath", true, "View Path");
        addOption("filename", true, "File Name");
    }

    @Override
    protected void executeSpecific() throws AutomicException {
        // outputs
        String fileDescription;
        String mapVersion;
        String creator;
        String versionStatus = "";
        long dataSize;
        String modifier;
        String creationTime;
        String modificationTime;
        String storedAs = "";

        validateInputs();
        try {
            ChangePackage cpkg = null;
            projectState = (State) harvestProject.getStateList().find(projectStateName);

            if (projectState != null) {

                if (CommonUtil.checkNotEmpty(packageName)) {
                    cpkg = (ChangePackage) projectState.getChangePackageList().find(packageName);
                    if (cpkg == null) {
                        throw new AutomicException(
                                String.format(ExceptionConstants.PACKAGE_NOT_FOUND_FAILURE, packageName));
                    }
                }

                WorkingView view = projectState.getView();
                VersionChooser verChooser = new VersionChooser(view);

                verChooser.setParentPath(viewPath);
                verChooser.setRecursive(true);
                verChooser.setItemName(fileName);

                verChooser.setVersionOption(CmsdkConstants.VERSION_FILTER_LATEST_IN_VIEW);
                verChooser.setVersionStatusOption(CmsdkConstants.VERSION_FILTER_ALL_TAG);
                verChooser.setBranchOption(CmsdkConstants.BRANCH_FILTER_TRUNK_ONLY);
                verChooser.setFileType(CmsdkConstants.FILETYPE_ANY);

                if (cpkg != null) {
                    verChooser.setVersionOption(CmsdkConstants.VERSION_FILTER_LATEST_IN_PACKAGE);
                    verChooser.setItemPackageObjId(cpkg, 0);
                    verChooser.setBranchOption(CmsdkConstants.BRANCH_FILTER_TRUNK_AND_BRANCH);
                }

                verChooser.execute();

                VersionList versionList = verChooser.getVersionList();
                int count = versionList.size();

                if (count <= 0) {
                    throw new AutomicException(String.format(ExceptionConstants.FILE_NOT_FOUND_FAILURE, fileName));
                }

                Version version = (Version) versionList.get(0);
                String fname = version.getName();

                if (fileName.equals(fname)) {
                    fileDescription = version.getDescription();
                    mapVersion = version.getMappedVersionName();
                    creator = version.getCreatorName();
                    String versionTag = version.getVersionStatus();

                    switch (versionTag) {
                        case "N":
                            versionStatus = "Normal";
                            break;
                        case "R":
                            versionStatus = "Reserved";
                            break;
                        case "D":
                            versionStatus = "Deleted";
                            break;
                        case "M":
                            versionStatus = "Merge";
                            break;
                    }

                    dataSize = version.getDataSize();
                    modifier = version.getModifierName();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    creationTime = sdf.format(version.getCreationTime().getTime());
                    modificationTime = sdf.format(version.getModifiedTime().getTime());
                    int fileType = version.getFileType();
                    switch (fileType) {
                        case 0:
                            storedAs = "Binary";
                            break;
                        case 1:
                            storedAs = "Text";
                            break;
                        case 2:
                            storedAs = "Unknown";
                            break;
                    }
                } else {
                    throw new AutomicException(String.format(ExceptionConstants.FILE_NOT_FOUND_FAILURE, fileName));
                }
            } else {
                throw new AutomicException(String.format(ExceptionConstants.STATE_NOT_FOUND_FAILURE, projectStateName));

            }
        } catch (CmsdkException e) {
            throw new AutomicException(e.getMessage());
        }

        ConsoleWriter.writeln("UC4RB_HRV_CREATE_DATE::=" + creationTime);
        ConsoleWriter.writeln("UC4RB_HRV_CREATOR_NAME::=" + creator);
        ConsoleWriter.writeln("UC4RB_HRV_DATA_SIZE::=" + dataSize);
        ConsoleWriter.writeln("UC4RB_HRV_FILE_DESC::=" + fileDescription);
        ConsoleWriter.writeln("UC4RB_HRV_FILE_MAPPED_VERSION::=" + mapVersion);
        ConsoleWriter.writeln("UC4RB_HRV_MODIFIED_DATE::=" + modificationTime);
        ConsoleWriter.writeln("UC4RB_HRV_MODIFIER_NAME::=" + modifier);
        ConsoleWriter.writeln("UC4RB_HRV_STORED_AS::=" + storedAs);
        ConsoleWriter.writeln("UC4RB_HRV_VERSION_STATUS::=" + versionStatus);
        ConsoleWriter.writeln("Version details retrieved successfully");
    }

    @Override
    public void validateInputs() throws AutomicException {
        super.validateInputs();
        packageName = getOptionValue("packagename");
        projectStateName = getOptionValue("statename");
        InputValidators.checkMandatoryParameter(projectStateName, Resource.STATE_NAME);
        viewPath = getOptionValue("viewpath");
        InputValidators.checkMandatoryParameter(viewPath, Resource.VIEW_PATH);
        fileName = getOptionValue("filename");
        InputValidators.checkMandatoryParameter(fileName, Resource.FILE_NAME);

    }
}
