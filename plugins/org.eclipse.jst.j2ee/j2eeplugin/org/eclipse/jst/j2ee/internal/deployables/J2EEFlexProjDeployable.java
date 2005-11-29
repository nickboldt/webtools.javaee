/*******************************************************************************
 * Copyright (c) 2003, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.j2ee.internal.deployables;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jem.workbench.utility.JemProjectUtilities;
import org.eclipse.jst.j2ee.componentcore.util.EARArtifactEdit;
import org.eclipse.jst.j2ee.ejb.EJBJar;
import org.eclipse.jst.j2ee.internal.EjbModuleExtensionHelper;
import org.eclipse.jst.j2ee.internal.IEJBModelExtenderManager;
import org.eclipse.jst.j2ee.internal.J2EEConstants;
import org.eclipse.jst.j2ee.internal.plugin.IJ2EEModuleConstants;
import org.eclipse.jst.j2ee.internal.project.J2EEProjectUtilities;
import org.eclipse.jst.server.core.IApplicationClientModule;
import org.eclipse.jst.server.core.IConnectorModule;
import org.eclipse.jst.server.core.IEJBModule;
import org.eclipse.jst.server.core.IEnterpriseApplication;
import org.eclipse.jst.server.core.IJ2EEModule;
import org.eclipse.jst.server.core.IWebModule;
import org.eclipse.wst.common.componentcore.ArtifactEdit;
import org.eclipse.wst.common.componentcore.ComponentCore;
import org.eclipse.wst.common.componentcore.internal.util.ComponentUtilities;
import org.eclipse.wst.common.componentcore.resources.IVirtualComponent;
import org.eclipse.wst.common.componentcore.resources.IVirtualFolder;
import org.eclipse.wst.common.componentcore.resources.IVirtualReference;
import org.eclipse.wst.server.core.IModule;
import org.eclipse.wst.server.core.internal.ModuleFile;
import org.eclipse.wst.server.core.internal.ModuleFolder;
import org.eclipse.wst.server.core.model.IModuleFolder;
import org.eclipse.wst.server.core.model.IModuleResource;
import org.eclipse.wst.server.core.util.ProjectModule;
/**
 * J2EE module superclass.
 */
public class J2EEFlexProjDeployable extends ProjectModule implements IJ2EEModule, IEnterpriseApplication, IApplicationClientModule, IConnectorModule, IEJBModule, IWebModule {
	private static final IPath WEB_CLASSES_PATH = new Path("WEB-INF").append("classes"); //$NON-NLS-1$ //$NON-NLS-2$
    protected IVirtualComponent component = null;
    private List members = new ArrayList();

	/**
	 * Constructor for J2EEFlexProjDeployable.
	 * 
	 * @param project
	 * @param aComponent
	 */
	public J2EEFlexProjDeployable(IProject project, IVirtualComponent aComponent) {
		super(project);
		component = aComponent;
	}

	/**
	 * @see org.eclipse.jst.server.core.IJ2EEModule#isBinary()
	 */
	public boolean isBinary() {
		return false;
	}

	/**
	 * Returns the root folders for the resources in this module.
	 * 
	 * @return a possibly-empty array of resource folders
	 */
	public IContainer[] getResourceFolders() {
		IVirtualComponent vc = ComponentCore.createComponent(getProject());
		if (vc != null) {
			IVirtualFolder vFolder = vc.getRootFolder();
			if (vFolder != null)
				return vFolder.getUnderlyingFolders();
		}
		
		return null;
	}

	/**
	 * Returns the root folders containing Java output in this module.
	 * 
	 * @return a possibly-empty array of Java output folders
	 */
	public IContainer[] getJavaOutputFolders() {
		IVirtualComponent vc = ComponentCore.createComponent(getProject());
		if (vc == null)
			return new IContainer[0];
		
		return J2EEProjectUtilities.getOutputContainers(getProject());
	}

	public IModuleResource[] members() throws CoreException {
		members.clear();
		IPath javaPath = Path.EMPTY;
		if (J2EEProjectUtilities.isDynamicWebProject(component.getProject()))
			javaPath = WEB_CLASSES_PATH;
		
		IContainer[] cont = getResourceFolders();
		IContainer[] javaCont = getJavaOutputFolders();
		
		if (javaPath.isEmpty()) {
			IContainer[] cont2 = new IContainer[cont.length + javaCont.length];
			
			System.arraycopy(cont, 0, cont2, 0, cont.length);
			System.arraycopy(javaCont, 0, cont2, cont.length, javaCont.length);
			cont = cont2;
			javaCont = null;
			javaPath = null;
		}
		
		int size = cont.length;
		for (int i = 0; i < size; i++) {
			IModuleResource[] mr = getMembers(cont[i], Path.EMPTY, javaPath, javaCont);
			int size2 = mr.length;
			for (int j = 0; j < size2; j++) {
				if (!members.contains(mr[j]))
					members.add(mr[j]);
			}
		}
		
		IModuleResource[] mr = new IModuleResource[members.size()];
		members.toArray(mr);
		return mr;
	}
	
