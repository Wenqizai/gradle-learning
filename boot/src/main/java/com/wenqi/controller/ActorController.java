package com.wenqi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wenqi Liang
 * @date 2025/3/2
 */
@RestController
@RequestMapping("/actor")
public class ActorController {

    @GetMapping("/who-is-name")
    public String getActorName() {
        return "wenqi";
    }
}
