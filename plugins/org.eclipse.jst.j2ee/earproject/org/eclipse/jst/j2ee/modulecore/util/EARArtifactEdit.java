/***************************************************************************************************
 * Copyright (c) 2003, 2004 IBM Corporation and others. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: IBM Corporation - initial API and implementation
 **************************************************************************************************/

package org.eclipse.jst.j2ee.modulecore.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jst.j2ee.application.Application;
import org.eclipse.jst.j2ee.application.ApplicationFactory;
import org.eclipse.jst.j2ee.application.ApplicationResource;
import org.eclipse.jst.j2ee.internal.J2EEConstants;
import org.eclipse.jst.j2ee.internal.common.XMLResource;
import org.eclipse.jst.j2ee.internal.modulecore.util.EnterpriseArtifactEdit;
import org.eclipse.wst.common.componentcore.ArtifactEditModel;
import org.eclipse.wst.common.componentcore.StructureEdit;
import org.eclipse.wst.common.componentcore.ModuleCoreNature;
import org.eclipse.wst.common.componentcore.UnresolveableURIException;
import org.eclipse.wst.common.componentcore.internal.ReferencedComponent;
import org.eclipse.wst.common.componentcore.internal.WorkbenchComponent;
import org.eclipse.wst.common.componentcore.internal.util.IModuleConstants;

/**
 * <p>
 * EARArtifactEdit obtains an {@see org.eclipse.jst.j2ee.application.Application}&nbsp;metamodel.
 * The {@see org.eclipse.jst.j2ee.application.ApplicationResource}&nbsp; which stores the metamodel
 * is retrieved from the {@see org.eclipse.wst.common.modulecore.ArtifactEditModel}&nbsp;using a
 * cached constant (@see
 * org.eclipse.jst.j2ee.commonarchivecore.internal.helpers.ArchiveConstants#APPLICATION_DD_URI). The
 * defined methods extract data or manipulate the contents of the underlying resource.
 * </p>
 */

public class EARArtifactEdit extends EnterpriseArtifactEdit {

	public static final Class ADAPTER_TYPE = EARArtifactEdit.class;
	/**
	 * <p>
	 * Identifier used to group and query common artifact edits.
	 * </p>
	 */
	public static String TYPE_ID = "jst.ear"; //$NON-NLS-1$
	

	/**
	 * <p>
	 * Returns an instance facade to manage the underlying edit model for the given
	 * {@see WorkbenchComponent}. Instances of EARArtifactEdit that are returned through this method
	 * must be {@see #dispose()}ed of when no longer in use.
	 * </p>
	 * <p>
	 * Use to acquire an EARArtifactEdit facade for a specific {@see WorkbenchComponent}&nbsp;that will not
	 * be used for editing. Invocations of any save*() API on an instance returned from this method
	 * will throw exceptions.
	 * </p>
	 * <p>
	 * <b>This method may return null. </b>
	 * </p>
	 * 
	 * @param aModule
	 *            A valid {@see WorkbenchComponent}&nbsp;with a handle that resolves to an accessible
	 *            project in the workspace
	 * @return An instance of EARArtifactEdit that may only be used to read the underlying content
	 *         model
	 * @throws UnresolveableURIException
	 *             could not resolve uri.
	 */
	public static EARArtifactEdit getEARArtifactEditForRead(WorkbenchComponent aModule) {
		try {
			if (isValidEARModule(aModule)) {
				IProject project = StructureEdit.getContainingProject(aModule);
				ModuleCoreNature nature = ModuleCoreNature.getModuleCoreNature(project);
				return new EARArtifactEdit(nature, aModule, true);
			}
		} catch (UnresolveableURIException uue) {
		}
		return null;
	}