	private IModuleResource getExistingModuleResource(List aList, IPath path) {
    	IModuleResource result = null;
    	// If the list is empty, return null
    	if (aList==null || aList.isEmpty())
    		return null;
    	// Otherwise recursively check to see if given resource matches current resource or if it is a child
    	int i=0;
    	do {
	    	IModuleResource moduleResource = (IModuleResource) aList.get(i);
	    		if (moduleResource.getModuleRelativePath().append(moduleResource.getName()).equals(path))
	    			result = moduleResource;
	    		// if it is a folder, check its children for the resource path
	    		else if (moduleResource instanceof IModuleFolder) {
	    			result = getExistingModuleResource(Arrays.asList(((IModuleFolder)moduleResource).members()),path);
	    		}
	    		i++;
    	} while (result == null && i<aList.size() );
    	return result;
    }

	/**
	 * Find the module resources for a given container and path. Inserts in the java containers
	 * at a given path if not null.
	 * 
	 * @param cont a container
	 * @param path the current module relative path
	 * @param javaPath the path where Java resources fit in the root
	 * @param javaCont
	 * @return a possibly-empty array of module resources
	 * @throws CoreException
	 */
	protected IModuleResource[] getMembers(IContainer cont, IPath path, IPath javaPath, IContainer[] javaCont) throws CoreException {
		IResource[] res = cont.members();
		int size2 = res.length;
		List list = new ArrayList(size2);
		for (int j = 0; j < size2; j++) {
			if (res[j] instanceof IContainer) {
				IContainer cc = (IContainer) res[j];
				// Retrieve already existing module folder if applicable
				ModuleFolder mf = (ModuleFolder) getExistingModuleResource(members,new Path(cc.getName()));
				if (mf == null)
					mf = new ModuleFolder(cc, cc.getName(), path);
				IModuleResource[] mr = getMembers(cc, path.append(cc.getName()), javaPath, javaCont);
				IPath curPath = path.append(cc.getName());
				
				if (javaPath != null && curPath.isPrefixOf(javaPath))
					mr = handleJavaPath(path, javaPath, curPath, javaCont, mr, cc);

				addMembersToModuleFolder(mf, mr);
				list.add(mf);
			} else {
				IFile f = (IFile) res[j];
				//TODO this needs to be cleaned up when virtual API points to output folders instead of source folders.
				if (!f.getName().endsWith(".java")) { //$NON-NLS-1$
					ModuleFile mf = new ModuleFile(f, f.getName(), path, f.getModificationStamp());
					list.add(mf);
				}
			}
		}
		IModuleResource[] mr = new IModuleResource[list.size()];
		list.toArray(mr);
		return mr;
	}
	
	private IModuleResource[] handleJavaPath(IPath path, IPath javaPath, IPath curPath, IContainer[] javaCont, IModuleResource[] mr, IContainer cc) throws CoreException {
		if (curPath.equals(javaPath)) {
			int size = javaCont.length;
			for (int i = 0; i < size; i++) {
				IModuleResource[] mr2 = getMembers(javaCont[i], path.append(cc.getName()), null, null);
				IModuleResource[] mr3 = new IModuleResource[mr.length + mr2.length];
				System.arraycopy(mr, 0, mr3, 0, mr.length);
				System.arraycopy(mr2, 0, mr3, mr.length, mr2.length);
				mr = mr3;
			}
		} else {
			boolean containsFolder = false;
			String name = javaPath.segment(curPath.segmentCount());
			int size = mr.length;
			for (int i = 0; i < size && !containsFolder; i++) {
				if (mr[i] instanceof IModuleFolder) {
					IModuleFolder mf2 = (IModuleFolder) mr[i];
					if (name.equals(mf2.getName())) {
						containsFolder = true;
					}
				}
			}
			
			if (!containsFolder) {
				ModuleFolder mf2 = new ModuleFolder(null, name, curPath);
				IModuleResource[] mrf = new IModuleResource[0];
				size = javaCont.length;
				for (int i = 0; i < size; i++) {
					IModuleResource[] mrf2 = getMembers(javaCont[i], javaPath, null, null);
					IModuleResource[] mrf3 = new IModuleResource[mrf.length + mrf2.length];
					System.arraycopy(mr, 0, mrf3, 0, mrf.length);
					System.arraycopy(mrf2, 0, mrf3, mrf.length, mrf2.length);
					mrf = mrf3;
				}
				
				mf2.setMembers(mrf);
				
				IModuleResource[] mr3 = new IModuleResource[mr.length + 1];
				System.arraycopy(mr, 0, mr3, 0, mr.length);
				mr3[mr.length] = mf2;
				mr = mr3;
			}
		}
		return mr;
	}
	
