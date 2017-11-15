package com.piggymetrics.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.piggymetrics.auth.domain.Permission;

@Repository
public interface PermissionRepository extends CrudRepository<Permission, Long> {

}
