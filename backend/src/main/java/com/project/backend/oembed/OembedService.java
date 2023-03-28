package com.project.backend.oembed;

import org.json.simple.JSONObject;

public interface OembedService {

    JSONObject getOembedList(String so) throws Exception;

}
