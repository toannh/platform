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


import org.exoplatform.webui.core.UIContainer;

import java.util.HashMap;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 * exo@exoplatform.com
 * July 31, 2014
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
