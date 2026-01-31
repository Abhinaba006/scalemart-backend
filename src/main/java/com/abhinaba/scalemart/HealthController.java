package com.abhinaba.scalemart;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health")
    public String checkStatus(){
        return "ScaleMart Server is Up - reloaded (Gradle Version)!";
    }
}
