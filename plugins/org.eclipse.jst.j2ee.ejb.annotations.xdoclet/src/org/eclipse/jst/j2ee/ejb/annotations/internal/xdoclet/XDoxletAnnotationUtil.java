/***************************************************************************************************
 * Copyright (c) 2005 Eteration A.S. and others. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Eteration A.S. - initial API and implementation
 **************************************************************************************************/

package org.eclipse.jst.j2ee.ejb.annotations.internal.xdoclet;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TypeDeclaration;


public class XDoxletAnnotationUtil {
	private static final String XDOCLET_EJB_BEAN_TAG = "@ejb.bean"; //$NON-NLS-1$
	private static final String XDOCLET_WEB_TAG = "@web"; //$NON-NLS-1$
	public static class XDocletFinder extends ASTVisitor {
		boolean isXDocletBean = false;
		boolean isXDocletWebBean = false;
	
		public XDocletFinder(boolean visitDocTags) {
			super(visitDocTags);
		}

		public boolean visit(TypeDeclaration type) {
			if (type.getJavadoc() == null)
				return true;
			Iterator tags = type.getJavadoc().tags().iterator();
			while (tags.hasNext()) {
				TagElement element = (TagElement) tags.next();
				if (XDOCLET_EJB_BEAN_TAG.equals(element.getTagName())) {
					isXDocletBean=true;
					return false;
				}else	if (element.getTagName() != null && element.getTagName().startsWith(XDOCLET_WEB_TAG)) {
					isXDocletWebBean=true;
					return false;
				}
			}
			return true;
		}
	}

	public static boolean isXDocletAnnotatedResource(IResource resource) {
		ICompilationUnit compilationUnit = JavaCore.createCompilationUnitFrom((IFile) resource);
		return isXDocletAnnotated(compilationUnit);
	}

	
	public static boolean isXDocletAnnotatedEjbClass(IJavaElement compilationUnit) {
		if (compilationUnit != null && compilationUnit.getElementType() == IJavaElement.COMPILATION_UNIT){
			ASTParser parser = ASTParser.newParser(AST.JLS3);
			parser.setSource((ICompilationUnit)compilationUnit);
			CompilationUnit ast = (CompilationUnit) parser.createAST(null);
			XDoxletAnnotationUtil.XDocletFinder docletFinder = new XDoxletAnnotationUtil.XDocletFinder(true);
			ast.accept(docletFinder);
			return docletFinder.isXDocletBean;
		}
		return false;
	}
	public static boolean isXDocletAnnotatedWebClass(IJavaElement compilationUnit) {
		if (compilationUnit != null && compilationUnit.getElementType() == IJavaElement.COMPILATION_UNIT){
			ASTParser parser = ASTParser.newParser(AST.JLS3);
			parser.setSource((ICompilationUnit)compilationUnit);
			CompilationUnit ast = (CompilationUnit) parser.createAST(null);
			XDoxletAnnotationUtil.XDocletFinder docletFinder = new XDoxletAnnotationUtil.XDocletFinder(true);
			ast.accept(docletFinder);
			return docletFinder.isXDocletWebBean;
		}
		return false;
	}
	public static boolean isXDocletAnnotated(IJavaElement compilationUnit) {
		if (compilationUnit != null && compilationUnit.getElementType() == IJavaElement.COMPILATION_UNIT){
			ASTParser parser = ASTParser.newParser(AST.JLS3);
			parser.setSource((ICompilationUnit)compilationUnit);
			CompilationUnit ast = (CompilationUnit) parser.createAST(null);
			XDoxletAnnotationUtil.XDocletFinder docletFinder = new XDoxletAnnotationUtil.XDocletFinder(true);
			ast.accept(docletFinder);
			return docletFinder.isXDocletWebBean || docletFinder.isXDocletBean ;
		}
		return false;
	}
}
