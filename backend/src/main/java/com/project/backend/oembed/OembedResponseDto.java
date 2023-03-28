package com.project.backend.oembed;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OembedResponseDto {

    private String title;
    private String type;
    private String version;
    private String provider_name;
    private String provider_url;
    private String author_name;
    private String author_url;
    private Integer is_plus;
    private String html;
    private Integer width;
    private Integer height;
    private String duration;
    private String description;
    private String thumbnail_url;
    private Integer thumbnail_width;
    private Integer thumbnail_height;
    private String thumbnail_url_with_play_button;
    private String upload_date;
    private Integer video_id;
    private String uri;
    private Long cache_age;

}
