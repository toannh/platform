package org.exoplatform.platform.common.navigation;


import org.exoplatform.container.PortalContainer;
import org.exoplatform.container.configuration.ConfigurationManager;
import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.services.jcr.ext.hierarchy.NodeHierarchyCreator;
import org.exoplatform.test.BasicTestCase;

import javax.jcr.Session;


public class TestNavigationServiceHandler extends BasicTestCase {
    protected PortalContainer container;

    protected RepositoryService repositoryService;

    protected NavigationServiceHandler navigationServiceHandler;

    protected ConfigurationManager configurationManager;
    protected NodeHierarchyCreator nodeHierarchyCreator;



    public void setUp() throws Exception {
        container = PortalContainer.getInstance();
        repositoryService = getService(RepositoryService.class);
        navigationServiceHandler = getService(NavigationServiceHandler.class);
        configurationManager = getService(ConfigurationManager.class);
        nodeHierarchyCreator = getService(NodeHierarchyCreator.class);
        Session session = null;
        // invoke navigationServiceHandler() explicitly to store the new version in the JCR
        navigationServiceHandler.start();
    }
    public void testgetHomePageLogoURI() throws Exception {
        String ss =   navigationServiceHandler.getHomePageLogoURI();
        assertNull(ss);

    }
    protected <T> T getService(Class<T> clazz) {
        return clazz.cast(container.getComponentInstanceOfType(clazz));
    }
}
