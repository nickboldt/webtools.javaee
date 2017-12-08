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
package org.eclipse.jst.j2ee.internal.ejb.provider;



import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.jst.j2ee.common.CommonFactory;
import org.eclipse.jst.j2ee.ejb.EJBRelation;
import org.eclipse.jst.j2ee.ejb.EjbFactory;
import org.eclipse.jst.j2ee.ejb.EjbPackage;
import org.eclipse.jst.j2ee.ejb.Relationships;
import org.eclipse.jst.j2ee.internal.plugin.J2EEPlugin;
import org.eclipse.jst.j2ee.webservice.wscommon.WscommonFactory;


/**
 * This is the item provider adpater for a {@link org.eclipse.jst.j2ee.internal.internal.ejb.Relationships}object.
 */
public class RelationshipsItemProvider extends EjbItemProviderAdapter {
	/**
	 * This constructs an instance from a factory and a notifier.
	 */
	public RelationshipsItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This creates a new child for {@link org.eclipse.jst.j2ee.internal.internal.ejb.command.CreateChildCommand}.
	 */
	@Override
	public Object createChild(Object object) {
		// TODO: check that this is what you want.
		EJBRelation child = EjbFactory.eINSTANCE.createEJBRelation();

		// TODO: initialize child here...

		return child;
	}

	/**
	 * This specifies how to implement {@link #getChildren}and {@link AddCommand}and
	 * {@link RemoveCommand}support in {@link #createCommand}.
	 */
	@Override
	public Collection getChildrenReferences(Object object) {
		if (childrenReferences == null) {
			super.getChildrenReferences(object);
			childrenReferences.add(EjbPackage.eINSTANCE.getRelationships_EjbRelations());
		}
		return childrenReferences;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EReference getChildReference(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildReference(object, child);
	}


	/**
	 * This returns the image for {@link org.eclipse.jst.j2ee.internal.internal.ejb.command.CreateChildCommand}.
	 */
	@Override
	public Object getCreateChildImage(Object object) {
		EObject refObject = (EObject) object;
		return J2EEPlugin.getPlugin().getImage(refObject.eClass().getName() + "CreateEJBRelation"); //$NON-NLS-1$
	}

	/**
	 * This returns the label for {@link org.eclipse.jst.j2ee.internal.internal.ejb.command.CreateChildCommand}.
	 */
	@Override
	public String getCreateChildText(Object object) {
		return EJBProviderLibrariesResourceHandler.getString("Create_EJBRelation_2"); //$NON-NLS-1$
	}

	/**
	 * This returns the help text for {@link org.eclipse.jst.j2ee.internal.internal.ejb.command.CreateChildCommand}.
	 */
	@Override
	public String getCreateChildToolTipText(Object object) {
		EObject refObject = (EObject) object;
		return EJBProviderLibrariesResourceHandler.getString("Create_a_child_of_type_EJBRelation_for_the_selected__3") + refObject.eClass().getName() + EJBProviderLibrariesResourceHandler.getString("_4"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * This returns Relationships.gif.
	 */
	@Override
	public Object getImage(Object object) {
		return J2EEPlugin.getPlugin().getImage("Relationships"); //$NON-NLS-1$
	}

	/**
	 * This returns the parent of the Relationships.
	 */
	@Override
	public Object getParent(Object object) {
		return ((EObject) object).eContainer();
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 */
	@Override
	public List getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			EjbPackage pkg = EjbPackage.eINSTANCE;

			// This is for the description feature.
			//
			itemPropertyDescriptors.add(new ItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), EJBProviderLibrariesResourceHandler.getString("Description_6"), //$NON-NLS-1$
						EJBProviderLibrariesResourceHandler.getString("The_description_property_of_the_relationships_7"), //$NON-NLS-1$
						pkg.getRelationships_Description(), true, ItemPropertyDescriptor.TEXT_VALUE_IMAGE));

			// This is for the ejbJar feature.
			//
			itemPropertyDescriptors.add(new ItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), EJBProviderLibrariesResourceHandler.getString("Ejb_Jar_8"), //$NON-NLS-1$
						EJBProviderLibrariesResourceHandler.getString("The_ejb_jar_of_the_relationships_9"), //$NON-NLS-1$
						pkg.getRelationships_EjbJar(), false));

		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Description feature. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addDescriptionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(new ItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getString("_UI_Relationships_description_feature"), //$NON-NLS-1$
					getString("_UI_PropertyDescriptor_description", "_UI_Relationships_description_feature", "_UI_Relationships_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					EjbPackage.eINSTANCE.getRelationships_Description(), true, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE));
	}

	@Override
	public String getText(Object object) {
		Relationships relationships = ((Relationships) object);
		return EJBProviderLibrariesResourceHandler.getString("Relationships__10") + relationships.getDescription(); //$NON-NLS-1$
	}

	/**
	 * This handles notification by calling {@link #fireNotifyChanged fireNotifyChanged}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		switch (notification.getFeatureID(Relationships.class)) {
			case EjbPackage.RELATIONSHIPS__DESCRIPTION :
			case EjbPackage.RELATIONSHIPS__EJB_RELATIONS :
			case EjbPackage.RELATIONSHIPS__DESCRIPTIONS : {
				fireNotifyChanged(notification);
				return;
			}
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds to the collection of {@link org.eclipse.emf.edit.command.CommandParameter}s
	 * describing all of the children that can be created under this object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add(createChildParameter(EjbPackage.eINSTANCE.getRelationships_EjbRelations(), EjbFactory.eINSTANCE.createEJBRelation()));

		newChildDescriptors.add(createChildParameter(EjbPackage.eINSTANCE.getRelationships_Descriptions(), CommonFactory.eINSTANCE.createDescription()));

		newChildDescriptors.add(createChildParameter(EjbPackage.eINSTANCE.getRelationships_Descriptions(), WscommonFactory.eINSTANCE.createDescriptionType()));
	}

	/**
	 * Return the resource locator for this item provider's resources. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return J2EEPlugin.getDefault();
	}
}
