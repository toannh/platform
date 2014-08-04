package org.exoplatform.platform.component.extension;


import org.exoplatform.webui.core.UIContainer;

import java.util.HashMap;

/**
 * Created by toannh on 7/31/14.
 */
public class UIExtensionContainer extends UIContainer {

  private HashMap<String, Object> extContext = null;

  protected boolean checkModificationContext(HashMap<String, Object> extContext2) throws Exception {
    if (!extContext2.equals(extContext)) {
      extContext = (HashMap<String, Object>) extContext2.clone();
      return true;
    }
    return false;
  }
}
