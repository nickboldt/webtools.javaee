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
/*
 * Created on Jan 6, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.eclipse.jst.servlet.tomcat.tests;

import junit.framework.Test;
import junit.framework.TestCase;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jst.j2ee.application.internal.operations.IAnnotationsDataModel;
import org.eclipse.jst.j2ee.internal.J2EEVersionConstants;
import org.eclipse.jst.j2ee.internal.common.operations.INewJavaClassDataModelProperties;
import org.eclipse.jst.j2ee.internal.web.archive.operations.WebFacetProjectCreationDataModelProvider;
import org.eclipse.jst.j2ee.internal.web.operations.AddServletOperation;
import org.eclipse.jst.j2ee.internal.web.operations.INewServletClassDataModelProperties;
import org.eclipse.jst.j2ee.internal.web.operations.NewServletClassDataModelProvider;
import org.eclipse.jst.j2ee.web.project.facet.IWebFacetInstallDataModelProperties;
import org.eclipse.wst.common.componentcore.datamodel.properties.IFacetDataModelProperties;
import org.eclipse.wst.common.componentcore.datamodel.properties.IFacetProjectCreationDataModelProperties;
import org.eclipse.wst.common.componentcore.datamodel.properties.IFacetProjectCreationDataModelProperties.FacetDataModelMap;
import org.eclipse.wst.common.componentcore.internal.operation.IArtifactEditOperationDataModelProperties;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelOperation;
import org.eclipse.wst.common.tests.LogUtility;
import org.eclipse.wst.common.tests.ProjectUtility;
import org.eclipse.wst.common.tests.SimpleTestSuite;
import org.eclipse.wst.common.tests.TaskViewUtility;

/** 
 * @author blancett
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class WebProjectCreationTomcatTest extends TestCase {
    
    public void createSimpleProject(String projectName) throws Exception {
        IDataModel dataModel = DataModelFactory.createDataModel(new WebFacetProjectCreationDataModelProvider());
        dataModel.setProperty(IFacetProjectCreationDataModelProperties.FACET_PROJECT_NAME, projectName);
        setServerTargetProperty(dataModel);
        dataModel.getDefaultOperation().execute(new NullProgressMonitor(), null);
    }

    /**
     * @param dataModel
     */
    public void setServerTargetProperty(IDataModel dataModel) {
        dataModel.setProperty(IFacetProjectCreationDataModelProperties.FACET_RUNTIME, AllTomcatTests.TOMCAT_RUNTIME.getId());
    }

    public static void createServlet(IDataModel model) throws Exception {
         AddServletOperation op = new AddServletOperation(model);
         op.execute(null,null);
         ProjectUtility.verifyProject(op.getTargetProject().getName(), true); 
         TaskViewUtility.verifyNoErrors();
    }

    public IDataModel setupStandaloneWebProject(String projectName, int j2eeVersion) throws Exception {
        createSimpleProject(projectName);
        IDataModel model = getWebComponentCreationDataModel(projectName, j2eeVersion);
        createStandaloneWebProject(model);
        createServlet(projectName);
        return model;
    }

    public IDataModel setupStandaloneAnnotatedWebProject(String projectName, int j2eeVersion) throws Exception {
        createSimpleProject(projectName);

        IDataModel model = getWebComponentCreationDataModel(projectName, j2eeVersion);
        createStandaloneWebProject(model);
        createAnnotatedServlet(projectName);
        return model;
    }
    
    public static void createStandaloneWebProject(IDataModel model) throws Exception {
        IDataModelOperation webOp = model.getDefaultOperation();
        webOp.execute(new NullProgressMonitor(), null);
        // ProjectUtility.verifyProject(model.getTargetProject().getName(),
        // true);
        TaskViewUtility.verifyNoErrors();
    }
    
    private IDataModel getWebComponentCreationDataModel(String projectName, int j2eeVersion) {
        IProject javaProject = ProjectUtility.getProject(projectName);
        String moduleName = projectName + "WebModule"; //$NON-NLS-1$
        String moduleDeployName = moduleName + ".war"; //$NON-NLS-1$
        IDataModel model = DataModelFactory.createDataModel(IWebFacetInstallDataModelProperties.class);
        model.setProperty(IFacetProjectCreationDataModelProperties.FACET_PROJECT_NAME, javaProject.getName());
        FacetDataModelMap map = (FacetDataModelMap) model.getProperty(IFacetProjectCreationDataModelProperties.FACET_DM_MAP);
        IDataModel webModel = map.getFacetDataModel(IWebFacetInstallDataModelProperties.DYNAMIC_WEB);
        webModel.setIntProperty(IFacetDataModelProperties.FACET_VERSION,j2eeVersion);
        return model;
    }

    /**
     * @throws Exception
     */
    private void createServlet(String projectName) throws Exception {
        IDataModel servletModel = setupServletCreationDataModel(projectName, false);
        createServlet(servletModel);
    }

    /**
     * @throws Exception
     */
    private void createAnnotatedServlet(String projectName) throws Exception {
        IDataModel servletModel = setupServletCreationDataModel(projectName, true);
        createServlet(servletModel);
    }

    public IDataModel setupServletCreationDataModel(String projectName, boolean isAnnotated) {
    	IDataModel servletDataModel = DataModelFactory.createDataModel(NewServletClassDataModelProvider.class);
        servletDataModel.setProperty(IArtifactEditOperationDataModelProperties.PROJECT_NAME, projectName);
        servletDataModel.setProperty(IArtifactEditOperationDataModelProperties.COMPONENT_NAME, projectName);
        servletDataModel.setProperty(INewJavaClassDataModelProperties.CLASS_NAME, "FooServlet"); //$NON-NLS-1$
        servletDataModel.setProperty(INewServletClassDataModelProperties.DISPLAY_NAME, "FooServlet"); //$NON-NLS-1$
        servletDataModel.setBooleanProperty(IAnnotationsDataModel.USE_ANNOTATIONS, isAnnotated);
        return servletDataModel;
    }

    public void createVaildProjectAndServletCreation(String projectName, int j2eeVersion) throws Exception {
        LogUtility.getInstance().resetLogging();
        IDataModel model = null;
        model = setupStandaloneWebProject(projectName, j2eeVersion);
        LogUtility.getInstance().verifyNoWarnings();
        checkValidDataModel(model);
    }

    public void createVaildAnnotatedProjectAndServletCreation(String projectName, int j2eeVersion) throws Exception {
        LogUtility.getInstance().resetLogging();
        IDataModel model = null;
        model = setupStandaloneAnnotatedWebProject(projectName, j2eeVersion);
        LogUtility.getInstance().verifyNoWarnings();
        checkValidDataModel(model);
    }

    /**
     * @param model
     */
    protected void checkValidDataModel(IDataModel model) {
        // DataModelVerifier verifier =
        // DataModelVerifierFactory.getInstance().createVerifier(model);
        // try {
        // verifier.verify(model);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }

    }

    public void testVaild12WebProjectNameCreation() throws Exception {
        ProjectUtility.deleteAllProjects();
        createVaildProjectAndServletCreation("FooTomcatWebProject12", J2EEVersionConstants.WEB_2_2_ID); //$NON-NLS-1$
    }

    /*
     * TODO Uncomment this method when Annotation support for servlets is
     * enabled public void testVaild12AnnotatedWebProjectNameCreation() throws
     * Exception { ProjectUtility.deleteAllProjects();
     * createVaildAnnotatedProjectAndServletCreation("FooAnnotatedTomcatWebProject12",
     * J2EEVersionConstants.WEB_2_2_ID); }
     */

    public void testVaild13WebProjectNameCreation() throws Exception {
        ProjectUtility.deleteAllProjects();
        createVaildProjectAndServletCreation("Foo1TomcatWebProject13", J2EEVersionConstants.WEB_2_3_ID); //$NON-NLS-1$
    }

    /*
     * TODO Uncomment this method when Annotation support for servlets is
     * enabled public void testVaild13AnnotatedWebProjectNameCreation() throws
     * Exception { ProjectUtility.deleteAllProjects();
     * createVaildAnnotatedProjectAndServletCreation("Foo1TomcatWebProject13",
     * J2EEVersionConstants.WEB_2_3_ID); }
     */

    public void testVaild14WebProjectNameCreation() throws Exception {
        ProjectUtility.deleteAllProjects();
        createVaildProjectAndServletCreation("Foo1TomcatWebProject14", J2EEVersionConstants.WEB_2_4_ID); //$NON-NLS-1$
    }

    /*
     * TODO Uncomment this method when Annotation support for servlets is
     * enabled public void testVaild14AnnotatedWebProjectNameCreation() throws
     * Exception { ProjectUtility.deleteAllProjects();
     * createVaildAnnotatedProjectAndServletCreation("Foo1TomcatWebProject14",
     * J2EEVersionConstants.WEB_2_4_ID); }
     */

    public static Test suite() {
        return new SimpleTestSuite(WebProjectCreationTomcatTest.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.wtp.j2ee.headless.tests.j2ee.operations.ModuleProjectCreationOperationTest#getProjectCreationDataModel()
     */
    public IDataModel getProjectCreationDataModel() {
        return null;
    }

}
