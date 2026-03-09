package ems.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    
     @GetMapping("/")
    public String home() {
        return "EMS Project Running Successfully 🚀";
    }

    @GetMapping("/test")
    public String test() {
        return "Test API Working ✅";
    }


}
