/*
 * Created on Apr 1, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.eclipse.jst.servlet.tests.bvt;

import java.net.URL;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.eclipse.core.runtime.Platform;


/**
 * @author jsholl
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AutomatedBVT extends TestSuite {

    public static String baseDirectory = System.getProperty("user.dir") + java.io.File.separatorChar + "WARImportTests" + java.io.File.separatorChar; //$NON-NLS-1$ //$NON-NLS-2$
    
    static {
        try {
            URL url = Platform.getBundle("org.eclipse.jst.servlet.tests").getEntry("/"); //$NON-NLS-1$ //$NON-NLS-2$
        	AutomatedBVT.baseDirectory = Platform.asLocalURL(url).getFile() + "TestData"+ java.io.File.separatorChar; //$NON-NLS-1$
		} catch (Exception e) { 
			System.err.println("Using working directory since a workspace URL could not be located."); //$NON-NLS-1$
		} 
    }

    public static int unimplementedMethods;

    public static void main(String[] args) {
        unimplementedMethods = 0;
        TestRunner.run(suite());
        if (unimplementedMethods > 0) {
            System.out.println("\nCalls to warnUnimpl: " + unimplementedMethods); //$NON-NLS-1$
        }
    }

    public AutomatedBVT() {
        super();
        TestSuite suite = (TestSuite) AutomatedBVT.suite();
        for (int i = 0; i < suite.testCount(); i++) {
            addTest(suite.testAt(i));
        }
    }

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for org.eclipse.jst.servlet.tests.bvt"); //$NON-NLS-1$
        //suite.addTest(AllTomcatTests.suite());
        return suite;
    }
}
