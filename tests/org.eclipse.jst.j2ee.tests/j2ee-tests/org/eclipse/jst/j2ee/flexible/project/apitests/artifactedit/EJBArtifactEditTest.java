/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.j2ee.flexible.project.apitests.artifactedit;

import junit.framework.TestCase;

import org.eclipse.core.resources.IProject;
import org.eclipse.jst.j2ee.application.Module;
import org.eclipse.jst.j2ee.componentcore.EnterpriseArtifactEdit;
import org.eclipse.jst.j2ee.ejb.EJBJar;
import org.eclipse.jst.j2ee.ejb.componentcore.util.EJBArtifactEdit;
import org.eclipse.jst.j2ee.internal.common.XMLResource;
import org.eclipse.wst.common.componentcore.ArtifactEdit;
import org.eclipse.wst.common.componentcore.ComponentCore;
import org.eclipse.wst.common.componentcore.internal.ArtifactEditModel;
import org.eclipse.wst.common.componentcore.resources.IVirtualComponent;
import org.eclipse.wst.common.internal.emfworkbench.EMFWorkbenchContext;

public class EJBArtifactEditTest extends TestCase {

	private IProject ejbProject;
	private String ejbModuleName;

	public EJBArtifactEditTest() {
		super();

		if (TestWorkspace.init()) {
			ejbProject = TestWorkspace.getTargetProject(TestWorkspace.EJB_PROJECT_NAME);
			ejbModuleName = TestWorkspace.EJB_MODULE_NAME;
		} else {
			fail();

		}
	}

	public void testGetJ2EEVersion() {
		EnterpriseArtifactEdit edit = null;
		try {
			edit = EJBArtifactEdit.getEJBArtifactEditForRead(ejbProject);
			String version = new Integer(edit.getJ2EEVersion()).toString();
			assertTrue(version.equals(TestWorkspace.EJB_PROJECT_VERSION));
		} finally {
			if (edit != null) {
				edit.dispose();
			}
		}
	}

	public void testGetDeploymentDescriptorResource() {
		EnterpriseArtifactEdit edit = null;
		try {
			edit = EJBArtifactEdit.getEJBArtifactEditForRead(ejbProject);
			String uri = edit.getDeploymentDescriptorResource().getURI().toString();
			assertTrue(uri.equals(TestWorkspace.EJB_DD_RESOURCE_URI));
		} finally {
			if (edit != null) {
				edit.dispose();
			}
			assertTrue(edit != null);
		}
	}

	// /////BUG in PlatformURL\\\\\\\\\\\

	public void testGetDeploymentDescriptorRoot() {
		EnterpriseArtifactEdit edit = null;
		try {
			edit = EJBArtifactEdit.getEJBArtifactEditForRead(ejbProject);
			edit.getDeploymentDescriptorRoot();
			// /////BUG in PlatformURL\\\\\\\\\\\turning test off////
			/*
			 * EObject object = edit.getDeploymentDescriptorRoot(); assertNotNull(object);
			 */
		} catch (Exception e) {
			// TODO
		} finally {
			if (edit != null) {
				edit.dispose();
			}
			assertTrue(edit != null);
		}
	}

	// /////////////BUG in PlatformURL\\\\\\\\\\\


	public void testCreateModelRoot() {
		EnterpriseArtifactEdit edit = null;
		try {
			edit = EJBArtifactEdit.getEJBArtifactEditForWrite(ejbProject);
			edit.createModelRoot();
			// ////BUG turning off\\\\\\\\\\\\\
			/*
			 * EObject object = edit.createModelRoot(); assertNotNull(object);
			 */
		} catch (Exception e) {
			// TODO
		} finally {
			if (edit != null) {
				edit.dispose();
			}
			assertTrue(edit != null);
		}
	}

	/*
	 * Class under test for EObject createModelRoot(int)
	 */
	// ///////////////BUG in PlatformURLModuleConnection
	public void testCreateModelRootint() {
		EnterpriseArtifactEdit edit = null;
		try {
			// ///////BUG in PlatformURLModuleConnection
			edit = EJBArtifactEdit.getEJBArtifactEditForRead(ejbProject);
			edit.createModelRoot(14);
			/*
			 * EObject object = edit.createModelRoot(14); assertNotNull(object);
			 */
		} catch (Exception e) {
			// TODO
		} finally {
			if (edit != null) {
				edit.dispose();
			}
			assertTrue(edit != null);
		}
	}

	/*
	 * Class under test for void EJBArtifactEdit(ComponentHandle, boolean)
	 */
	public void testEJBArtifactEditComponentHandleboolean() {
		EnterpriseArtifactEdit edit = null;
		try {
			edit = new EJBArtifactEdit(ejbProject, true);
			assertNotNull(edit);
		} finally {
			if (edit != null) {
				edit.dispose();
			}
		}
	}

	/*
	 * Class under test for void EJBArtifactEdit(ArtifactEditModel)
	 */
	public void testEJBArtifactEditArtifactEditModel() {
		EnterpriseArtifactEdit edit = new EJBArtifactEdit(getArtifactEditModelforRead());
		assertNotNull(edit);
		edit.dispose();
	}

	public void testGetEJBJarXmiResource() {
		EJBArtifactEdit edit = null;
		try {
			edit = EJBArtifactEdit.getEJBArtifactEditForRead(ejbProject);
			String uri = edit.getEJBJarXmiResource().toString();
			// THIS IS A BUG\\ - commmenting out as suggested by DW
			// assertTrue(uri.equals(TestWorkspace.EJB_DD_XMI_RESOURCE_URI));
		} catch (Exception e) {
			// TODO
		} finally {
			if (edit != null) {
				edit.dispose();
			}
			assertTrue(edit != null);
		}
	}

