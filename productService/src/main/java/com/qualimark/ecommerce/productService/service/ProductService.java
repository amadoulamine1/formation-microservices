package com.qualimark.ecommerce.productService.service;

import com.qualimark.ecommerce.productService.model.Product;
import com.qualimark.ecommerce.productService.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class ProductService {

    // Injection de dépendance du repository
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }


    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("No product found with id " + id));
    }

    public Product findByName(String name){
        return productRepository.findByName(name).orElseThrow( () -> new IllegalArgumentException("No product found with name " + name));
    }

    public Product createProduct(Product product) {
        if(productRepository.findByName(product.getName()).isPresent()){
            throw  new IllegalArgumentException(product.getName() + " already exists");
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Long id,Product product) {
        Product oldProduct=this.findById(id);
        oldProduct.setName(product.getName());
        oldProduct.setDescription(product.getDescription());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setStock(product.getStock());
        oldProduct.setCategory(product.getCategory());
        return productRepository.save(oldProduct);
    }

    public void deleteProduct( Long id){
        Product product=this.findById(id);
        productRepository.deleteById(id);
    }

}
