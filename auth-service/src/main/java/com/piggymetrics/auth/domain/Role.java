package com.piggymetrics.auth.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "uaa_role")
public class Role extends AbstractAuditingEntity {

    /** serialVersionUID */
    private static final long serialVersionUID = -4283843346647863732L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "role_name")
    private String roleName;
    
    @Column(name = "desciption")
    private String desciption;
    
    @JsonIgnore
	@ManyToMany(targetEntity = Permission.class, fetch = FetchType.EAGER)
	@BatchSize(size = 20)
	private Set<Permission> permissions = new HashSet<>();

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDesciption() {
        return desciption;
    }
    
    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}
}
