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
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.eclipse.jst.ejb.ui.internal.actions;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jst.ejb.ui.internal.wizard.EJBComponentExportWizard;
import org.eclipse.jst.j2ee.internal.actions.BaseAction;
import org.eclipse.jst.j2ee.internal.plugin.J2EEUIPlugin;
import org.eclipse.swt.widgets.Shell;


/**
 * @author jsholl
 * 
 * To change this generated comment go to Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ExportEJBAction extends BaseAction {
	public static String LABEL = Platform.getResourceString(J2EEUIPlugin.getDefault().getBundle(), "%ejb.export.action.description_ui_"); //$NON-NLS-1$
	private static final String ICON = "export_ejbjar_wiz"; //$NON-NLS-1$

	public ExportEJBAction() {
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
		EJBComponentExportWizard wizard = new EJBComponentExportWizard();
		J2EEUIPlugin plugin = J2EEUIPlugin.getDefault();
		wizard.init(plugin.getWorkbench(), selection);

		WizardDialog dialog = new WizardDialog(shell, wizard);
		dialog.create();
		dialog.open();
	}

}
