/***************************************************************************************************
 * Copyright (c) 2003, 2004 IBM Corporation and others. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: IBM Corporation - initial API and implementation
 **************************************************************************************************/
/*
 * Created on Sep 29, 2003
 * 
 * To change the template for this generated file go to Window&gt;Preferences&gt;Java&gt;Code
 * Generation&gt;Code and Comments
 */
package org.eclipse.jst.j2ee.ejb.internal.plugin;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jst.j2ee.application.internal.operations.J2EEComponentCreationDataModel;
import org.eclipse.jst.j2ee.application.internal.operations.J2EEComponentCreationOperation;
import org.eclipse.jst.j2ee.application.internal.operations.J2EEModuleImportDataModel;
import org.eclipse.jst.j2ee.ejb.EJBJar;
import org.eclipse.jst.j2ee.ejb.EnterpriseBean;
import org.eclipse.jst.j2ee.internal.archive.operations.ImportOption;
import org.eclipse.jst.j2ee.internal.earcreation.UpdateModuleReferencesInEARProjectCommand;
import org.eclipse.jst.j2ee.internal.ejb.archiveoperations.EJBPostImportOperation;
import org.eclipse.jst.j2ee.internal.ejb.archiveoperations.EjbComponentCreationDataModel;
import org.eclipse.jst.j2ee.internal.ejb.archiveoperations.EjbComponentCreationOperation;
import org.eclipse.jst.j2ee.internal.ejb.project.EJBEditModel;
import org.eclipse.jst.j2ee.internal.ejb.project.EJBNatureRuntime;
import org.eclipse.jst.j2ee.internal.ejb.project.operations.EJBClientJarCreationOperation;
import org.eclipse.jst.j2ee.internal.ejb.project.operations.EJBClientProjectDataModel;
import org.eclipse.jst.j2ee.internal.ejb.project.operations.EJBModuleImportDataModel;
import org.eclipse.jst.j2ee.internal.moduleextension.EarModuleExtensionImpl;
import org.eclipse.jst.j2ee.internal.moduleextension.EjbModuleExtension;
import org.eclipse.jst.j2ee.internal.project.IEJBNatureConstants;
import org.eclipse.jst.j2ee.internal.project.IJ2EEProjectTypes;
import org.eclipse.jst.j2ee.internal.project.J2EENature;
import org.eclipse.wst.common.frameworks.internal.operations.IHeadlessRunnableWithProgress;
import org.eclipse.wst.common.frameworks.internal.operations.WTPOperation;
import org.eclipse.wst.common.internal.emfworkbench.operation.EditModelOperationDataModel;

public class EjbModuleExtensionImpl extends EarModuleExtensionImpl implements EjbModuleExtension {

