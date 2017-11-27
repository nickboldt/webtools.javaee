/*******************************************************************************
 * Copyright (c) 2003, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.ejb.ui.internal.wizard;

import org.eclipse.jst.ejb.ui.internal.util.EJBUIMessages;
import org.eclipse.jst.j2ee.internal.actions.IJ2EEUIContextIds;
import org.eclipse.jst.j2ee.internal.plugin.J2EEUIMessages;
import org.eclipse.jst.j2ee.internal.plugin.J2EEUIPlugin;
import org.eclipse.jst.j2ee.internal.plugin.J2EEUIPluginIcons;
import org.eclipse.jst.j2ee.internal.project.J2EEProjectUtilities;
import org.eclipse.jst.j2ee.internal.wizard.J2EEModuleImportPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;


/**
 * @author cbridgha
 * 
 * To change the template for this generated type comment go to Window>Preferences>Java>Code
 * Generation>Code and Comments
 */
public class EJBComponentImportPage extends J2EEModuleImportPage {
	/**
	 * @param model
	 * @param pageName
	 */
	public EJBComponentImportPage(IDataModel model, String pageName) {
		super(model, pageName);
		setTitle(EJBUIMessages.EJB_IMPORT_MAIN_PG_TITLE);
		setDescription(EJBUIMessages.EJB_IMPORT_MAIN_PG_DESC);
		setImageDescriptor(J2EEUIPlugin.getDefault().getImageDescriptor(J2EEUIPluginIcons.EJB_IMPORT_WIZARD_BANNER));
	}

	@Override
	protected String getFileNamesStoreID() {
		return "EJB";//$NON-NLS-1$
	}

	@Override
	protected String getFileImportLabel() {
		return EJBUIMessages.EJB_JAR_FILE_LBL;
	}

	@Override
	protected String[] getFilterExpression() {
		return new String[]{"*.jar"}; //$NON-NLS-1$
	}

	@Override
	protected String getProjectImportLabel() {
		return J2EEUIMessages.getResourceString(J2EEUIMessages.EJB_PROJ_LBL);
	}

	@Override
	protected Composite createTopLevelComposite(Composite parent) {
		setInfopopID(IJ2EEUIContextIds.IMPORT_EJB_WIZARD_P1);
		return super.createTopLevelComposite(parent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jst.j2ee.internal.ui.wizard.J2EEModuleImportPage#createAnnotationsStandaloneGroup(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createAnnotationsStandaloneGroup(Composite composite) {
		// new AnnotationsStandaloneGroup(composite, getEJBDataModel(), false);
	}
	
	@Override
	protected String getModuleFacetID(){
		return J2EEProjectUtilities.EJB;
	}	
}
