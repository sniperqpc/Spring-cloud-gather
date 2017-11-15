package com.piggymetrics.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.piggymetrics.auth.domain.Group;

@Repository
public interface GroupRepository extends CrudRepository<Group, Long> {

}
