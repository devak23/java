package tcp;

import lombok.extern.slf4j.Slf4j;

import java.net.*;
import java.util.Enumeration;

@Slf4j
public class InetAddressExample {

    public static void main(String[] args) {
        // Get network interfaces and associated addresses for this host
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            log.info("Following network interfaces found on this machine:");
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                log.info("Name: {}", iface.getName());

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    log.info("type (v4/v6)? : {}. Address: {}",
                            address instanceof Inet4Address  ? "v4" : address instanceof Inet6Address ?  "v6" : "unknown"
                            ,address.getHostAddress());
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        // Get the name(s)/address(es) or hosts given on the command line
        for (String host: args) {
            try {
                log.info("{}: ", host);
                InetAddress[] addresses = InetAddress.getAllByName(host);

                for(InetAddress address: addresses) {
                    log.info("{}/{}", address.getHostName(), address.getHostAddress());
                }

            } catch (UnknownHostException e) {
                log.error("Cannot find address for host: {}", host);
            }
        }
    }
}
