package org.exoplatform.platform.component.extension;

import org.exoplatform.platform.component.UICreatePlatformToolBarPortlet;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.webui.application.WebuiRequestContext;
import org.exoplatform.webui.core.UIComponent;
import org.exoplatform.webui.ext.UIExtension;
import org.exoplatform.webui.ext.UIExtensionManager;

import java.util.HashMap;
import java.util.List;

/**
 * Created by toannh on 7/30/14.
 */
public abstract class UIPlatformExtensionContainer extends UIExtensionContainer {

  protected int extensionSize;

  protected static final Log log = ExoLogger.getLogger("org.exoplatform.wiki.webui.control.UIWikiExtensionContainer");

  @Override
  public void processRender(WebuiRequestContext context) throws Exception {
    try {
      UICreatePlatformToolBarPortlet uiCreatePlatformToolBarPortlet = getAncestorOfType(UICreatePlatformToolBarPortlet.class);
      HashMap<String, Object> extContext =  uiCreatePlatformToolBarPortlet.getUIExtContext();
      if (checkModificationContext(extContext)) {
        UIExtensionManager manager = getApplicationComponent(UIExtensionManager.class);
        List<UIExtension> extensions = manager.getUIExtensions(getExtensionType());

        extensionSize = 0;

//        Add new children
        if (extensions != null && extensions.size() > 0) {
          // Remove old extension
          for (UIExtension extension : extensions) {
            removeChild(extension.getComponent());
          }
//          Add new extension
          for (UIExtension extension : extensions) {
            UIComponent uicomponent = manager.addUIExtension(extension, extContext, this);
            if (uicomponent != null) {
              extensionSize++;
            }
          }
        }
      }

      super.processRender(context);
    } catch (Exception e) {
      if (log.isDebugEnabled()) {
        log.debug("[UIPLFExtensionContainer] An exception happens when rendering UIPLFExtensionContainer", e);
      }
    }
  }

  public abstract String getExtensionType();
}

