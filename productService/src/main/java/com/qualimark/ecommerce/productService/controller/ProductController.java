package com.qualimark.ecommerce.productService.controller;

import com.qualimark.ecommerce.productService.model.Product;
import com.qualimark.ecommerce.productService.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.findById(id);
            return ResponseEntity.ok(product);
        }catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping (value="/create" ,consumes ="application/json" ,produces = "application/json")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        try{
            return ResponseEntity.created(null).body(productService.createProduct(product));
        }catch (IllegalArgumentException e) {
            System.out.println(e.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        try {
            return ResponseEntity.ok(productService.updateProduct(id, product));
        }catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();

        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            this.productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        }catch (IllegalArgumentException e) {
            System.out.println(e.toString());
            return ResponseEntity.notFound().build();
        }
    }

}
