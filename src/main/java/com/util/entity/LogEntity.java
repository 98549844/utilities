package com.util.entity;

//import lombok.Data;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


//@Data
//@EntityListeners(AuditingEntityListener.class)
@Table(name = "log_table")
@Entity
public class LogEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(strategy="identity", name="id")
    @Column(name = "logId")
    Long logId;

    @Column(name = "operator")
    String operator;
    @Column(name = "description")
    String description;
    @Column(name = "accessTime")
    Date accessTime;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
    }
}
