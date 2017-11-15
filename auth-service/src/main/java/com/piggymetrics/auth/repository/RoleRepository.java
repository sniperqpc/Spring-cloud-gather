package com.piggymetrics.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.piggymetrics.auth.domain.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

}
