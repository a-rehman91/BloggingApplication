package com.api.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
