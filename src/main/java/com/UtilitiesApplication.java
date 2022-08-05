package com;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
public class UtilitiesApplication {

    private static final Logger log = LogManager.getLogger(UtilitiesApplication.class.getName());




    public static void main(String[] args) {
        SpringApplication.run(UtilitiesApplication.class, args);
    }




    public static void getHello() {
        log.traceEntry();
        log.trace("我是trace");
        log.info("我是info信息:{}", "ACE_UTIL");
        log.error("我是error");
        log.fatal("我是fatal");
        log.trace("退出程序.");
        log.traceExit();
        return;
    }


}
