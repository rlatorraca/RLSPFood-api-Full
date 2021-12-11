package ca.com.rlsp.rlspfoodapi.hostcheck;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class HostCheckController {

    @GetMapping("/hostcheck")
    public String hostcheck() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress() + " - "
                + InetAddress.getLocalHost().getHostName();
    }
}
