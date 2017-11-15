/*
 * Copyright @ 1998-2017 Shenzhen Kingdom Technology CO.,LTD.
 * All Rights Reserved
 *
 *      http://www.szkingdom.com/
 *      http://www.szrhtj.com/
 *
 */

package com.piggymetrics.auth.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "uaa_role")
public class Role implements Serializable {

    /** serialVersionUID TODO */
    private static final long serialVersionUID = -4283843346647863732L;

    @Id
    private Long id;
    
    @Column(name = "parent_role_id")
    private Role parent;
    
    @Column(name = "role_name")
    private String roleName;
    
    @Column(name = "generate_time")
    private Date generateTime;
    
    @Column(name = "desciption")
    private String desciption;

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Role getParent() {
        return parent;
    }

    public void setParent(Role parent) {
        this.parent = parent;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Date getGenerateTime() {
        return generateTime;
    }

    public void setGenerateTime(Date generateTime) {
        this.generateTime = generateTime;
    }

    public String getDesciption() {
        return desciption;
    }
    
    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }
}
