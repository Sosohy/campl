package com.example.campl_;

import java.io.Serializable;

public class UrlDTO implements Serializable {

    String name;
    String link;

    public UrlDTO(String name, String link) {
        this.name = name;
        this.link = link;
    }

    public UrlDTO() {
        this.name = "default";
        this.link = "default";
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }



}
