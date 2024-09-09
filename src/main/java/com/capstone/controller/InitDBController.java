package com.capstone.controller;

import com.capstone.service.InitDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InitDBController {
    private final InitDBService initDBService;

    @GetMapping("/init/uploadBottom")
    public void uploadBottomCSV() {
        initDBService.saveBottomCSV();
    }

    @GetMapping("/init/uploadTop")
    public void uploadTopCSV() {
        initDBService.saveTopCSV();
    }
}
