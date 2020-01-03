package com.buckvid.pojo;

import javax.persistence.*;

public class Bgm {
    @Id
    private String id;

    private String author;

    private String name;

    /**
     * Playing Address
     */
    private String path;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取Playing Address
     *
     * @return path - Playing Address
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置Playing Address
     *
     * @param path Playing Address
     */
    public void setPath(String path) {
        this.path = path;
    }
}