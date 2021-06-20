package util.entity;

//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


//@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_table")
@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(strategy = "identity", name = "id")
    @Column
    private Integer id;
    @Column
    private String password;


    //personal info
    @Column
    private String userName;
    @Column
    private String status;
    @Column
    private String email;
    @Column
    private String mobile;

    @Column
    private String domain;
    @Column
    private String ip;
    @Column
    private String hostName;

    @Column
    private String createUser;
    //注册日子
    @Column
    private LocalDateTime createDate;
    @Column
    private String amendUser;
    @Column
    private LocalDateTime amendDate;
    @Column
    private String remark;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getAmendUser() {
        return amendUser;
    }

    public void setAmendUser(String amendUser) {
        this.amendUser = amendUser;
    }

    public LocalDateTime getAmendDate() {
        return amendDate;
    }

    public void setAmendDate(LocalDateTime amendDate) {
        this.amendDate = amendDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