	/**
	 * <p>
	 * Returns an instance facade to manage the underlying edit model for the given
	 * {@see WorkbenchComponent}. Instances of WebArtifactEdit that are returned through this method
	 * must be {@see #dispose()}ed of when no longer in use.
	 * </p>
	 * <p>
	 * Use to acquire an EARArtifactEdit facade for a specific {@see WorkbenchComponent}&nbsp;that
	 * will be used for editing.
	 * </p>
	 * <p>
	 * <b>This method may return null. </b>
	 * </p>
	 * 
	 * @param aModule
	 *            A valid {@see WorkbenchComponent}&nbsp;with a handle that resolves to an accessible
	 *            project in the workspace
	 * @return An instance of EARArtifactEdit that may be used to modify and persist changes to the
	 *         underlying content model
	 */
	public static EARArtifactEdit getEARArtifactEditForWrite(WorkbenchComponent aModule) {
		try {
			if (isValidEARModule(aModule)) {
				IProject project = StructureEdit.getContainingProject(aModule);
				ModuleCoreNature nature = ModuleCoreNature.getModuleCoreNature(project);
				return new EARArtifactEdit(nature, aModule, false);
			}
		} catch (UnresolveableURIException uue) {
		}
		return null;
	}

	/**
	 * @param module
	 *            A {@see WorkbenchComponent}
	 * @return True if the supplied module
	 *         {@see ArtifactEdit#isValidEditableModule(WorkbenchComponent)}and the moduleTypeId is a
	 *         JST module
	 */
	public static boolean isValidEARModule(WorkbenchComponent aModule) throws UnresolveableURIException {
		if (!isValidEditableModule(aModule))
			return false;
		/* and match the JST_WEB_MODULE type */
		if (!TYPE_ID.equals(aModule.getComponentType().getComponentTypeId()))
			return false;
		return true;
	}

	/**
	 * <p>
	 * Creates an instance facade for the given {@see ArtifactEditModel}.
	 * </p>
	 * 
	 * @param anArtifactEditModel
	 */
	public EARArtifactEdit(ArtifactEditModel model) {
		super(model);
	}	
	
	/**
	 * <p>
	 * Creates an instance facade for the given {@see ArtifactEditModel}
	 * </p>
	 * 
	 * @param aNature
	 *            A non-null {@see ModuleCoreNature}for an accessible project
	 * @param aModule
	 *            A non-null {@see WorkbenchComponent}pointing to a module from the given
	 *            {@see ModuleCoreNature}
	 */


	public EARArtifactEdit(ModuleCoreNature aNature, WorkbenchComponent aModule, boolean toAccessAsReadOnly) {
		super(aNature, aModule, toAccessAsReadOnly);
	}

	/**
	 * <p>
	 * Retrieves J2EE version information from ApplicationResource.
	 * </p>
	 * 
	 * @return an integer representation of a J2EE Spec version
	 *  
	 */
	public int getJ2EEVersion() {
		return getApplicationXmiResource().getJ2EEVersionID();
	}

	/**
	 * 
	 * @return ApplicationResource from (@link getDeploymentDescriptorResource())
	 *  
	 */

	public ApplicationResource getApplicationXmiResource() {
		return (ApplicationResource) getDeploymentDescriptorResource();
	}

	/**
	 * <p>
	 * Obtains the Application {@see Application}root object from the {@see ApplicationResource},
	 * the root object contains all other resource defined objects.
	 * </p>
	 * 
	 * @return Application
	 *  
	 */

	public Application getApplication() {
		return (Application) getDeploymentDescriptorRoot();
	}

	/**
	 * <p>
	 * Retrieves the resource from the {@see ArtifactEditModel}
	 * </p>
	 * 
	 * @return Resource
	 *  
	 */

