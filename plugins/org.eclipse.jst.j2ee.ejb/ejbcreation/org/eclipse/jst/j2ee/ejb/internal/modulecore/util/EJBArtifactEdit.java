package org.eclipse.jst.j2ee.ejb.internal.modulecore.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jst.j2ee.application.ApplicationPackage;
import org.eclipse.jst.j2ee.application.Module;
import org.eclipse.jst.j2ee.common.XMLResource;
import org.eclipse.jst.j2ee.ejb.EJBJar;
import org.eclipse.jst.j2ee.ejb.EJBResource;
import org.eclipse.jst.j2ee.ejb.EjbFactory;
import org.eclipse.jst.j2ee.internal.J2EEConstants;
import org.eclipse.jst.j2ee.internal.modulecore.util.EnterpriseArtifactEdit;
import org.eclipse.wst.common.modulecore.ArtifactEditModel;
import org.eclipse.wst.common.modulecore.ModuleCore;
import org.eclipse.wst.common.modulecore.ModuleCoreNature;
import org.eclipse.wst.common.modulecore.UnresolveableURIException;
import org.eclipse.wst.common.modulecore.WorkbenchComponent;

/**
 * <p>
 * EJBArtifactEdit obtains a EJB Deployment Descriptor metamodel specifec data from a
 * {@see org.eclipse.jst.j2ee.ejb.EJBResource}&nbsp; which stores the metamodel. The
 * {@see org.eclipse.jst.j2ee.ejb.EJBResource}&nbsp;is retrieved from the
 * {@see org.eclipse.wst.common.modulecore.ArtifactEditModel}&nbsp;using a constant {@see
 * J2EEConstants#EJBJAR_DD_URI_OBJ}. The defined methods extract data or manipulate the contents of
 * the underlying resource.
 * </p>
 */
public class EJBArtifactEdit extends EnterpriseArtifactEdit {
	
	/**
	 * <p>
	 * Identifier used to link EJBArtifactEdit to a EJBEditAdapterFactory {@see
	 * EJBEditAdapterFactory} stored in an AdapterManger (@see AdapterManager)
	 * </p>
	 */

	public static final Class ADAPTER_TYPE = EJBArtifactEdit.class;

	/**
	 * <p>
	 * Identifier used to group and query common artifact edits.
	 * </p>
	 */

	public static String TYPE_ID = "jst.ejb"; //$NON-NLS-1$