	private void addMembersToModuleFolder(ModuleFolder mf, IModuleResource[] mr) {
		IModuleResource[] existingMembers = mf.members();
		if (existingMembers==null)
			existingMembers = new IModuleResource[0];
		List membersJoin = new ArrayList();
		membersJoin.addAll(Arrays.asList(existingMembers));
		List newMembers = Arrays.asList(mr);
		for (int i=0; i<newMembers.size(); i++) {
			boolean found = false;
			IModuleResource newMember = (IModuleResource) newMembers.get(i);
			for (int k=0; k<membersJoin.size(); k++) {
				IModuleResource existingResource = (IModuleResource) membersJoin.get(k);
				if (existingResource.equals(newMember)) {
					found = true;
					break;
				}
			}
			if (!found)
				membersJoin.add(newMember);
		}
		mf.setMembers((IModuleResource[]) membersJoin.toArray(new IModuleResource[membersJoin.size()]));
	}

    /**
     * Returns the classpath as a list of absolute IPaths.
     * 
     * @return an array of paths
     */
    public IPath[] getClasspath() {
		List paths = new ArrayList();
        IJavaProject proj = JemProjectUtilities.getJavaProject(getProject());
        URL[] urls = JemProjectUtilities.getClasspathAsURLArray(proj);
		for (int i = 0; i < urls.length; i++) {
			URL url = urls[i];
			paths.add(Path.fromOSString(url.getPath()));
		}
        return  (IPath[]) paths.toArray(new IPath[paths.size()]);
    }
    
    public String getJNDIName(String ejbName) {
    	if (!J2EEProjectUtilities.isEJBProject(component.getProject()))
    		return null;
		EjbModuleExtensionHelper modHelper = null;
		EJBJar jar = null;
		ArtifactEdit ejbEdit = null;
		try {
			ejbEdit = ComponentUtilities.getArtifactEditForRead(component);
			if (ejbEdit != null) {
				jar = (EJBJar) ejbEdit.getContentModelRoot();
				modHelper = IEJBModelExtenderManager.INSTANCE.getEJBModuleExtension(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ejbEdit != null)
				ejbEdit.dispose();
		}
		return modHelper == null ? null : modHelper.getJNDIName(jar, jar.getEnterpriseBeanNamed(ejbName));
	}

    /**
     * Returns the child modules of this module.
     * 
     * @return org.eclipse.wst.server.core.model.IModule[]
     */
    public IModule[] getChildModules() {
        return getModules();
    }

    public IModule[] getModules() {
		List modules = new ArrayList();
    	IVirtualReference[] components = component.getReferences();
    	for (int i = 0; i < components.length; i++) {
			IVirtualReference reference = components[i];
			IVirtualComponent virtualComp = reference.getReferencedComponent();
			if (virtualComp.getProject()!=component.getProject()) {
				Object module = FlexibleProjectServerUtil.getModule(virtualComp.getProject());
				if (module != null && !modules.contains(module))
					modules.add(module);
			}
		}
      return (IModule[]) modules.toArray(new IModule[modules.size()]);
	}

    public String getURI(IModule module) {
    	IVirtualComponent comp = ComponentCore.createComponent(module.getProject());
    	String aURI = null;
    	if (J2EEProjectUtilities.isEARProject(comp.getProject())) {
			EARArtifactEdit earEdit = null;
			try {
				earEdit = EARArtifactEdit.getEARArtifactEditForRead(component);
				if (earEdit != null)
					aURI = earEdit.getModuleURI(comp);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (earEdit != null)
					earEdit.dispose();
			}
    	}
    	else if (J2EEProjectUtilities.isDynamicWebProject(comp.getProject())) {
    		if (!comp.isBinary()) {
        		IVirtualReference ref = component.getReference(comp.getName());
        		aURI = ref.getRuntimePath().append(comp.getName()+IJ2EEModuleConstants.WAR_EXT).toString();
        	}
    	} else if (J2EEProjectUtilities.isEJBProject(comp.getProject()) || J2EEProjectUtilities.isApplicationClientProject(comp.getProject())) {
    		if (!comp.isBinary()) {
        		IVirtualReference ref = component.getReference(comp.getName());
        		aURI = ref.getRuntimePath().append(comp.getName()+IJ2EEModuleConstants.JAR_EXT).toString();
        	}
    	} else if (J2EEProjectUtilities.isJCAProject(comp.getProject())) {
    		if (!comp.isBinary()) {
        		IVirtualReference ref = component.getReference(comp.getName());
        		aURI = ref.getRuntimePath().append(comp.getName()+IJ2EEModuleConstants.RAR_EXT).toString();
        	}
    	}
    	
    	if (aURI !=null && aURI.length()>1 && aURI.startsWith("/")) //$NON-NLS-1$
    		aURI = aURI.substring(1);
    	return aURI;
	}
    
    public String getContextRoot() {
		Properties props = component.getMetaProperties();
		if(props.containsKey(J2EEConstants.CONTEXTROOT))
			return props.getProperty(J2EEConstants.CONTEXTROOT);
	    return component.getName();
    }
}