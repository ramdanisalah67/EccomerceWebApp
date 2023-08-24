package com.bezkoder.springjwt.controllers;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bezkoder.springjwt.models.Categorie;
import com.bezkoder.springjwt.models.ImageModel;
import com.bezkoder.springjwt.models.Product;
import com.bezkoder.springjwt.repository.CategorieRepository;
import com.bezkoder.springjwt.repository.ImageModelRepository;
import com.bezkoder.springjwt.repository.ProductRepositiry;




@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	
	@Autowired
	private ProductRepositiry productRepository ;
	@Autowired
	private CategorieRepository categorieRepository ;
	@Autowired
	private ImageModelRepository imageRepository ;
	
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}

	@GetMapping(path = { "/get/{imageName}" })
	public ImageModel getImage(@PathVariable("imageName") String imageName) throws IOException {
		System.out.println("hello");
		ImageModel retrievedImage = imageRepository.findByName(imageName);
		
		ImageModel img = new ImageModel(null,retrievedImage.getName(), retrievedImage.getType(),
				retrievedImage.getPicByte());
		return img;
	}
	// uncompress the image bytes before returning it to the angular application
		public static byte[] decompressBytes(byte[] data) {
			Inflater inflater = new Inflater();
			inflater.setInput(data);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
			byte[] buffer = new byte[1024];
			try {
				while (!inflater.finished()) {
					int count = inflater.inflate(buffer);
					outputStream.write(buffer, 0, count);
				}
				outputStream.close();
			} catch (IOException ioe) {
			} catch (DataFormatException e) {
			}
			return outputStream.toByteArray();
		}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String userAccess() {
		return "User Content.";
	}

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		return "Moderator Board.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}
	
	@GetMapping("/AllProduct")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN') ")	
	public List<Product> lister(){
	
		return productRepository.findAll();
		}
	@GetMapping("/AllCategories")
	
	public List<Categorie> categories(){
	
		return categorieRepository.findAll();
		}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = {"/addNewProduct"},consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	
	public Product Ajouter(@RequestPart("product") Product p,@RequestPart("imageFile") MultipartFile [] file) {
		
	try {
		
		Set<ImageModel> images = uploadImage(file);
		p.setProductImages(images);	
		return productRepository.save(p);

			}
	catch(Exception e) {
		System.out.println(e.getMessage());
		return null ;
	}

	}
	
	public Set<ImageModel> uploadImage(MultipartFile [] multipartFiles) throws IOException {
		Set<ImageModel> imageModels = new HashSet<>();
		for (MultipartFile file: multipartFiles) {
			ImageModel imageModel= new ImageModel(null,
						file.getOriginalFilename(),
						file.getContentType(),
						file.getBytes()
					
					);
			imageModels.add(imageModel);
			
			
		}
		return imageModels ;
	}
	
	@GetMapping("/delete/{id}")
	public void Delete(@PathVariable Long id) {
		
		productRepository.deleteById(id);
	}
	@GetMapping("getProductById/{productId}")
	public Product giveme( @PathVariable Long productId) {
		return productRepository.findById(productId).get();
	}
/*	@GetMapping("categorie")
	public List<Categorie> afficher(){
		return categorieRepository.all() ;
	}
	@GetMapping("productByCategorie/{id}")
	public List<Product> go(@PathVariable Long id){
		return productRepository.allProduct(id) ;
	}
	*/
}
















































