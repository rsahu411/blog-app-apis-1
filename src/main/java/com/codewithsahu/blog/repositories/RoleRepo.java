package com.codewithsahu.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithsahu.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
