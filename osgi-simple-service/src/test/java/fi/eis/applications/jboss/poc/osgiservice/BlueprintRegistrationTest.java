package fi.eis.applications.jboss.poc.osgiservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.junit.Arquillian;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import fi.eis.applications.jboss.poc.osgiservice.api.MessageService;

@RunWith(Arquillian.class)
@Ignore
public class BlueprintRegistrationTest {
    @Inject
    public BundleContext context;

    @Inject
    public Bundle bundle;
    @Test
    public void testBlueprintContainerAvailable() throws Exception {/*
        BlueprintSupport.provideBlueprint(context, bundle);
        bundle.start();
        assertEquals("example-blueprint", bundle.getSymbolicName());
        BlueprintContainer container = getBlueprintContainer();
        assertNotNull("BlueprintContainer available", container);
        */
    }

    @Test
    public void testServiceA() throws Exception {
        ServiceReference sref = context.getServiceReference(MessageService.class.getName());
        assertNotNull("ServiceA not null", sref);
        MessageService service = (MessageService) context.getService(sref);
    }    
}
