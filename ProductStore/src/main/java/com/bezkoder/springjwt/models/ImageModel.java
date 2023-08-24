package com.bezkoder.springjwt.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;
	private String name ;
	private String type ;
	@Column(length = 500000000)
	private byte[] picByte ;
}
