package com.jauharteam.ojek.ojek;

import com.jauharteam.ojek.identity.IdentityService;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by dery on 11/5/17.
 */
public class IdentityServiceLoader {
    private static IdentityService identityService = null;
    public static IdentityService getIdentityService() {
        if(identityService!=null) return identityService;
        try {
            ConfigLoader configLoader = new ConfigLoader();
            URL url = new URL(configLoader.getConfig().getIdentityServicePath());
            QName qname = new QName("http://identity.ojek.jauharteam.com/", "IdentityServiceImplService");
            Service service = Service.create(url, qname);
            identityService = service.getPort(IdentityService.class);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return identityService;
    }
}
