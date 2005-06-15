/*
 * Created on Jan 6, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.eclipse.wtp.j2ee.headless.tests.jca.operations;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wtp.j2ee.headless.tests.j2ee.operations.ModuleExportOperationTestCase;
import org.eclipse.wtp.j2ee.headless.tests.j2ee.operations.ModuleImportOperationTestCase;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class RARExportOperationTest extends ModuleExportOperationTestCase {
	
	public RARExportOperationTest(String name) {
		super(name);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.wtp.j2ee.headless.tests.j2ee.operations.ModuleExportOperationTestCase#suite()
	 */
	public static Test suite() {
		return new TestSuite(RARExportOperationTest.class);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wtp.j2ee.headless.tests.j2ee.operations.ModuleExportOperationTestCase#getModelInstance()
	 */
	protected IDataModel getModelInstance() {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.wtp.j2ee.headless.tests.j2ee.operations.ModuleExportOperationTestCase#getImportTestCase()
	 */
	protected ModuleImportOperationTestCase getImportTestCase() { 
		return new RARImportOperationTest("");
	}
	
	/**
	 * @return
	 */
	public String getModuleExportFileExt() {
		return ".rar";
	}

}
