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
package org.eclipse.jem.tests.proxy.remote;
/*
 *  $RCSfile: TestProjectAccess.java,v $
 *  $Revision: 1.4 $  $Date: 2005/08/24 20:58:55 $ 
 */
import org.eclipse.jem.internal.proxy.core.*;
import org.eclipse.jem.internal.proxy.core.IBeanProxy;
import org.eclipse.jem.internal.proxy.core.IBeanTypeProxy;
import org.eclipse.jem.tests.proxy.AbstractTestProxy;

/**
 * @author richkulp
 *
 * Test Project Access.
 */
public class TestProjectAccess extends AbstractTestProxy {

	public TestProjectAccess() {
		super();
	}

	public TestProjectAccess(String name) {
		super(name);
	}
	
	public void testClassFromProject() {
		IBeanTypeProxy testClassType = proxyTypeFactory.getBeanTypeProxy("org.eclipse.jem.testing.proxy.remote.TestClass"); //$NON-NLS-1$
		assertNotNull(testClassType);		
	}
	
	public void testProjectClassInstantiation() throws ThrowableProxy {
		IBeanTypeProxy testClassType = proxyTypeFactory.getBeanTypeProxy("org.eclipse.jem.testing.proxy.remote.TestClass"); //$NON-NLS-1$
		IBeanProxy testClass = testClassType.newInstance();
		assertNotNull(testClass);		
	}

	public void testProjectClassMethod() throws ThrowableProxy {
		IBeanTypeProxy testClassType = proxyTypeFactory.getBeanTypeProxy("org.eclipse.jem.testing.proxy.remote.TestClass"); //$NON-NLS-1$
		IMethodProxy testMethod = testClassType.getMethodProxy("getTestString"); //$NON-NLS-1$
		assertNotNull(testMethod);		
	}
	
	public void testProjectClassMethodInvoke() throws ThrowableProxy {
		IBeanTypeProxy testClassType = proxyTypeFactory.getBeanTypeProxy("org.eclipse.jem.testing.proxy.remote.TestClass"); //$NON-NLS-1$
		IMethodProxy testMethod = testClassType.getMethodProxy("getTestString"); //$NON-NLS-1$
		IBeanProxy testClass = testClassType.newInstance();
		IStringBeanProxy aString = (IStringBeanProxy) testMethod.invoke(testClass);
		assertNotNull(aString);
		assertEquals("TESTSTRING", aString.stringValue());		
	}	

}
