package com.fx.api.web;

import com.fx.api.service.AcceptingState;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AcceptingState acceptingState;

    public AdminController(AcceptingState acceptingState) {
        this.acceptingState = acceptingState;
    }

    @GetMapping("/accepting")
    public Map<String, Boolean> getAccepting() {
        return Map.of("accepting", acceptingState.isAccepting());
    }

    @PostMapping("/accepting")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Boolean> setAccepting(@RequestBody Map<String, Boolean> body) {
        boolean value = body.getOrDefault("accepting", true);
        acceptingState.set(value);
        return Map.of("accepting", acceptingState.isAccepting());
    }
}
