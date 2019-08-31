package org.isabella.revdol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String viewIndex() {
        return "index.html";
    }

    @GetMapping("/error")
    public String viewError() {
        return "error.html";
    }
}
