/*******************************************************************************
 * Copyright (c) 2003, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.j2ee.internal.ejb.archiveoperations;



import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jem.util.emf.workbench.WorkbenchURIConverter;
import org.eclipse.jem.util.emf.workbench.WorkbenchURIConverterImpl;
import org.eclipse.jem.workbench.utility.JemProjectUtilities;
import org.eclipse.jst.j2ee.commonarchivecore.internal.exception.ArchiveRuntimeException;
import org.eclipse.jst.j2ee.internal.archive.operations.J2EELoadStrategyImpl;
import org.eclipse.jst.j2ee.internal.ejb.project.EJBNatureRuntime;

//TODO delete jsholl
/**
 * @deprecated
 *
 */
public class EJBProjectLoadStrategyImpl extends J2EELoadStrategyImpl {
	/**
	 * EJBProjectLoadStrategyImpl constructor comment.
	 */
	public EJBProjectLoadStrategyImpl(IProject aProject) {
		super();
		project = aProject;
		filesList = new ArrayList();
	}

	/**
	 * @see com.ibm.etools.archive.LoadStrategy
	 */
	public IContainer getModuleContainer() {

		try {
			EJBNatureRuntime enr = EJBNatureRuntime.getRuntime(project);
			return enr.getModuleServerRoot();
		} catch (Exception e) {
			throw new ArchiveRuntimeException(EJBArchiveOpsResourceHandler.getString("ARCHIVE_OPERATION_ErroOccured"), e);} //$NON-NLS-1$
	}

	/**
	 * save method comment.
	 */
	public WorkbenchURIConverter getProjectURIConverter() {

		EJBNatureRuntime enr = EJBNatureRuntime.getRuntime(project);
		projectURIConverter = new WorkbenchURIConverterImpl(enr.getModuleServerRoot());
		if (isExportSource()) {
			List sourceContainers = JemProjectUtilities.getSourceContainers(enr.getProject());
			for (int i = 0; i < sourceContainers.size(); i++) {
				projectURIConverter.addInputContainer((IFolder) sourceContainers.get(i));
			}
			return projectURIConverter;
		}
		return projectURIConverter;
	}
}