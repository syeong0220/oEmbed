package com.project.backend.oembed;

import org.springframework.beans.factory.annotation.Autowired;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/oembed")
public class OembedController {
    private static final Logger log = LoggerFactory.getLogger(OembedController.class);

    @Autowired
    private OembedService oEmbedService;

    @GetMapping("/list")
    public @ResponseBody JSONObject getOembedList(@RequestParam(required = false) String url)
            throws Exception {

        log.debug("url : {} ", url);

        return oEmbedService.getOembedList(url);

    }

}
