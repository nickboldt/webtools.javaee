/*******************************************************************************
 * Copyright (c) 2003, 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Oracle - EJB Timer
 *******************************************************************************/
package org.eclipse.jst.ejb.ui.internal.util;

import org.eclipse.osgi.util.NLS;


public class EJBUIMessages extends NLS {
	
	private static final String BUNDLE_NAME = "ejb_ui";//$NON-NLS-1$

	private EJBUIMessages() {
		// Do not instantiate
	}

	public static String KEY_0;
	public static String KEY_1;
	public static String KEY_2;
	public static String KEY_3;
	public static String KEY_4;
	public static String KEY_5;
	public static String KEY_6;
	public static String KEY_7;
	public static String KEY_8;
	public static String KEY_9;
	public static String KEY_10;
	public static String KEY_11;
	public static String KEY_12;
	public static String KEY_13;
	public static String KEY_14;
	public static String KEY_15;
	public static String KEY_16;
	public static String KEY_17;
	public static String KEY_18;
	public static String KEY_19;
	public static String KEY_20;
	public static String KEY_21;
	public static String KEY_22;
	public static String KEY_23;
	public static String KEY_24;
	public static String KEY_25;
	public static String KEY_26;
	public static String KEY_27;
	public static String KEY_28;
	public static String GroupedEJBJarItemProvider_UI_0;
	public static String GroupedEJBJarItemProvider_UI_1;
	public static String GroupedEJBJarItemProvider_UI_2;
	public static String NewEJBModuleAction_UI_0;
	public static String pageTitle;
	public static String pageDescription;
	public static String configFolderLabel;
	
	//common
	public static String NEW_JAVA_CLASS_DESTINATION_WIZARD_PAGE_DESC;
	public static String NEW_JAVA_CLASS_OPTIONS_WIZARD_PAGE_DESC;
	
	//EnterpriseBeans
	public static String ADD_SESSION_BEAN_WIZARD_PAGE_TITLE;
	public static String STATE_TYPE_LABEL;
	public static String CREATE_BUSINESS_INTERFACE;
	public static String REMOTE_BUSINESS_INTERFACE;
	public static String REMOTE_BUSINESS_INTERFACE_E;
	public static String REMOTE_BUSINESS_INTERFACE_LABEL;
	public static String REMOTE_HOME_INTERFACE_LABEL;
	public static String LOCAL_BUSINESS_INTERFACE;
	public static String LOCAL_BUSINESS_INTERFACE_LABEL;
	public static String LOCAL_HOME_INTERFACE_LABEL;
	public static String NO_INTERFACE;
	public static String EJB_NAME;
	public static String TRANSACTION_TYPE_CONTAINER;
	public static String TRANSACTION_TYPE_BEAN;
	public static String STATE_TYPE_STATELESS;
	public static String STATE_TYPE_STATEFUL;
	public static String chooseInterface;
	public static String addAs;
	public static String HOMECOMPONENTINTERFACE;
	public static String MAPPED_NAME;
	public static String TRANSACTION_TYPE;
	public static String BUSSINESS_INTERFACE;
	public static String ADD_INTERFACES;
	public static String REMOVE_INTERFACES;
	public static String ADD_SESSION_BEAN_WIZARD_PAGE_DESC;
	public static String ADD_MESSAGE_DRIVEN_BEAN_WIZARD_PAGE_TITLE;
	public static String DESTINATION_LABEL;
	public static String DESTINATION_TYPE_QUEUE;
	public static String DESTINATION_TYPE_TOPIC;
	public static String JMS;
	public static String DESTINATION_NAME_LABEL;
	public static String ADD_MESSAGE_DRIVEN_BEAN_WIZARD_PAGE_DESC;
	public static String MESSAGE_LISTENER_INTERFACE_HYPERLINK;
	public static String MESSAGE_LISTENER_INTERFACE_HYPERLINK_TOOLTIP;
	public static String CLICK_TO_SELECT;
	public static String LOCAL_COMPONENT_INTERFACE_CODE;
	public static String LOCAL_HOME_INTERFACE_CODE;
	public static String REMOTE_COMPONENT_INTERFACE_CODE;
	public static String REMOTE_HOME_INTERFACE_CODE;
	public static String LOCAL_COMPONENT_INTERFACE_TOOLTIP;
	public static String LOCAL_HOME_INTERFACE_TOOLTIP;
	public static String REMOTE_COMPONENT_INTERFACE_TOOLTIP;
	public static String REMOTE_HOME_INTERFACE_TOOLTIP;
	public static String EJB_CLIENT_JAR_GROUP;
	public static String ASYNC;

	//EJB Timer
    public static String timerWizardTitle;
    public static String timerWizardDescription;
    public static String timerScheduleLabel;
    public static String NON_PERSISTENT;

	static {
		NLS.initializeMessages(BUNDLE_NAME, EJBUIMessages.class);
	}
	
	public static final String IMAGE_LOAD_ERROR = KEY_0;
	public static final String EJB_PROJECT_WIZ_TITLE = KEY_1;
	public static final String EJB_VERSION_LBL = KEY_2;
	public static final String EJB_PROJECT_MAIN_PG_TITLE = KEY_3;
	public static final String EJB_PROJECT_MAIN_PG_DESC = KEY_4;
	public static final String EJB_IMPORT_MAIN_PG_DESC = KEY_5;
	public static final String EJB_IMPORT_MAIN_PG_TITLE = KEY_6;
	public static final String EJB_JAR_FILE_LBL = KEY_7;
	public static final String EJB_PROJECT_LBL = KEY_8;
	public static final String EJB_EXPORT_MAIN_PG_TITLE = KEY_9;
	public static final String EJB_EXPORT_MAIN_PG_DESC = KEY_10;
	public static final String EJB_Client_Title = KEY_11;
	public static final String EJB_Client_Desc = KEY_12;
	public static final String Client_JAR_URI = KEY_13;
	public static final String Project_name = KEY_14;
	public static final String Project_location = KEY_15;
	public static final String EJB_Project = KEY_16;
	public static final String DELETE_BEAN_ONLY = KEY_17;
	public static final String Delete_Bean_Classes_UI = KEY_18;
	public static final String Delete_Access_Bean = KEY_19;
	public static final String Delete_Deployed_Code = KEY_20;
	public static final String Select_All_UI = KEY_21;
	public static final String Deselect_All_UI = KEY_22;
	public static final String The_selected_Enterprise_Be = KEY_23;
	public static final String Delete = KEY_24;
	public static final String Cannot_delete_Enterprise_B = KEY_25;
	public static final String Cannot_delete_bean = KEY_26;
	public static final String Delete_Enterprise_Beans = KEY_27;
	public static final String Delete_Options_Query = KEY_27;
	public static final String NewEJBModuleAction = NewEJBModuleAction_UI_0;
}
