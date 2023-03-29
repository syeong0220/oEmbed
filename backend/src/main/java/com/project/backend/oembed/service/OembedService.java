package com.project.backend.oembed.service;

import org.json.simple.JSONObject;

public interface OembedService {
    JSONObject getOembedInfo(String so) throws Exception;
}
