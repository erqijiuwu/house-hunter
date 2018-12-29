package com.neat.data.template;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class IndexTemplate {
	@RequestMapping("/")
    public String index() {
        return "Merry Christmas!";
    }
}
