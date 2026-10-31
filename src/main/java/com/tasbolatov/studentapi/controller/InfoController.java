package com.tasbolatov.studentapi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class InfoController {

    @Value("${app.student-name}")
    private String studentName;

    @Value("${app.version}")
    private String version;

    @GetMapping("/")
    public Map<String, String> info() {
        Map<String, String> info = new LinkedHashMap<>();
        info.put("project", "tasbolatov-temirlan-student-api");
        info.put("author", studentName);
        info.put("version", version);
        info.put("endpoints", "/api/students");
        return info;
    }
}
