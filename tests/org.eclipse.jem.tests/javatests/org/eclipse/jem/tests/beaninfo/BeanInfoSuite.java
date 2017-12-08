/*******************************************************************************
 * Copyright (c) 2003, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jem.tests.beaninfo;
/*
 *  $RCSfile: BeanInfoSuite.java,v $
 *  $Revision: 1.13 $  $Date: 2007/03/14 17:26:59 $ 
 */
import java.net.URL;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;

import org.eclipse.jem.internal.beaninfo.adapters.BeaninfoNature;
import org.eclipse.jem.tests.JavaProjectUtil;
import org.eclipse.jem.tests.JavaTestsPlugin;

/**
 * @author richkulp
 *
 * This is the true test suite for Beaninfo Testing.
 */
public class BeanInfoSuite extends TestSetup {

	// Test cases to be include in the suite
	private static final Class testsList[] =
		{ TestReflection.class, // NOTE: This one must always be first because it does some tests that depend on this.
		TestStandard.class, /*TestAWTSwingUI.class*/ };

	/**
	 * Constructor for BeanInfoSuite.
	 */
	public BeanInfoSuite(String name) {
		super(new TestSuite(name) {
			{
				for (int i = 0; i < testsList.length; i++) {
					addTestSuite(testsList[i]);
				}
			}

		});
	}
	
	public BeanInfoSuite() {
		this("Test BeanInfo Suite");
	}

	public static Test suite() {
		return new BeanInfoSuite();
	}

	private boolean oldAutoBuildingState; // autoBuilding state before we started.
	protected void setUp() throws Exception {
		System.out.println("-- Initializing the BeanInfo test data --"); //$NON-NLS-1$
		oldAutoBuildingState = JavaProjectUtil.setAutoBuild(true);
		URL installURL = JavaTestsPlugin.getPlugin().getBundle().getEntry("/");
		String[] zipPaths = new String[3];
		zipPaths[0] = FileLocator.toFileURL(new URL(installURL, "testdata/testbeaninfo.zip")).getFile();
		zipPaths[1] = FileLocator.toFileURL(new URL(installURL, "testdata/testbeaninfobeaninfos.zip")).getFile();
		zipPaths[2] = FileLocator.toFileURL(new URL(installURL, "testdata/testbeaninfopreq.zip")).getFile();
		IProject[] projects =
			JavaProjectUtil.importProjects(
				new String[] {
					AbstractBeanInfoTestCase.TEST_BEANINFO_PROJECT,
					AbstractBeanInfoTestCase.TEST_BEANINFO_BEANINFOS_PROJECT,
					AbstractBeanInfoTestCase.TEST_BEANINFO_PREREQ_PROJECT },
				zipPaths);
		assertNotNull(projects[0]);
		assertNotNull(projects[1]);
		assertNotNull(projects[2]);
		JavaProjectUtil.waitForAutoBuild();
		System.out.println("-- Data initialized --"); //$NON-NLS-1$

		BeaninfoNature nature = BeaninfoNature.getRuntime(projects[0]);
		assertNotNull(nature);
	}

	protected void tearDown() throws Exception {
		ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				JavaProjectUtil.deleteProject(JavaProjectUtil.getProject(AbstractBeanInfoTestCase.TEST_BEANINFO_PROJECT));
				JavaProjectUtil.deleteProject(JavaProjectUtil.getProject(AbstractBeanInfoTestCase.TEST_BEANINFO_BEANINFOS_PROJECT));
				JavaProjectUtil.deleteProject(JavaProjectUtil.getProject(AbstractBeanInfoTestCase.TEST_BEANINFO_PREREQ_PROJECT));
			}
		}, ResourcesPlugin.getWorkspace().getRoot(), 0, null);

		JavaProjectUtil.setAutoBuild(oldAutoBuildingState);
	}

}
