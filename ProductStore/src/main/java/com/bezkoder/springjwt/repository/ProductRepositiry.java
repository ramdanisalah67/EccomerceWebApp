package com.bezkoder.springjwt.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bezkoder.springjwt.models.Product;

import jakarta.websocket.server.PathParam;


public interface ProductRepositiry extends JpaRepository<Product, Long>{

	//@Query("select p from Product p join Categorie c where c=:x")
	//List<Product> allProduct(@Param("x") Long id);
	
	Page<Product> findByNameContaining(String name,Pageable p);
}
