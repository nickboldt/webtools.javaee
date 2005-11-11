package org.eclipse.jst.j2ee.jca.project.facet;

import org.eclipse.jst.j2ee.internal.common.CreationConstants;
import org.eclipse.jst.j2ee.internal.common.J2EEVersionUtil;
import org.eclipse.jst.j2ee.internal.project.J2EEProjectUtilities;
import org.eclipse.jst.j2ee.project.facet.J2EEModuleFacetInstallDataModelProvider;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

public class ConnectorFacetInstallDataModelProvider extends J2EEModuleFacetInstallDataModelProvider implements IConnectorFacetInstallDataModelProperties {

	public Object getDefaultProperty(String propertyName) {
		if (propertyName.equals(FACET_ID)) {
			return J2EEProjectUtilities.JCA;
		} else if (propertyName.equals(CONFIG_FOLDER))
			return CreationConstants.DEFAULT_CONNECTOR_SOURCE_FOLDER;
		return super.getDefaultProperty(propertyName);
	}
	
	protected int convertFacetVersionToJ2EEVersion(IProjectFacetVersion version) {
		return J2EEVersionUtil.convertConnectorVersionStringToJ2EEVersionID(version.getVersionString());
	}
}