	public Resource getDeploymentDescriptorResource() {
		return getArtifactEditModel().getResource(URI.createURI(J2EEConstants.APPLICATION_DD_URI));
	}

	
	/**
	 * <p>
	 * Creates a deployment descriptor root object (Application) and populates with data. Adds the root
	 * object to the deployment descriptor resource.
	 * </p>
	 * 
	 * <p>
	 * 
	 * @param aModule
	 *            A non-null pointing to a {@see XMLResource}
	 * @param version
	 * 			Version to be set on resource....if null default is taken
	 * 
	 * Note: This method is typically used for JUNIT - move?
	 * </p>
	 */
	protected void addApplicationIfNecessary(XMLResource aResource) {
	    if (aResource != null) {
		    if(aResource.getContents() == null || aResource.getContents().isEmpty()) {
		        Application newApp = ApplicationFactory.eINSTANCE.createApplication();
				aResource.getContents().add(newApp);
		    } 
		    Application application = (Application)aResource.getContents().get(0);
			URI moduleURI = getArtifactEditModel().getModuleURI();
			try {
				application.setDisplayName(StructureEdit.getDeployedName(moduleURI));
			} catch (UnresolveableURIException e) {
			}
			aResource.setID(application, J2EEConstants.APPL_ID);
			//TODO add more mandatory elements
		}
	}
	/**
	 * Checks if the uri mapping already exists.
	 * 
	 * @param String
	 *            currentURI - The current uri of the module.
	 * @return boolean
	 */
	public boolean uriExists(String currentURI) {
		if (currentURI != null) {
		    List refComponents = module.getReferencedComponents();
		    if(refComponents == null) return false;
		    ReferencedComponent comp = null;
		    for (int i = 0; i < refComponents.size(); i++){
		        comp = (ReferencedComponent)refComponents.get(i);
		        if(comp.getHandle().equals(currentURI))
		            return true;
		    }
		} // if
		return false;
	} // uriExists


	/* (non-Javadoc)
	 * @see org.eclipse.jst.j2ee.internal.modulecore.util.EnterpriseArtifactEdit#createModelRoot()
	 */
	public EObject createModelRoot() {
	    return createModelRoot(getJ2EEVersion());
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jst.j2ee.internal.modulecore.util.EnterpriseArtifactEdit#createModelRoot(java.lang.Integer)
	 */
	public EObject createModelRoot(int version) {
	    ApplicationResource res = (ApplicationResource)getDeploymentDescriptorResource();
		res.setModuleVersionID(version);
		addApplicationIfNecessary(res);
		return ((ApplicationResource)getDeploymentDescriptorResource()).getRootObject();
	}
	/**
	 * @param WorkbenchComponent
	 * @return - a list of util modules referred by a given j2ee module
	 */
	public  List getWorkbenchUtilModules(WorkbenchComponent module) {
		if(module.getComponentType().getComponentTypeId().equals(IModuleConstants.JST_EAR_MODULE)) {
		List utilComponents = new ArrayList();
		List refComponents = module.getReferencedComponents();
		for(int i = 0; i < refComponents.size(); i++) {
			WorkbenchComponent component = (WorkbenchComponent)refComponents.get(i);
			if(component.getComponentType().getComponentTypeId().equals(IModuleConstants.JST_UTILITY_MODULE));
				utilComponents.add(component);
		 }
		 return utilComponents;
	  }
	  return null;
	}
	
	public List getWorkbenchJ2EEModules(WorkbenchComponent module) {
		if(module.getComponentType().getComponentTypeId().equals(IModuleConstants.JST_EAR_MODULE)) {
		List j2eeComponents = new ArrayList();
		List refComponents = module.getReferencedComponents();
		for(int i = 0; i < refComponents.size(); i++) {
			WorkbenchComponent component = (WorkbenchComponent)refComponents.get(i);
			String typeId = component.getComponentType().getComponentTypeId();
			if(typeId.equals(IModuleConstants.JST_EJB_MODULE) || typeId.equals(IModuleConstants.JST_WEB_MODULE) || typeId.equals(IModuleConstants.JST_APPCLIENT_MODULE) || typeId.equals(IModuleConstants.JST_CONNECTOR_MODULE))
				j2eeComponents.add(component);
		 }
		 return j2eeComponents;
	  }
	  return null;
	}
}