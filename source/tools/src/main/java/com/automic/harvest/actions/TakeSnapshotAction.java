
package com.automic.harvest.actions;

import com.automic.harvest.base.HarvestProcessAction;
import com.automic.harvest.constants.ExceptionConstants;
import com.automic.harvest.constants.Resource;
import com.automic.harvest.exception.AutomicException;
import com.automic.harvest.util.CommonUtil;
import com.automic.harvest.util.ConsoleWriter;
import com.automic.harvest.util.InputValidators;
import com.ca.cmsdk.CmsdkException;
import com.ca.cmsdk.VersionChooser;
import com.ca.cmsdk.common.CmsdkConstants;
import com.ca.cmsdk.lists.VersionList;
import com.ca.cmsdk.process.ProcessType;
import com.ca.cmsdk.process.TakeSnapshotProcess;

/**
 * This action takes a snapshot for a given project with latest versions in the view
 * 
 * @author vijendraparmar
 *
 */

public class TakeSnapshotAction extends HarvestProcessAction {

    private String snapshotName;

    private boolean otherProjectsVisibility;

    /**
     * Initializes a newly created {@code TakeSnapshotAction}
     */
    public TakeSnapshotAction() {
        addOption("snapshot", true, Resource.SNAPSHOT_NAME);
        addOption("visibility", true, Resource.VISIBLE_TO_OTHER_PROJECTS_NAME);

    }

    @Override
    protected void executeSpecific() throws AutomicException {

        validateInputs();

        TakeSnapshotProcess takeSnapshotProcess = null;
        try {

            if (process != null && process instanceof TakeSnapshotProcess) {
                takeSnapshotProcess = (TakeSnapshotProcess) process;
                takeSnapshotProcess.get();

                /*
                 * Find the latest versions
                 */
                VersionChooser vc = new VersionChooser(projectState.getView());
                vc.clear();
                vc.setRecursive(true);
                vc.setItemName("*");
                vc.setParentPath("\\");
                vc.setVersionItemOption(CmsdkConstants.VERSION_FILTER_ITEM_BOTH);
                vc.setVersionOption(CmsdkConstants.VERSION_FILTER_LATEST_IN_VIEW);
                vc.execute();
                VersionList vrsnlist = vc.getVersionList();
                takeSnapshotProcess.setGloballyVisible(otherProjectsVisibility);
                takeSnapshotProcess.execute(0, snapshotName, vrsnlist, null, null);
                ConsoleWriter.writeln(String.format("Take Snapshot view %s executed successfully.", snapshotName));
            } else {
                throw new AutomicException(ExceptionConstants.PROCESS_FAILURE);
            }
        } catch (CmsdkException e) {
            throw new AutomicException(e.getMessage());
        }

    }

    @Override
    public void validateInputs() throws AutomicException {
        super.validateInputs();
        snapshotName = getOptionValue("snapshot");
        InputValidators.checkMandatoryParameter(snapshotName, Resource.SNAPSHOT_NAME);

        String tmp = getOptionValue("visibility");
        InputValidators.checkMandatoryParameter(tmp, Resource.VISIBLE_TO_OTHER_PROJECTS_NAME);
        otherProjectsVisibility = CommonUtil.convert2Bool(tmp);
    }

    @Override
    protected ProcessType getProcessType() {
        return ProcessType.TAKESNAPSHOT_PROCESS;
    }

}