	public EjbModuleExtensionImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jst.j2ee.internal.internal.moduleextension.EjbModuleExtension#initializeEjbReferencesToModule(org.eclipse.jst.j2ee.internal.internal.j2eeproject.J2EENature)
	 */
	public void initializeEjbReferencesToModule(J2EENature nature, UpdateModuleReferencesInEARProjectCommand cmd) {
		EJBEditModel editModel = ((EJBNatureRuntime) nature).getEJBEditModelForWrite(this);
		boolean foundRef = false;
		try {
			EJBJar jar = editModel.getEJBJar();
			if (jar != null) {
				List ejbs = jar.getEnterpriseBeans();
				int size = ejbs.size();
				EnterpriseBean ejb;
				for (int i = 0; i < size; i++) {
					ejb = (EnterpriseBean) ejbs.get(i);
					foundRef = foundRef || cmd.initializeEjbReferencesToModule(ejb.getEjbRefs());
					foundRef = foundRef || cmd.initializeEjbReferencesToModule(ejb.getEjbLocalRefs());
				}
			}
			if (foundRef)
				cmd.addNestedEditModel(editModel);
		} finally {
			if (!foundRef)
				editModel.releaseAccess(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jst.j2ee.internal.internal.moduleextension.EjbModuleExtension#createProjectInfo()
	 */
	//	public J2EEJavaProjectInfo createProjectInfo() {
	//		return new EJBProjectInfo();
	//	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jst.j2ee.internal.internal.moduleextension.EjbModuleExtension#createProjectInfo()
	 */
	//	public J2EEJavaProjectInfo createProjectInfo(int version) {
	//		EJBProjectInfo info = new EJBProjectInfo();
	//		info.setModuleVersion(version);
	//		return info;
	//	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jst.j2ee.internal.internal.moduleextension.EjbModuleExtension#createImportOperation(org.eclipse.core.resources.IProject,
	 *      org.eclipse.jst.j2ee.internal.internal.commonarchivecore.EJBJarFile)
	 */
	//    public J2EEImportOperationOLD createImportOperation(IProject proj,
	// EJBJarFile ejbJarFile) {
	//        return new EJBJarImportOperationOLD(proj, ejbJarFile);
	//    }
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jst.j2ee.internal.internal.moduleextension.EjbModuleExtension#createEJBPostImportOperation(org.eclipse.core.resources.IProject)
	 */
	public IHeadlessRunnableWithProgress createEJBPostImportOperation(IProject aProj) {
		EJBNatureRuntime nature = EJBNatureRuntime.getRuntime(aProj);
		return new EJBPostImportOperation(nature);
	}

	public EJBJar getEJBJar(IProject aProject) {
		EJBNatureRuntime runtime = EJBNatureRuntime.getRuntime(aProject);
		if (runtime == null)
			return null;
		return runtime.getEJBJar();
	}

	public IProject getDefinedEJBClientJARProject(IProject anEJBProject) {
		EJBNatureRuntime runtime = EJBNatureRuntime.getRuntime(anEJBProject);
		if (runtime == null)
			return null;
		return runtime.getDefinedEJBClientJARProject();
	}

	public WTPOperation createEJBClientJARProject(IProject anEJBProject) {
		EJBClientProjectDataModel dataModel = new EJBClientProjectDataModel();
		dataModel.setProperty(EditModelOperationDataModel.PROJECT_NAME, anEJBProject.getName());
		EJBClientJarCreationOperation op = new EJBClientJarCreationOperation(dataModel);
		return op;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jst.j2ee.internal.internal.moduleextension.EarModuleExtension#createProjectCreationOperation(org.eclipse.jst.j2ee.internal.internal.application.operations.J2EEModuleCreationDataModel)
	 */
	public J2EEComponentCreationOperation createProjectCreationOperation(J2EEComponentCreationDataModel dataModel) {
		return new EjbComponentCreationOperation((EjbComponentCreationDataModel) dataModel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jst.j2ee.internal.internal.moduleextension.EarModuleExtension#createProjectDataModel()
	 */
	public J2EEComponentCreationDataModel createProjectDataModel() {
		EjbComponentCreationDataModel model = new EjbComponentCreationDataModel();

		//Added this property so Application Creation Wizard, will not create a
		//EJB client jar, when a EJB module is created.
		model.setProperty(EjbComponentCreationDataModel.CREATE_CLIENT, Boolean.FALSE);

		//Override the default to not create a default session bean.
		//This is necessary when creating a default EJB project from an EAR project wizard.
		model.setProperty(EjbComponentCreationDataModel.CREATE_DEFAULT_SESSION_BEAN, Boolean.FALSE);
		return model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jst.j2ee.internal.internal.moduleextension.EarModuleExtension#createImportDataModel()
	 */
	public J2EEModuleImportDataModel createImportDataModel() {
		return new EJBModuleImportDataModel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jst.j2ee.internal.internal.moduleextension.EarModuleExtension#getNatureID()
	 */
	public String getNatureID() {
		return IEJBNatureConstants.NATURE_ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jst.j2ee.internal.internal.moduleextension.EarModuleExtension#createProjectCreationOperation(com.ibm.etools.archive.ear.operations.ImportOption)
	 */
	public J2EEComponentCreationOperation createProjectCreationOperation(ImportOption option) {
		if (option.getArchiveType() == IJ2EEProjectTypes.EJB_CLIENT) {
			J2EEComponentCreationDataModel model = (J2EEComponentCreationDataModel) option.getModel();
			model.setProperty(EjbComponentCreationDataModel.CREATE_CLIENT, Boolean.TRUE);
			return createProjectCreationOperation(model);
		}
		return super.createProjectCreationOperation(option);
	}

}