package com.mkyong.controller;

import com.mkyong.model.Book;
import com.mkyong.repository.BookRepository;
import com.mkyong.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RestApiController {

    public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

    @Autowired
            @Qualifier("NamedParameterJdbcBookRepository")
    BookRepository productService; //Service which will do all data retrieval/manipulation work

    // -------------------Retrieve All Products--------------------------------------------

    @RequestMapping(value = "/product/", method = RequestMethod.GET, produces="application/json")
    public ResponseEntity<List<Book>> listAllProducts() {
        List<Book> products = productService.findAll();
        if (products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // -------------------Retrieve Single Product------------------------------------------

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getProduct(@PathVariable("id") long id) {
        logger.info("Fetching Product with id {}", id);
        Optional <Book> product = productService.findById(id);
        if (product == null) {
            logger.error("Product with id {} not found.", id);
            return new ResponseEntity<>(new CustomErrorType("Product with id " + id  + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    // -------------------Create a Product-------------------------------------------

    @RequestMapping(value = "/product/", method = RequestMethod.POST, produces="application/json")
    public ResponseEntity<?> createProduct(@RequestBody Book product) {
        logger.info("Creating Product : {}", product);

        if (productService.isProductExist(product)) {
            logger.error("Unable to create. A Product with name {} already exist", product.getName());
            return new ResponseEntity<>(new CustomErrorType("Unable to create. A Product with name " +
                    product.getName() + " already exist."), HttpStatus.CONFLICT);
        }
        productService.saveProduct(product);

        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    // ------------------- Update a Product ------------------------------------------------

    @RequestMapping(value = "/product/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProduct(@PathVariable("id") long id, @RequestBody Book product) {
        logger.info("Updating Product with id {}", id);

        Optional <Book> currentProduct = productService.findById(id);

        if (currentProduct == null) {
            logger.error("Unable to update. Product with id {} not found.", id);
            return new ResponseEntity<>(new CustomErrorType("Unable to upate. Product with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        currentProduct.setName(product.getName());
        currentProduct.setCategoryId(product.getCategoryId());
        currentProduct.setPrice(product.getPrice());

        productService.updateProduct(currentProduct);
        return new ResponseEntity<>(currentProduct, HttpStatus.OK);
    }

    // ------------------- Delete a Product-----------------------------------------

    @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProduct(@PathVariable("id") long id) {
        logger.info("Fetching & Deleting Product with id {}", id);

        Optional<Book> product = productService.findById(id);
        if (product == null) {
            logger.error("Unable to delete. Product with id {} not found.", id);
            return new ResponseEntity<>(new CustomErrorType("Unable to delete. Product with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        productService.deleteProductById(id);
        return new ResponseEntity<Book>(HttpStatus.NO_CONTENT);
    }

    // ------------------- Delete All Products-----------------------------

    @RequestMapping(value = "/product/", method = RequestMethod.DELETE)
    public ResponseEntity<Book> deleteAllProducts() {
        logger.info("Deleting All Products");

        productService.deleteAllProducts();
        return new ResponseEntity<Book>(HttpStatus.NO_CONTENT);
    }

}