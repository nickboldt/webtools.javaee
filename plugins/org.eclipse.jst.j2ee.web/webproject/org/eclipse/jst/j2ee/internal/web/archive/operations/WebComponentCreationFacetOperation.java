/*******************************************************************************
 * Copyright (c) 2003, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.j2ee.internal.web.archive.operations;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jem.util.emf.workbench.ProjectUtilities;
import org.eclipse.jem.util.logger.proxy.Logger;
import org.eclipse.jst.j2ee.datamodel.properties.IJ2EEComponentCreationDataModelProperties;
import org.eclipse.jst.j2ee.datamodel.properties.IJavaComponentCreationDataModelProperties;
import org.eclipse.jst.j2ee.project.facet.FacetProjectCreationDataModelProvider;
import org.eclipse.jst.j2ee.project.facet.IFacetDataModelPropeties;
import org.eclipse.jst.j2ee.project.facet.IFacetProjectCreationDataModelProperties;
import org.eclipse.jst.j2ee.project.facet.J2EEComponentCreationFacetOperation;
import org.eclipse.jst.j2ee.web.datamodel.properties.IWebComponentCreationDataModelProperties;
import org.eclipse.jst.j2ee.web.project.facet.IWebFacetInstallDataModelProperties;
import org.eclipse.jst.j2ee.web.project.facet.WebFacetInstallDataModelProvider;
import org.eclipse.wst.common.componentcore.datamodel.properties.IComponentCreationDataModelProperties;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.runtime.IRuntime;
import org.eclipse.wst.common.project.facet.core.runtime.RuntimeManager;

public class WebComponentCreationFacetOperation extends J2EEComponentCreationFacetOperation {

	public WebComponentCreationFacetOperation(IDataModel model) {
		super(model);
	}

	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		IDataModel dm = DataModelFactory.createDataModel(new FacetProjectCreationDataModelProvider());
		String projectName = model.getStringProperty(IComponentCreationDataModelProperties.PROJECT_NAME);
		dm.setProperty(IFacetProjectCreationDataModelProperties.FACET_PROJECT_NAME, projectName);
		List facetDMs = new ArrayList();
		facetDMs.add(setupJavaInstallAction());
		facetDMs.add(setupWebInstallAction());
		dm.setProperty(IFacetProjectCreationDataModelProperties.FACET_DM_LIST, facetDMs);
		IStatus stat = dm.getDefaultOperation().execute(monitor, info);
		if( stat.isOK()){
			String earProjectName = (String) model.getProperty(IJ2EEComponentCreationDataModelProperties.EAR_COMPONENT_NAME);
			IProject earProject = ProjectUtilities.getProject( earProjectName );
			stat = addtoEar(projectName, earProjectName);
		}		

		return stat;
	}

	protected IDataModel setupWebInstallAction() {
		String versionStr = model.getPropertyDescriptor(IJ2EEComponentCreationDataModelProperties.COMPONENT_VERSION).getPropertyDescription();
		IDataModel webFacetInstallDataModel = DataModelFactory.createDataModel(new WebFacetInstallDataModelProvider());
		webFacetInstallDataModel.setProperty(IFacetDataModelPropeties.FACET_PROJECT_NAME, model.getStringProperty(IComponentCreationDataModelProperties.PROJECT_NAME));
		webFacetInstallDataModel.setProperty(IFacetDataModelPropeties.FACET_VERSION_STR, versionStr);
		webFacetInstallDataModel.setProperty(IWebFacetInstallDataModelProperties.CONTENT_DIR, model.getStringProperty(IWebComponentCreationDataModelProperties.WEBCONTENT_FOLDER));
		webFacetInstallDataModel.setProperty(IWebFacetInstallDataModelProperties.EAR_PROJECT_NAME,
				model.getProperty(IJ2EEComponentCreationDataModelProperties.EAR_COMPONENT_NAME));		
		return webFacetInstallDataModel;
	}

	protected void setRuntime(IFacetedProject facetProj) throws CoreException {
		String runtimeID = model.getStringProperty(IJavaComponentCreationDataModelProperties.RUNTIME_TARGET_ID);
		try {
			IRuntime runtime = RuntimeManager.getRuntime(runtimeID);
			facetProj.setRuntime(runtime, null);
		} catch (IllegalArgumentException e) {
			Logger.getLogger().logError(e);
		}
	}

}
