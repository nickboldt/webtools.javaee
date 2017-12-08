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
/*
 * Created on Mar 27, 2003
 * 
 * To change this generated comment go to Window>Preferences>Java>Code
 * Generation>Code and Comments
 */
package org.eclipse.jst.ejb.ui.internal.actions;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jst.ejb.ui.internal.wizard.EJBComponentImportWizard;
import org.eclipse.jst.j2ee.internal.actions.BaseAction;
import org.eclipse.jst.j2ee.internal.plugin.J2EEUIPlugin;
import org.eclipse.swt.widgets.Shell;


/**
 * @author jsholl
 * 
 * To change this generated comment go to Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ImportEJBAction extends BaseAction {
	public static String LABEL = EJBArchiveUIResourceHandler.getString("Import_EJBJar"); //$NON-NLS-1$
	private static final String ICON = "import_ejbjar_wiz"; //$NON-NLS-1$

	public ImportEJBAction() {
		super();
		setText(LABEL);
		setImageDescriptor(J2EEUIPlugin.getDefault().getImageDescriptor(ICON));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jst.j2ee.internal.common.actions.BaseAction#primRun(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void primRun(Shell shell) {

		EJBComponentImportWizard wizard = new EJBComponentImportWizard();

		J2EEUIPlugin plugin = J2EEUIPlugin.getDefault();

		wizard.init(plugin.getWorkbench(), StructuredSelection.EMPTY);

		WizardDialog dialog = new WizardDialog(shell, wizard);
		dialog.create();
		dialog.open();
	}

}
