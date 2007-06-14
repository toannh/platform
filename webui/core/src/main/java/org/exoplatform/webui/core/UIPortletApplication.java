package org.exoplatform.webui.core;

import java.io.Writer;
import java.util.List;

import javax.portlet.PortletResponse;

import org.exoplatform.services.portletcontainer.ExoPortletRequest;
import org.exoplatform.services.portletcontainer.pci.ExoWindowID;
import org.exoplatform.services.portletcontainer.pci.WindowID;
import org.exoplatform.webui.application.WebuiApplication;
import org.exoplatform.webui.application.WebuiRequestContext;

abstract public class UIPortletApplication extends  UIApplication {
  
  static public String VIEW_MODE = "ViewMode" ;
  static public String EDIT_MODE = "EditMode" ;
  static public String HELP_MODE = "HelpMode" ;
  static public String CONFIG_MODE = "ConfigMode" ;

  public UIPortletApplication() throws Exception {
    
  }
  
  public void  processRender(WebuiApplication app, WebuiRequestContext context) throws Exception {
    WebuiRequestContext pContext = (WebuiRequestContext)context.getParentAppRequestContext();
    ExoPortletRequest renderRequest = (ExoPortletRequest) context.getRequest();
    WindowID windowID = renderRequest.getWindowID();
    if(context.useAjax() && !pContext.getFullRender()) {
      Writer w =  context.getWriter() ;
      w.write("<div class=\"PortletResponse\" style=\"display: none\">") ;
      w.  append("<div class=\"PortletResponsePortletId\">"+windowID.getUniqueID()+"</div>") ;
      w.  append("<div class=\"PortletResponsePortletTitle\"></div>") ;
      w.  append("<div class=\"PortletResponsePortletMode\"></div>") ;
      w.  append("<div class=\"PortletResponsePortletState\"></div>") ;
      w.  append("<div class=\"PortletResponseData\">") ;
      List<UIComponent> list = context.getUIComponentToUpdateByAjax() ;
      if(list == null) list = app.getDefaultUIComponentToUpdateByAjax(context) ;      
      for(UIComponent uicomponent : list) {
        renderBlockToUpdate(uicomponent, context, w) ;
      }
      w.  append("</div>") ;
      w.  append("<div class=\"PortletResponseScript\"></div>") ;
      w.write("</div>") ;
    }  else {
      super.processRender(context) ;
    }
  }
}