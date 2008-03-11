/***********************************************************************
 * Copyright (c) 2008 by SAP AG, Walldorf. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     SAP AG - initial API and implementation
 ***********************************************************************/
package org.eclipse.jst.jee.model.internal.mergers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jst.javaee.ejb.SessionBean;
/**
 * Accessible Bean Merger is used to merge beans that are accessible via interfaces.
 * For the time being it is used to merge only Session Beans.
 * 
 * @author Dimitar Giormov
 */
public class AccessibleBeanMerger extends EnterpriseBeanMerger {

  /**
   * @param baseBean element is the main element that values will be taken with highest priority.
   * @param toMergeBean element will give all of his extra values to base.
   * @param _kind
   */
  public AccessibleBeanMerger(SessionBean baseBean,
      SessionBean toMergeBean, int _kind) {
    super(baseBean, toMergeBean, _kind);
  }

  /* (non-Javadoc)
   * @see org.eclipse.jst.javaee.ejb.model.mergers.EnterpriseBeanMerger#process()
   */
  public List process() throws ModelException {
    List warnings = new ArrayList();
    warnings.addAll(super.process());
    mergeInterfaces(warnings);
    return warnings;
  }

  private void mergeInterfaces(List warnings) throws ModelException {
    boolean isInBase = getBaseBean() instanceof SessionBean;
    boolean isInToMerge = getToMergeBean() instanceof SessionBean;

    if (isInBase && isInToMerge) {
      SessionBean baseBean = (SessionBean) getBaseBean();
      SessionBean toMergeBean = (SessionBean) getToMergeBean();

      List toMergeBusinessLocal = toMergeBean.getBusinessLocals();
      for(Object toMergeIntfs:toMergeBusinessLocal){
        if (containsInterface(baseBean.getBusinessRemotes(), (String) toMergeIntfs)) {
          continue;
        }
        if (!containsInterface(baseBean.getBusinessLocals(), (String) toMergeIntfs)) {
          baseBean.getBusinessLocals().add(toMergeIntfs);
        }
      }

      List toMergeIntfs = toMergeBean.getBusinessRemotes();       
      for(Object toMergeRemoteIntf:toMergeIntfs){
        if (containsInterface(baseBean.getBusinessLocals(), (String) toMergeRemoteIntf)) {
          continue;
        }
        if (!containsInterface(baseBean.getBusinessRemotes(), (String) toMergeRemoteIntf)) {
          baseBean.getBusinessRemotes().add(toMergeRemoteIntf);
        }
      }


      if (baseBean.getLocal() == null){
        String toMergeLocal = toMergeBean.getLocal();
        baseBean.setLocal(toMergeLocal);   
      }

      if (baseBean.getLocalHome() == null){
        String toMergeLocalHome = toMergeBean.getLocalHome();
        baseBean.setLocalHome(toMergeLocalHome);   
      }

      if (baseBean.getRemote() == null){
        String toMergeRemote = toMergeBean.getRemote();
        baseBean.setRemote(toMergeRemote);   
      }
      
      if (baseBean.getHome() == null){
        String toMergeRemoteHome = toMergeBean.getHome();
        baseBean.setHome(toMergeRemoteHome);   
      }
      
    }
  }

  private boolean containsInterface(List business, String intfs) {
    for (Object object : business) {
      if (object.toString().equals(intfs)){
        return true;
      }
    }
    return false;
  }
}
