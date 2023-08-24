package com.bezkoder.springjwt.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Categorie {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;
	private String categorieName ;
	
	@OneToMany(mappedBy = "categorie")
	private List<Product> products;
}





