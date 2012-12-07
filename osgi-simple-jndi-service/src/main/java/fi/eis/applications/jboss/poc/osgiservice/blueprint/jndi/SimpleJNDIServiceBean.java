package fi.eis.applications.jboss.poc.osgiservice.blueprint.jndi;

import org.osgi.service.jndi.JNDIContextManager;

import fi.eis.applications.jboss.poc.osgiservice.api.MessageService;

import java.util.Map;

public class SimpleJNDIServiceBean implements MessageService {
    private JNDIContextManager jndiContextManager;

    public void bindJNDIService(JNDIContextManager jndiContextManager, Map props) {
        this.jndiContextManager = jndiContextManager;
        System.out.println(jndiContextManager + " is linked");
    }

    public void unbindJNDIService(JNDIContextManager jndiContextManager, Map props) {
        this.jndiContextManager = null;
        System.out.println("jndiContextManager is unlinked");
    }
	public String getMessage() {
		return "Hello";
	}
}
