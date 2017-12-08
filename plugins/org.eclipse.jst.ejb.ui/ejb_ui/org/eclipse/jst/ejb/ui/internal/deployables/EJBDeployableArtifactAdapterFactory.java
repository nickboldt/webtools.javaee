/***************************************************************************************************
 * Copyright (c) 2005 IBM Corporation and others. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: IBM Corporation - initial API and implementation
 **************************************************************************************************/

/*
 * Created on Jan 18, 2005
 */
package org.eclipse.jst.ejb.ui.internal.deployables;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.debug.ui.actions.ILaunchable;
import org.eclipse.jst.j2ee.ejb.internal.deployables.EJBDeployableArtifactAdapterUtil;
import org.eclipse.wst.server.core.IModuleArtifact;
import org.eclipse.wst.server.core.model.ModuleArtifactAdapterDelegate;

public class EJBDeployableArtifactAdapterFactory extends ModuleArtifactAdapterDelegate implements IAdapterFactory {

	public Object getAdapter(Object adaptableObject, Class adapterType) {
      return null;
	}

	public Class[] getAdapterList() {
		return new Class[]{ILaunchable.class};
	}

	@Override
	public IModuleArtifact getModuleArtifact(Object obj) {
		return EJBDeployableArtifactAdapterUtil.getModuleObject(obj);
	}

}
