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
package org.eclipse.jst.j2ee.common.internal.provider;



import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.jst.j2ee.common.CommonFactory;
import org.eclipse.jst.j2ee.common.CommonPackage;
import org.eclipse.jst.j2ee.common.EnvEntry;
import org.eclipse.jst.j2ee.internal.common.CommonEditResourceHandler;
import org.eclipse.jst.j2ee.internal.plugin.J2EEPlugin;
import org.eclipse.jst.j2ee.webservice.wscommon.WscommonFactory;



/**
 * This is the item provider adpater for a {@link com.ibm.etools.common.EnvEntry}object.
 */
public class EnvEntryItemProvider extends CommonItemProviderAdapter implements IEditingDomainItemProvider, IItemLabelProvider, IItemPropertySource, ITreeItemContentProvider {

	/**
	 * This constructs an instance from a factory and a notifier.
	 */
	public EnvEntryItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns environment_entity.gif.
	 */
	@Override
	public Object getImage(Object object) {
		return J2EEPlugin.getPlugin().getImage("environment_entity");//$NON-NLS-1$
	}

	/**
	 * This returns the parent of the EnvEntry.
	 */
	@Override
	public Object getParent(Object object) {

		return object == null ? null : ((EnvEntry) object).eContainer();
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 */
	@Override
	public List getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			CommonPackage pkg = CommonPackage.eINSTANCE;

			// This is for the description feature.
			//
			itemPropertyDescriptors.add(new ItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), CommonEditResourceHandler.getString("Description_UI_"), //$NON-NLS-1$
						CommonEditResourceHandler.getString("The_description_property_UI_"), //$NON-NLS-1$
						pkg.getEnvEntry_Description()));

			// This is for the name feature.
			//
			itemPropertyDescriptors.add(new ItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), CommonEditResourceHandler.getString("Name_UI_"), //$NON-NLS-1$
						CommonEditResourceHandler.getString("The_name_property_UI_"), //$NON-NLS-1$
						pkg.getEnvEntry_Name()));

			// This is for the value feature.
			//
			itemPropertyDescriptors.add(new ItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), CommonEditResourceHandler.getString("Value_UI_"), //$NON-NLS-1$
						CommonEditResourceHandler.getString("The_value_property_UI_"), //$NON-NLS-1$
						pkg.getEnvEntry_Value()));

			// This is for the type feature.
			//
			itemPropertyDescriptors.add(new ItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), CommonEditResourceHandler.getString("Type_UI_"), //$NON-NLS-1$
						CommonEditResourceHandler.getString("The_type_property_UI_"), //$NON-NLS-1$
						pkg.getEnvEntry_Type()));

			// This is for the webApp feature.
			//
			/*
			 * itemPropertyDescriptors.add (new ItemPropertyDescriptor
			 * (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
			 * ResourceHandler.getString("WebApp_UI_"), //$NON-NLS-1$
			 * ResourceHandler.getString("The_webApp_property_UI_"), //$NON-NLS-1$
			 * pkg.getEnvEntry_WebApp()));
			 */

			// This is for the client feature.
			//
			/*
			 * itemPropertyDescriptors.add (new ItemPropertyDescriptor
			 * (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
			 * ResourceHandler.getString("Client_UI_"), //$NON-NLS-1$
			 * ResourceHandler.getString("The_client_property_UI_"), //$NON-NLS-1$
			 * pkg.getEnvEntry_Client()));
			 */

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
		itemPropertyDescriptors.add(new ItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getString("_UI_EnvEntry_description_feature"), //$NON-NLS-1$
					getString("_UI_PropertyDescriptor_description", "_UI_EnvEntry_description_feature", "_UI_EnvEntry_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					CommonPackage.eINSTANCE.getEnvEntry_Description(), true, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE));
	}

	/**
	 * This adds a property descriptor for the Name feature. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(new ItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getString("_UI_EnvEntry_name_feature"), //$NON-NLS-1$
					getString("_UI_PropertyDescriptor_description", "_UI_EnvEntry_name_feature", "_UI_EnvEntry_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					CommonPackage.eINSTANCE.getEnvEntry_Name(), true, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE));
	}

	/**
	 * This adds a property descriptor for the Value feature. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addValuePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(new ItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getString("_UI_EnvEntry_value_feature"), //$NON-NLS-1$
					getString("_UI_PropertyDescriptor_description", "_UI_EnvEntry_value_feature", "_UI_EnvEntry_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					CommonPackage.eINSTANCE.getEnvEntry_Value(), true, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE));
	}

	/**
	 * This adds a property descriptor for the Type feature. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(new ItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getString("_UI_EnvEntry_type_feature"), //$NON-NLS-1$
					getString("_UI_PropertyDescriptor_description", "_UI_EnvEntry_type_feature", "_UI_EnvEntry_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					CommonPackage.eINSTANCE.getEnvEntry_Type(), true, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE));
	}

	/**
	 * This specifies how to implement {@link #getChildren}and
	 * {@link org.eclipse.emf.edit.command.AddCommand}and
	 * {@link org.eclipse.emf.edit.command.RemoveCommand}support in {@link #createCommand}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Collection getChildrenReferences(Object object) {
		if (childrenReferences == null) {
			super.getChildrenReferences(object);
			childrenReferences.add(CommonPackage.eINSTANCE.getEnvEntry_Descriptions());
		}
		return childrenReferences;
	}


	@Override
	public String getText(Object object) {
		return "EnvEntry " + ((EnvEntry) object).getName();//$NON-NLS-1$
	}

	/**
	 * This handles notification by calling {@link #fireNotifyChanged fireNotifyChanged}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		switch (notification.getFeatureID(EnvEntry.class)) {
			case CommonPackage.ENV_ENTRY__DESCRIPTION :
			case CommonPackage.ENV_ENTRY__NAME :
			case CommonPackage.ENV_ENTRY__VALUE :
			case CommonPackage.ENV_ENTRY__TYPE :
			case CommonPackage.ENV_ENTRY__DESCRIPTIONS : {
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

		newChildDescriptors.add(createChildParameter(CommonPackage.eINSTANCE.getEnvEntry_Descriptions(), CommonFactory.eINSTANCE.createDescription()));

		newChildDescriptors.add(createChildParameter(CommonPackage.eINSTANCE.getEnvEntry_Descriptions(), WscommonFactory.eINSTANCE.createDescriptionType()));
	}

	/**
	 * Return the resource locator for this item provider's resources. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *  
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return J2EEPlugin.getDefault();
	}

	/**
	 * This always returns false. The base class has already implemented
	 * {@link ItemProviderAdapter#getChildren ItemProviderAdapter.getChildren}to return the empty
	 * enumeration, and this to check that enumeration, but we know there can't be any children, so
	 * this is faster.
	 */
	@Override
	public boolean hasChildren(Object object) {
		return false;
	}
}
