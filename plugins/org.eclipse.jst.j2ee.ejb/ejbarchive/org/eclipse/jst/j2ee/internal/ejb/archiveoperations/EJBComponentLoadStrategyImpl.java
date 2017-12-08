/*******************************************************************************
 * Copyright (c) 2003, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.j2ee.internal.ejb.archiveoperations;

import org.eclipse.jst.j2ee.ejb.componentcore.util.EJBArtifactEdit;
import org.eclipse.jst.j2ee.internal.archive.operations.J2EEComponentLoadStrategyImpl;
import org.eclipse.wst.common.componentcore.ArtifactEdit;
import org.eclipse.wst.common.componentcore.resources.IVirtualComponent;

public class EJBComponentLoadStrategyImpl extends J2EEComponentLoadStrategyImpl {

	public EJBComponentLoadStrategyImpl(IVirtualComponent vComponent) {
		this(vComponent, true);
	}

	public EJBComponentLoadStrategyImpl(IVirtualComponent vComponent, boolean includeClasspathComponents) {
		super(vComponent, includeClasspathComponents);		
	}
	@Override
	protected synchronized ArtifactEdit getArtifactEditForRead() {
		if(artifactEdit == null){
			artifactEdit = EJBArtifactEdit.getEJBArtifactEditForRead(vComponent);
		}
		return artifactEdit;
	}
}
