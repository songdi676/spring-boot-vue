package com.songdi.springbootvue;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/demo")
public class DemoController {
    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @GetMapping(value = "/hello/{objectId}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String hello(@PathVariable("objectId") String objectId) {

        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        logger.info("{} hello {}", address.getHostAddress(), objectId);

        return address.getHostAddress() + " Hello " + objectId + " v2 \n";

    }

    @GetMapping(value = "/fusing/{objectId}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String fusing(@PathVariable("objectId") Integer objectId) {

        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        logger.info("{} hello {}", address.getHostAddress(), objectId);

        if (objectId % 2 == 0) {
            throw new RuntimeException("异常");
        }
        return address.getHostAddress() + "Fusing Hello World " + objectId + " v2 \n";

    }

    @GetMapping(value = "/delayed/{objectId}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String delayed(@PathVariable("objectId") Integer objectId) throws InterruptedException {

        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        logger.info("{} hello {}", address.getHostAddress(), objectId);

        Thread.sleep(objectId * 20);
        return address.getHostAddress() + "Delayed Hello World " + objectId + " v2 \n";

    }
}
