package com.automic.harvest.base;

import com.automic.harvest.constants.ExceptionConstants;
import com.automic.harvest.constants.Resource;
import com.automic.harvest.exception.AutomicException;
import com.automic.harvest.util.InputValidators;
import com.ca.cmsdk.CmsdkAuthenticationException;
import com.ca.cmsdk.CmsdkException;
import com.ca.cmsdk.Project;

/**
 * This class is used to find project then run project related actions.
 * 
 * @author asthasingh
 * 
 */
public abstract class HarvestProjectAction extends HarvestBaseAction {

    protected String projectName;
    protected Project harvestProject;

    /**
     * Initializes a newly created {@code HarvestProjectAction}
     * 
     */
    public HarvestProjectAction() {
        addOption("projectname", true, "Project Name");
    }

    /**
     * checks whether the project exist in harvest or not
     * 
     * @return true/false
     * @throws CmsdkAuthenticationException
     * @throws CmsdkException
     */
    public boolean findProject() throws CmsdkAuthenticationException, CmsdkException {
        if (session != null) {
            harvestProject = session.getProject(projectName);
        }
        return (harvestProject != null);
    }

    /**
     * Validate and initialize inputs
     * 
     * @throws AutomicException
     */
    public void validateInputs() throws AutomicException {

        projectName = getOptionValue("projectname");
        InputValidators.checkMandatoryParameter(projectName, Resource.PROJECT_NAME);
        try {
            if (!findProject()) {
                throw new AutomicException(String.format(ExceptionConstants.PROJECT_NOT_FOUND_FAILURE, projectName));
            }
        } catch (CmsdkException e) {
            throw new AutomicException(e.getMessage());
        }
    }

}
