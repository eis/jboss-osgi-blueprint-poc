package fi.eis.applications.jboss.poc.wab;

import org.jboss.logging.Logger;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;

public class HttpServiceTracker extends ServiceTracker {

  private static Logger log = Logger.getLogger(HttpServiceTracker.class);	
	
  public HttpServiceTracker(final BundleContext context) {
    super(context, HttpService.class.getName(), null);
  }

  @Override
  public Object addingService(final ServiceReference reference) {
    HttpService httpService = (HttpService) super.addingService(reference);
    if (httpService == null)
      return null;

    try {
    	log.debug("Registering servlet at /hi_to_osgi");
      httpService.registerServlet("/hi_to_osgi",
          new AnotherHelloWorldServlet(), null, null);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return httpService;
  }

  @Override
  public void removedService(final ServiceReference reference,
      final Object service) {
    HttpService httpService = (HttpService) service;

    log.debug("Unregistering /hi_to_osgi");
    httpService.unregister("/hi_to_osgi");

    super.removedService(reference, service);
  }
}