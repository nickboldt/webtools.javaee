/***************************************************************************************************
 * Copyright (c) 2003, 2006 IBM Corporation and others. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: IBM Corporation - initial API and implementation
 *				 David Schneider, david.schneider@unisys.com - [142500] WTP properties pages fonts don't follow Eclipse preferences
 **************************************************************************************************/
package org.eclipse.jst.ejb.ui.internal.wizard;

import org.eclipse.core.resources.IProject;
import org.eclipse.jem.util.emf.workbench.ProjectUtilities;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jst.ejb.ui.internal.util.EJBUIMessages;
import org.eclipse.jst.j2ee.ejb.EJBJar;
import org.eclipse.jst.j2ee.internal.actions.IJ2EEUIContextIds;
import org.eclipse.jst.j2ee.internal.ejb.archiveoperations.IEjbClientProjectCreationDataModelProperties;
import org.eclipse.jst.j2ee.internal.plugin.J2EEUIMessages;
import org.eclipse.jst.j2ee.project.EJBUtilities;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelWizardPage;

public class EJBClientComponentCreationWizardPage extends DataModelWizardPage implements IEjbClientProjectCreationDataModelProperties{
	protected EJBJar selProject = null;
	private Label selectedProjectLabel;
	private Text selectedProjectName;
	private Label clientJarURILabel;
	private Text clientJarURI;
	protected int indent = 0;
	private Label projectNameLabel;
    protected Text projectNameText = null;
    private static final String MODULE_NAME_UI = J2EEUIMessages.getResourceString(J2EEUIMessages.NAME_LABEL);    
    private static final int SIZING_TEXT_FIELD_WIDTH = 305;	

	/**
	 * @param model
	 * @param pageName
	 */
	public EJBClientComponentCreationWizardPage(IDataModel model, String pageName) {
		super(model, pageName);
		setTitle(EJBUIMessages.EJB_Client_Title);
		setDescription(EJBUIMessages.EJB_Client_Desc);
	}

	/**
	 * @param model
	 * @param pageName
	 * @param title
	 * @param titleImage
	 */
	public EJBClientComponentCreationWizardPage(IDataModel model, String pageName, String title, ImageDescriptor titleImage) {
		super(model, pageName, title, titleImage);
	}


    
	@Override
	protected Composite createTopLevelComposite(Composite parent) {
		
		Composite top = new Composite(parent, SWT.NONE);
	    top.setLayout(new GridLayout());
	    top.setData(new GridData(GridData.FILL_BOTH));
		
	
		Composite composite = new Composite(top, SWT.NONE);
		GridLayout layout = new GridLayout( 3, false );
		composite.setLayout(layout);
	
		setInfopopID(IJ2EEUIContextIds.NEW_EJB_WIZARD_P2);
		
		createProjectNameGroup(composite);
		createEJBComponentSection(composite);
		createClientJarURISection(composite);
		handleHasClientJar();
	    Dialog.applyDialogFont(parent);
		return top;
	}
    
    private void createProjectNameGroup(Composite parent) {
        // set up project name label
        projectNameLabel = new Label(parent, SWT.NONE);
        projectNameLabel.setText(MODULE_NAME_UI);
        GridData data = new GridData();
        projectNameLabel.setLayoutData(data);
        // set up project name entry field
        projectNameText = new Text(parent, SWT.BORDER);
        data = new GridData(GridData.FILL_HORIZONTAL);
        data.widthHint = SIZING_TEXT_FIELD_WIDTH;
        projectNameText.setLayoutData(data);
        new Label(parent, SWT.NONE); // pad
        synchHelper.synchText(projectNameText, PROJECT_NAME, new Control[]{projectNameLabel});
        projectNameText.setFocus();
    }
    
    private void createEJBComponentSection(Composite parent) {
    	selectedProjectLabel = new Label(parent, SWT.NONE);
    	selectedProjectLabel.setText(EJBUIMessages.EJB_Project);
        GridData data = new GridData();
        selectedProjectLabel.setLayoutData(data);

        selectedProjectName = new Text(parent, SWT.BORDER);
        data = new GridData(GridData.FILL_HORIZONTAL);
        data.widthHint = SIZING_TEXT_FIELD_WIDTH;
        selectedProjectName.setLayoutData(data);
        new Label(parent, SWT.NONE); // pad
		selectedProjectName.setEditable(false);
		synchHelper.synchText(selectedProjectName, EJB_PROJECT_NAME, new Control[]{selectedProjectLabel});
    }
   


	private void handleHasClientJar() {
		String projectName = model.getStringProperty(EJB_PROJECT_NAME);
		IProject project = ProjectUtilities.getProject( projectName );
		
		if( project.exists() && project.isAccessible()){
			enableAllSections(EJBUtilities.hasEJBClientJARProject(project));
		}
	}
	
	private void enableAllSections(boolean state) {
		projectNameText.setEnabled(state);
		projectNameLabel.setEnabled(state);
		selectedProjectLabel.setEnabled(state);
		selectedProjectName.setEnabled(state);
		clientJarURILabel.setEnabled(state);
		clientJarURI.setEnabled(state);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.wst.common.frameworks.internal.ui.wizard.WTPWizardPage#getValidationPropertyNames()
	 */
	@Override
	protected String[] getValidationPropertyNames() {
		return new String[]{PROJECT_NAME, EJB_PROJECT_NAME, CLIENT_URI };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.wst.common.frameworks.internal.ui.wizard.WTPWizardPage#showValidationErrorsOnEnter()
	 */
	@Override
	protected boolean showValidationErrorsOnEnter() {
		return true;
	}


	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	protected void enter() {
		super.enter();
	}

	private void createClientJarURISection(Composite parent) {
	    // set up project name label
		clientJarURILabel = new Label(parent, SWT.NONE);
		clientJarURILabel.setText(EJBUIMessages.Client_JAR_URI + " "); //$NON-NLS-1$
	    GridData data = new GridData();
	    clientJarURILabel.setLayoutData(data);
	
	    clientJarURI = new Text(parent, SWT.BORDER);
	    data = new GridData(GridData.FILL_HORIZONTAL);
	    data.widthHint = SIZING_TEXT_FIELD_WIDTH;
	    clientJarURI.setLayoutData(data);
	    new Label(parent, SWT.NONE); // pad
		synchHelper.synchText(clientJarURI, CLIENT_URI, new Control[]{clientJarURILabel});
	}
}