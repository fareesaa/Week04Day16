package com.codeflex.springboot.repository;


import java.util.List;

import com.codeflex.springboot.model.Product;
import org.springframework.stereotype.Service;

public interface ProductRepository {
	
	Product findById(long id);
	
	Product findByName(String name);
	
	int saveProduct(Product product);
	
	int updateProduct(Product product);
	
	int deleteProductById(long id);

	List<Product> findAllProducts();

	int deleteAllProducts();
	
	boolean isProductExist(Product product);
	
}
