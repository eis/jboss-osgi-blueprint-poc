package fi.eis.applications.jboss.poc.wab;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import fi.eis.applications.jboss.poc.osgiservice.api.MessageService;

@SuppressWarnings("serial")
public class AnotherHelloWorldServlet extends HttpServlet {


  private static Logger log = Logger.getLogger(AnotherHelloWorldServlet.class);

  @Resource
  private MessageService service = null;
  
  private BundleContext bundleContext;
  
  public void setMessageService(MessageService service) {
	  log.debug("Setting service: " + service);
	  this.service = service;
  }
  
  public MessageService getMessageService() {
	  return this.service;
  }
  
  // TODO use blueprint for this
  @Override
  public void init(ServletConfig config) throws ServletException {
      super.init(config);
      bundleContext = (BundleContext) config.getServletContext().getAttribute("osgi-bundlecontext");
      
      final HttpServlet servlet = this;
      
      log.debugf("Registering with parameters %s, %s, %s", bundleContext, MessageService.class.getName(), null);
      
      ServiceTracker serviceTracker = new ServiceTracker(bundleContext, MessageService.class.getName(), null){
          @Override
          public Object addingService(final ServiceReference sref) {
            log.infof("Adding service: %s to %s", sref, servlet);
            service = (MessageService) super.addingService(sref);
            return service;
          }

          @Override
          public void removedService(final ServiceReference sref, final Object sinst) {
            super.removedService(sref, service);
            log.infof("Removing service: %s from %s", sref, servlet);
            service = null;
          }    	  
      };  
      serviceTracker.open(); 
  }  

  static String PAGE_HEADER = "<html><head><title>helloworld</title><body>";
  static String PAGE_FOOTER = "</body></html>";

  @Override
  protected void doGet(final HttpServletRequest req,
      final HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("text/html");
    PrintWriter writer = resp.getWriter();
    writer.println(PAGE_HEADER);
    writer.println("<h1>" + service.getMessage() + "</h1>");
    writer.print("Using BundleContext looked up in Servlet Context to list available OSGi Bundles:");
	writer.print("<ul>");
    for (Bundle bundle : bundleContext.getBundles()) {
    	writer.print("<li>");
        writer.print(bundle.getSymbolicName());
    	writer.print("</li>");
    }
	writer.print("</ul>");
    writer.println(PAGE_FOOTER);
    writer.close();
    
  }

}