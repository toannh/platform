package org.exoplatform.platform.common.account.setup.web;

import org.exoplatform.commons.api.settings.SettingService;
import org.exoplatform.commons.api.settings.SettingValue;
import org.exoplatform.commons.api.settings.data.Context;
import org.exoplatform.commons.api.settings.data.Scope;
import org.exoplatform.commons.utils.PropertyManager;
import org.exoplatform.container.PortalContainer;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.web.filter.Filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author <a href="fbradai@exoplatform.com">Fbradai</a>
 * @date 3/4/13
 */
public class AccountSetupFilter implements Filter {
    private static final String PLF_PLATFORM_EXTENSION_SERVLET_CTX = "/platform-extension";
    private static final String ACCOUNT_SETUP_SERVLET = "/accountSetup";

    private static final Log LOG = ExoLogger.getLogger(AccountSetupFilter.class);
    SettingService settingService ;
    // TODO : replace hard coded variable by ...
    private static final String REST_URI = "/rest";
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        boolean isDevMod = PropertyManager.isDevelopping();
        settingService = (SettingService) PortalContainer.getInstance().getComponentInstanceOfType(SettingService.class);
        boolean setupDone = false;
        SettingValue accountSetupNode = settingService.get(Context.GLOBAL, Scope.GLOBAL, AccountSetup.ACCOUNT_SETUP_NODE);
        if(accountSetupNode != null)
            setupDone = true;
        String requestUri = httpServletRequest.getRequestURI();
        boolean isRestUri = (requestUri.contains(REST_URI));
        if((!setupDone)&&(!isDevMod)&&(!isRestUri)){
            ServletContext platformExtensionContext = httpServletRequest.getSession().getServletContext().getContext(PLF_PLATFORM_EXTENSION_SERVLET_CTX);
            platformExtensionContext.getRequestDispatcher(ACCOUNT_SETUP_SERVLET).forward(httpServletRequest, httpServletResponse);
            return;
        }
        chain.doFilter(request, response);
    }
}