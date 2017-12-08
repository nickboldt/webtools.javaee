/*******************************************************************************
 * Copyright (c) 2003, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.j2ee.internal.ejb.provider;



import java.net.URL;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.jst.j2ee.internal.plugin.J2EEPlugin;


public class EJBProviderLibrariesResourceHandler {

	private static ResourceBundle fgResourceBundle;
	public static ResourceLocator RESOURCE_LOCATOR = new ResourceLocator() {
		public URL getBaseURL() {
			return null;
		}

		public Object getImage(String arg0) {
			return J2EEPlugin.getPlugin().getImage(arg0);
		}

		public String getString(String arg0) {
			return EJBProviderLibrariesResourceHandler.getString(arg0);
		}

		public String getString(String arg0, Object[] arg1) {
			return EJBProviderLibrariesResourceHandler.getString(arg0, arg1);
		}
		public String getString(String key, boolean translate) {
			// TODO For now...  translate not supported
			return getString(key);
		}

		public String getString(String key, Object[] substitutions, boolean translate) {
			// TODO For now...  translate not supported
			return getString(key,substitutions);
		}
	};

	/**
	 * Returns the resource bundle used by all classes in this Project
	 */
	public static ResourceBundle getResourceBundle() {
		try {
			return ResourceBundle.getBundle("ejbproviderlibraries");//$NON-NLS-1$
		} catch (MissingResourceException e) {
			// does nothing - this method will return null and
			// getString(String, String) will return the key
			// it was called with
		}
		return null;
	}

	public static String getString(String key) {
		if (fgResourceBundle == null) {
			fgResourceBundle = getResourceBundle();
		}

		if (fgResourceBundle != null) {
			try {
				return fgResourceBundle.getString(key);
			} catch (MissingResourceException e) {
				return "!" + key + "!";//$NON-NLS-2$//$NON-NLS-1$
			}
		}
		return "!" + key + "!";//$NON-NLS-2$//$NON-NLS-1$
	}

	public static String getString(String key, Object[] args) {

		try {
			return MessageFormat.format(getString(key), args);
		} catch (IllegalArgumentException e) {
			return getString(key);
		}

	}
}
