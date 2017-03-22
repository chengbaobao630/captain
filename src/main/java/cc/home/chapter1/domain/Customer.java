package cc.home.chapter1.domain;

import cc.home.chapter1.helper.BaseEntity;
import cc.home.chapter1.helper.annotation.Table;

/**
 * Created by cheng on 2017/3/22 0022.
 */
@Table(name = "customer")
public class Customer extends BaseEntity{

    private long id;

    private String name;

    private String contact;

    private String telephone;

    private String email;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
