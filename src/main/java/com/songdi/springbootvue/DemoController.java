package com.songdi.springbootvue;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/demo")
public class DemoController {
    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);
    @Autowired
    MeterRegistry registry;
    private Counter counter_core;
    private Counter counter_index;

    @PostConstruct
    private void init() {
        counter_core = registry.counter("business_requests_method_count", "method", "create user");
        counter_index = registry.counter("business_requests_method_count", "method", "query the bill");
    }

    @GetMapping(value = "/query-bill/{objectId}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String bill(@PathVariable("objectId") Integer objectId) {
        counter_index.increment();
        return "bill";
    }

    @GetMapping(value = "/createUser/{objectId}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String createUser(@PathVariable("objectId") Integer objectId) {
        counter_core.increment();
        return "createUser";
    }

    @GetMapping(value = "/hello/{objectId}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String hello(@PathVariable("objectId") String objectId) {

        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        logger.info("{} hello {}", address.getHostAddress(), objectId);

        return address.getHostAddress() + " Hello " + objectId + " v1 \n";

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
        return address.getHostAddress() + "Fusing Hello World " + objectId + " v1 \n";

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
        return address.getHostAddress() + "Delayed Hello World " + objectId + " v1 \n";

    }

    @GetMapping(value = "/cpu/{duration}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String cpuUseRatio(@PathVariable("duration") Integer duration) throws InterruptedException {
        logger.info("{} cpu {}", "", duration);
        int busyTime = 199;
        int idelTime = 200 - busyTime; // 50%的占有率
        long startTime = System.currentTimeMillis();
        long runTime = 0;
        while (true && System.currentTimeMillis() - startTime < duration) {
            runTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - runTime < busyTime)
                ;
            Thread.sleep(idelTime);
        }

        return "cpuUseRatio Hello World \n";

    }

    @PostMapping(value = "/echo", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Map<String, Object> postEcho(@RequestBody Map<String, String> stringMap,
        @RequestHeader Map<String, String> headers) {

        Map<String, Object> res = new HashMap<>();

        res.put("RequestBody", stringMap);
        res.put("headers", headers);
        return res;

    }
}
