package com.laca.scraperapp.controller;

import com.laca.scraperapp.util.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SystemController {

    @GetMapping("/exit")
    public ResponseEntity exit() {
        new Thread(SystemController::initiateServiceShutdown).start();
        return new ResponseEntity("Service shut down in 2 secs", HttpStatus.OK);
    }

    private static void initiateServiceShutdown() {
        Utils.waitMilliseconds(2000L);
        System.exit(0);
    }
}
