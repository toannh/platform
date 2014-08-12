/*
 * Copyright (C) 2003-2014 eXo Platform SEA.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
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
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 * exo@exoplatform.com
 * July 31, 2014
 */
public abstract class UIPlatformExtensionContainer extends UIExtensionContainer {

  protected int extensionSize;

  protected static final Log log = ExoLogger.getLogger(UIPlatformExtensionContainer.class);

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

