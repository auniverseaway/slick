package net.zum.slick.internal;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

    private ServiceRegistration<Object> serviceRegistration;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        final Object service = new Object();
        final Dictionary<String, String> properties = new Hashtable<>();
        properties.put("sling.auth.requirements", "/content/slick/auth");
        serviceRegistration = bundleContext.registerService(Object.class, service, properties);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        if (serviceRegistration != null) {
            serviceRegistration.unregister();
            serviceRegistration = null;
        }
    }

}