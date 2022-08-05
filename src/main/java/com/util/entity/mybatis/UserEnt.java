package com.util.entity.mybatis;

import java.io.Serializable;
import java.util.Date;

public class UserEnt implements Serializable {
    private Integer id;

    private Date amenddate;

    private String amenduser;

    private Date createdate;

    private String createuser;

    private String domain;

    private String email;

    private String hostname;

    private String ip;

    private String mobile;

    private String password;

    private String remark;

    private String status;

    private String username;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getAmenddate() {
        return amenddate;
    }

    public void setAmenddate(Date amenddate) {
        this.amenddate = amenddate;
    }

    public String getAmenduser() {
        return amenduser;
    }

    public void setAmenduser(String amenduser) {
        this.amenduser = amenduser;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}