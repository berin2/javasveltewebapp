package com.svelteup.app.backend.utils.objects;

import com.svelteup.app.backend.utils.enums.ProtocolEnum;
import lombok.Getter;
import org.osgi.service.url.URLConstants;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;

/**
 * HostDescriptor is a class which returns a string  that
 */
public class HostDescriptor {

    @Getter
    protected String hostString;

    public HostDescriptor(Environment env) throws UnknownHostException {
        String hostString = InetAddress.getLocalHost().getHostAddress();
        String portString = env.getProperty("server.port");
        String protocolEnumName = null;
        boolean sslEnabled = Boolean.parseBoolean(env.getProperty("ssl.enabled"));

        if(sslEnabled)
            protocolEnumName = ProtocolEnum.HTTPS.name().toLowerCase(Locale.ROOT);
        else
            protocolEnumName =  ProtocolEnum.HTTP.name().toLowerCase(Locale.ROOT);

        this.hostString = String.format("%s://%s:%s",protocolEnumName,hostString,portString);
    }
}
