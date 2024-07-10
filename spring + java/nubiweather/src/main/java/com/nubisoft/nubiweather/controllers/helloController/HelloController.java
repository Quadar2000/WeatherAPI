package com.nubisoft.nubiweather.controllers.helloController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nubisoft.nubiweather.models.basicMessage.BasicMessage;

@RestController
public class HelloController {

    @RequestMapping("/")
    public BasicMessage index() {
        return new BasicMessage("Nubiweather API");
    }
}