	/**
	 * <p>
	 * Creates an instance facade for the given {@see ArtifactEditModel}.
	 * </p>
	 * 
	 * @param anArtifactEditModel
	 */
	public EJBArtifactEdit(ArtifactEditModel model) {
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


	public EJBArtifactEdit(ModuleCoreNature aNature, WorkbenchComponent aModule, boolean toAccessAsReadOnly) {
		super(aNature, aModule, toAccessAsReadOnly);
	}
	
	/**
	 * 
	 * @return EJBResource from (@link getDeploymentDescriptorResource())
	 *  
	 */

	public EJBResource getEJBJarXmiResource() {
		return (EJBResource) getDeploymentDescriptorResource();
	}
	
	/**
	 * <p>
	 * Retrieves J2EE version information from EJBResource.
	 * </p>
	 * 
	 * @return an integer representation of a J2EE Spec version
	 *  
	 */

	public int getJ2EEVersion() {
		return getEJBJarXmiResource().getJ2EEVersionID();
	}
	
	/**
	 * <p>
	 * Checks is a EJB Client Jar exists for the ejb module project
	 * </p>
	 * 
	 * @return boolean
	 *  
	 */
	
	public boolean hasEJBClientJARProject(IProject project) {
		getEJBClientJarModule(project);
		if(module != null)
			return true;
		return false;
	}
	
	/**
	 * <p>
	 * Creates a new EJB module
	 * </p>
	 * 
	 * @return 
	 */
	public Module createNewModule() {
		return ((ApplicationPackage) EPackage.Registry.INSTANCE.getEPackage(ApplicationPackage.eNS_URI)).getApplicationFactory().createEjbModule();
	}

	/**
	 * @param project
	 */
	public void getEJBClientJarModule(IProject project) {
		EJBJar jar = getEJBJar();
		String clientJAR = null;
		if (jar != null)
			clientJAR = jar.getEjbClientJar();
		if (clientJAR != null) {	
			ModuleCore mc = ModuleCore.getModuleCoreForRead(project);
			WorkbenchComponent module = mc.findWorkbenchModuleByDeployName(clientJAR);
		}
	}
	
	
	/**
	 * <p>
	 * Retrieves the underlying resource from the ArtifactEditModel using defined URI.
	 * </p>
	 * 
	 * @return Resource
	 *  
	 */

	public Resource getDeploymentDescriptorResource() {
		return getArtifactEditModel().getResource(J2EEConstants.EJBJAR_DD_URI_OBJ);
	}
	
	/**
	 * 
	 * @return EJBJar from (@link getDeploymentDescriptorRoot())
	 *  
	 */
	public EJBJar getEJBJar() {
		return (EJBJar) getDeploymentDescriptorRoot();
	}
	
	/**
	 * <p>
	 * Obtains the EJBJar (@see EJBJar) root object from the EJBResource. If the root object does
	 * not exist, then one is created (@link addEJBJarIfNecessary(getEJBJarXmiResource())).
	 * The root object contains all other resource defined objects.
	 * </p>
	 * 
	 * @return EObject
	 *  
	 */
	public EObject getDeploymentDescriptorRoot() {
		List contents = getDeploymentDescriptorResource().getContents();
		if (contents.size() > 0)
			return (EObject) contents.get(0);
		addEJBJarIfNecessary(getEJBJarXmiResource());
		return (EObject) contents.get(0);
	}
	
	/**
	 * Returns the deployment descriptor type of the EJB module.
	 * @return int 
	 */
	
	public int getDeploymenyDescriptorType() {
		return XMLResource.EJB_TYPE;
	}
	
	/**
	 * <p>
	 * Creates a deployment descriptor root object (EJBJar) and populates with data. Adds the root
	 * object to the deployment descriptor resource.
	 * </p>
	 * 
	 * <p>
	 * 
	 * @param aModule
	 *            A non-null pointing to a {@see XMLResource}
	 * 
	 * Note: This method is typically used for JUNIT - move?
	 * </p>
	 */
	protected void addEJBJarIfNecessary(XMLResource aResource) {

		if (aResource != null && aResource.getContents().isEmpty()) {
			EJBJar ejbJar = EjbFactory.eINSTANCE.createEJBJar();
			aResource.getContents().add(ejbJar);
		}
		if (aResource != null ) {
			EJBJar ejbJar = (EJBJar)aResource.getContents().get(0);
			URI moduleURI = getArtifactEditModel().getModuleURI();
			try {
				ejbJar.setDisplayName(ModuleCore.getDeployedName(moduleURI));
			} catch (UnresolveableURIException e) {
			}
			aResource.setID(ejbJar, J2EEConstants.EJBJAR_ID);
			//TODO add more mandatory elements
		}
		try {
			aResource.saveIfNecessary();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * <p>
	 * Returns an instance facade to manage the underlying edit model for the given
	 * {@see WorkbenchComponent}. Instances of EJBArtifactEdit that are returned through this method
	 * must be {@see #dispose()}ed of when no longer in use.
	 * </p>
	 * <p>
	 * Use to acquire an EJBArtifactEdit facade for a specific {@see WorkbenchComponent}&nbsp;that will not
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
	 * @return An instance of EJBArtifactEdit that may only be used to read the underlying content
	 *         model
	 * @throws UnresolveableURIException
	 *             could not resolve uri.
	 */
	public static EJBArtifactEdit getEJBArtifactEditForRead(WorkbenchComponent aModule) {
		try {
			if (isValidEJBModule(aModule)) {
				IProject project = ModuleCore.getContainingProject(aModule.getHandle());
				ModuleCoreNature nature = ModuleCoreNature.getModuleCoreNature(project);
				return new EJBArtifactEdit(nature, aModule, true);
			}
		} catch (UnresolveableURIException uue) {
		}
		return null;
	}
	
	/**
	 * <p>
	 * Returns an instance facade to manage the underlying edit model for the given
	 * {@see WorkbenchComponent}. Instances of EJBArtifactEdit that are returned through this method
	 * must be {@see #dispose()}ed of when no longer in use.
	 * </p>
	 * <p>
	 * Use to acquire an EJBArtifactEdit facade for a specific {@see WorkbenchComponent}&nbsp;that
	 * will be used for editing.
	 * </p>
	 * <p>
	 * <b>This method may return null. </b>
	 * </p>
	 * 
	 * @param aModule
	 *            A valid {@see WorkbenchComponent}&nbsp;with a handle that resolves to an accessible
	 *            project in the workspace
	 * @return An instance of EJBArtifactEdit that may be used to modify and persist changes to the
	 *         underlying content model
	 */
	public static EJBArtifactEdit getEJBArtifactEditForWrite(WorkbenchComponent aModule) {
		try {
			if (isValidEJBModule(aModule)) {
				IProject project = ModuleCore.getContainingProject(aModule.getHandle());
				ModuleCoreNature nature = ModuleCoreNature.getModuleCoreNature(project);
				return new EJBArtifactEdit(nature, aModule, false);
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
	public static boolean isValidEJBModule(WorkbenchComponent aModule) throws UnresolveableURIException {
		if (!isValidEditableModule(aModule))
			return false;
		/* and match the JST_EJB_MODULE type */
		if (!TYPE_ID.equals(aModule.getComponentType().getModuleTypeId()))
			return false;
		return true;
	}

	public EObject createModelRoot() {
		if(getEJBJarXmiResource() == null) {
			 addEJBJarIfNecessary(getEJBJarXmiResource());
		}
		return getEJBJarXmiResource().getRootObject();
	}
	
	public void createModelRoot(IProject project, IPath path, int moduleVersion ) {
		
		File file = null;
		file = new File(path.toOSString());
		
		try{
			FileOutputStream out = new FileOutputStream(file);
			PrintStream p = null;
			p = new PrintStream( out );
			
			
			p.println( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" );
			if( moduleVersion == 11 ){
				p.println("<!DOCTYPE ejb-jar PUBLIC \"-//Sun Microsystems, Inc.//DTD Enterprise JavaBeans 1.1//EN\" \"http://java.sun.com/j2ee/dtds/ejb-jar_1_1.dtd\">");
				p.println("<ejb-jar id=\"ejb-jar_ID\">");
				p.println("<display-name>Test");
				p.println("</display-name>");				
				p.println("</ejb-jar>");
				//p.println("<ejb-jar id=\"ejb-jar_ID\"><display-name></display-name><enterprise-beans></enterprise-beans><ejb-client-jar> </ejb-client-jar></ejb-jar>");
				
			}			
			if( moduleVersion == 20 ){
				p.println("<!DOCTYPE ejb-jar PUBLIC \"-//Sun Microsystems, Inc.//DTD Enterprise JavaBeans 2.0//EN\" \"http://java.sun.com/dtd/ejb-jar_2_0.dtd\">");
				p.println("<ejb-jar id=\"ejb-jar_ID\"><display-name></display-name></ejb-jar>");
			}
			if( moduleVersion == 21 ){
				p.println( "<ejb-jar id=\"ejb-jar_ID\" version=\"2.1\" xmlns=\"http://java.sun.com/xml/ns/j2ee\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://java.sun.com/xml/ns/j2ee http://java.sun.com/xml/ns/j2ee/ejb-jar_2_1.xsd\"></ejb-jar>");
			}	

			p.close();					

		}catch(IOException e){
			e.printStackTrace();
		}

		try{
			project.refreshLocal(IResource.DEPTH_INFINITE, null);
		}catch(CoreException e){
			e.printStackTrace();
		}

		getDeploymentDescriptorRoot();
	}
			
	public EObject createModelRoot(int version) {
		// TODO Auto-generated method stub
		return null;
	}
}