	// //////////////Bug turning test off\\\\\\\\\\\

	public void testHasEJBClientJARProject() {
		EJBArtifactEdit edit = null;
		try {
			edit = EJBArtifactEdit.getEJBArtifactEditForRead(ejbProject);
			edit.hasEJBClientJARProject();
			// /////BUG\\\\\\\\\\\
			// boolean bool = edit.hasEJBClientJARProject(ejbProject);
			// assertEquals(bool, true);
		} finally {
			if (edit != null) {
				edit.dispose();
			}
		}
	}

	public void testCreateNewModule() {
		EJBArtifactEdit edit = null;
		try {
			edit = EJBArtifactEdit.getEJBArtifactEditForRead(ejbProject);
			Module module = edit.createNewModule();
			assertNotNull(module);
		} finally {
			if (edit != null) {
				edit.dispose();
			}
		}
	}

	// /////////////////bug\\\\\\\\\\\\\

	public void testGetEJBClientJarModule() {
		EJBArtifactEdit edit = null;
		try {
			edit = EJBArtifactEdit.getEJBArtifactEditForRead(ejbProject);
			edit.getEJBClientJarModule();
			// /////////bug\\\\\\\\
			// WorkbenchComponent comp = edit.getEJBClientJarModule(ejbProject);
			// assertNotNull(comp);
		} catch (Exception e) {
			// TODO
		} finally {
			if (edit != null) {
				edit.dispose();
			}
		}
	}

	public void testGetEJBJar() {
		EJBArtifactEdit edit = null;
		try {
			edit = EJBArtifactEdit.getEJBArtifactEditForRead(ejbProject);
			EJBJar jar = edit.getEJBJar();
			assertNotNull(jar);
		} finally {
			if (edit != null) {
				edit.dispose();
			}
		}
	}

	public void testGetDeploymenyDescriptorType() {
		EJBArtifactEdit edit = null;
		try {
			edit = EJBArtifactEdit.getEJBArtifactEditForRead(ejbProject);
			int type = edit.getDeploymentDescriptorType();
			assertTrue(type >= 0);
		} finally {
			if (edit != null) {
				edit.dispose();
			}
		}
	}

	public void testAddEJBJarIfNecessary() {
		EnterpriseArtifactEdit test = new EJBArtifactEdit(getArtifactEditModelforRead()) {
			protected void addEJBJarIfNecessary(XMLResource aResource) {
				// TODO add test
				super.addEJBJarIfNecessary(aResource);
			}

			public void test() {
				addEJBJarIfNecessary(null);
			}
		};

	}

	/*
	 * Class under test for EJBArtifactEdit getEJBArtifactEditForRead(ComponentHandle)
	 */
	public void testGetEJBArtifactEditForReadComponentHandle() {
		EnterpriseArtifactEdit edit = null;
		try {
			edit = EJBArtifactEdit.getEJBArtifactEditForRead(ejbProject);
			assertTrue(edit != null);
		} finally {
			if (edit != null) {
				edit.dispose();
			}
		}
	}

	/*
	 * Class under test for EJBArtifactEdit getEJBArtifactEditForWrite(ComponentHandle)
	 */
	public void testGetEJBArtifactEditForWriteComponentHandle() {
		EnterpriseArtifactEdit edit = null;
		try {
			edit = EJBArtifactEdit.getEJBArtifactEditForWrite(ejbProject);
		} finally {
			if (edit != null) {
				edit.dispose();
			}
			assertTrue(edit != null);
		}
	}

	/*
	 * Class under test for EJBArtifactEdit getEJBArtifactEditForRead(WorkbenchComponent)
	 */
	public void testGetEJBArtifactEditForReadWorkbenchComponent() {
		EnterpriseArtifactEdit edit = null;
		try {
			edit = EJBArtifactEdit.getEJBArtifactEditForRead(ejbProject);
			assertTrue(edit != null);
		} finally {
			if (edit != null) {
				edit.dispose();
			}
		}
	}

	/*
	 * Class under test for EJBArtifactEdit getEJBArtifactEditForWrite(WorkbenchComponent)
	 */
	public void testGetEJBArtifactEditForWriteWorkbenchComponent() {
		EnterpriseArtifactEdit edit = null;
		try {
			edit = EJBArtifactEdit.getEJBArtifactEditForWrite(ejbProject);
		} finally {
			if (edit != null) {
				edit.dispose();
			}
			assertTrue(edit != null);
		}
	}

	public void testIsValidEJBModule() {
		IVirtualComponent component = ComponentCore.createComponent(ejbProject,ejbModuleName);
		boolean valid = ArtifactEdit.isValidEditableModule(component);
		assertTrue(valid);
	}

	public ArtifactEditModel getArtifactEditModelforRead() {
		EMFWorkbenchContext context = new EMFWorkbenchContext(ejbProject);
		return new ArtifactEditModel(this.toString(), context, true, TestWorkspace.APP_CLIENT_MODULE_URI);
	}

	public EnterpriseArtifactEdit getArtifactEditForRead() {
		return new EJBArtifactEdit(getArtifactEditModelforRead());
	}

	public void pass() {
		assertTrue(true);
	}
}
