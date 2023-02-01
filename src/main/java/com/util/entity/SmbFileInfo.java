package com.util.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @Classname: SmbFile
 * @Date: 2023/2/1 上午 10:05
 * @Author: kalam_au
 * @Description:
 */


public class SmbFileInfo {
    private static final Logger log = LogManager.getLogger(SmbFileInfo.class.getName());

    private String ip;
    private String username;
    private String password;
    private String filepath;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}

