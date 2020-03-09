package com.shiku.robot.shikugame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan("com.shiku.robot.shikugame.base.filter")
@SpringBootApplication
public class ShikuGameApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShikuGameApplication.class, args);
    }

}
