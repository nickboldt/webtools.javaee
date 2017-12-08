/***************************************************************************************************
 * Copyright (c) 2005 Eteration A.S. and others. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Eteration A.S. - initial API and implementation
 **************************************************************************************************/

package org.eclipse.jst.j2ee.ejb.annotation.internal.model;

import org.eclipse.jst.j2ee.ejb.DestinationType;
import org.eclipse.jst.j2ee.ejb.EjbFactory;
import org.eclipse.jst.j2ee.ejb.MessageDriven;
import org.eclipse.jst.j2ee.ejb.MessageDrivenDestination;
import org.eclipse.jst.j2ee.ejb.TransactionType;
import org.eclipse.wst.common.frameworks.datamodel.DataModelEvent;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public class MessageDrivenBeanDelegate extends EnterpriseBeanDelegate implements IMessageDrivenBean {

	public MessageDrivenBeanDelegate() {
		super();
		MessageDriven mdBean = EjbFactory.eINSTANCE.createMessageDriven();
		this.setEnterpriseBean(mdBean);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jst.j2ee.ejb.annotation.internal.model.IEnterpriseBean#getDataModel()
	 */
	public void setDataModel(IDataModel dataModel) {
		super.setDataModel(dataModel);
		MessageDriven messageDriven = (MessageDriven) this.getEnterpriseBean();
		if (messageDriven == null)
			return;

		DestinationType dType = DestinationType.QUEUE_LITERAL;
		if (dataModel.getStringProperty(IMessageDrivenBeanDataModelProperties.DESTINATIONTYPE).equals(
				DestinationType.TOPIC_LITERAL.getName()))
			dType = DestinationType.TOPIC_LITERAL;
		MessageDrivenDestination destination = EjbFactory.eINSTANCE.createMessageDrivenDestination();
		destination.setType(dType);
		destination.setBean(messageDriven);
		messageDriven.setDestination(destination);
		String dName = dataModel.getStringProperty(IMessageDrivenBeanDataModelProperties.DESTINATIONNAME);
		if (dName == null || dName.length() <= 0)
			dName = dataModel.getStringProperty(IEnterpriseBeanClassDataModelProperties.JNDI_NAME);
		messageDriven.setMessageSelector(dName);

		TransactionType transactionType = TransactionType.CONTAINER_LITERAL;
		if (dataModel.getStringProperty(IMessageDrivenBeanDataModelProperties.TRANSACTIONTYPE).equals(
				TransactionType.BEAN_LITERAL.getName()))
			transactionType = TransactionType.BEAN_LITERAL;
		messageDriven.setTransactionType(transactionType);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jst.j2ee.ejb.annotations.internal.emitter.model.IMessageDrivenBeanDelegate#getDestinationType()
	 */
	public String getDestinationType() {
		MessageDriven messageDriven = (MessageDriven) this.getEnterpriseBean();
		return messageDriven.getDestination().getType().getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jst.j2ee.ejb.annotations.internal.emitter.model.IMessageDrivenBeanDelegate#getDestinationType()
	 */
	public String getTransactionType() {
		MessageDriven messageDriven = (MessageDriven) this.getEnterpriseBean();
		return messageDriven.getTransactionType().getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jst.j2ee.ejb.annotations.internal.emitter.model.IMessageDrivenBeanDelegate#getDestinationJndiName()
	 */
	public String getDestinationJndiName() {
		MessageDriven messageDriven = (MessageDriven) this.getEnterpriseBean();
		return messageDriven.getMessageSelector();
	}

	/**
	 * This method permits us to keep emf model for the bean in sync with the
	 * changes in the datamodel
	 */
	public void propertyChanged(DataModelEvent event) {
		super.propertyChanged(event);
		String property = event.getPropertyName();
		Object propertyValue = event.getProperty();
		MessageDriven messageDriven = (MessageDriven) this.getEnterpriseBean();
		if (messageDriven == null)
			return;

		if (IMessageDrivenBeanDataModelProperties.DESTINATIONTYPE.equals(property)) {
			DestinationType dType = DestinationType.QUEUE_LITERAL;
			if (propertyValue.equals(DestinationType.TOPIC_LITERAL.getName()))
				dType = DestinationType.TOPIC_LITERAL;
			MessageDrivenDestination destination = EjbFactory.eINSTANCE.createMessageDrivenDestination();
			destination.setType(dType);
			destination.setBean(messageDriven);
			messageDriven.setDestination(destination);
		} else if (IMessageDrivenBeanDataModelProperties.DESTINATIONNAME.equals(property)
				|| IEnterpriseBeanClassDataModelProperties.JNDI_NAME.equals(property)) {
			messageDriven.setMessageSelector((String) propertyValue);
		} else if (IEnterpriseBeanClassDataModelProperties.TRANSACTIONTYPE.equals(property)) {
			TransactionType transactionType = TransactionType.CONTAINER_LITERAL;
			if (propertyValue.equals(TransactionType.BEAN_LITERAL.getName()))
				transactionType = TransactionType.BEAN_LITERAL;
			messageDriven.setTransactionType(transactionType);
		}

	}
}
