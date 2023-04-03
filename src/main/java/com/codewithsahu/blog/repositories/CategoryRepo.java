package com.codewithsahu.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithsahu.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
