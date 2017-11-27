/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.j2ee.ejb.annotation.ui.internal.preferences;

import org.eclipse.osgi.util.NLS;

public final class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.jst.j2ee.ejb.annotation.ui.internal.preferences.preferences";//$NON-NLS-1$

	private Messages() {
		// Do not instantiate
	}

	public static String label_set_j2ee_annotation_preference;
	public static String label_active_annotation_provider;
	public static String desc_active_annotation_provider;

	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
}