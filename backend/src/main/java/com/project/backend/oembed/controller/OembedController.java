package com.project.backend.oembed.controller;

import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotBlank;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.backend.oembed.service.OembedService;

@CrossOrigin(origins = "http://localhost:10180")
@RestController
@RequestMapping("api/oembed")
public class OembedController {
    private static final Logger log = LoggerFactory.getLogger(OembedController.class);

    @Autowired
    private OembedService oEmbedService;

    @GetMapping("/list")
    public JSONObject getOembedInfo(@RequestParam(required = false) @NotBlank String url)
            throws Exception {
        return oEmbedService.getOembedInfo(url);
    }

}
