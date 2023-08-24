package com.bezkoder.springjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bezkoder.springjwt.models.ImageModel;


public interface ImageModelRepository extends JpaRepository<ImageModel, Long>{
	ImageModel findByName (String name);
}
