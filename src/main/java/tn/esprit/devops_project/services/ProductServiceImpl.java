package tn.esprit.devops_project.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.devops_project.services.Iservices.IProductService;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements IProductService {

    final ProductRepository productRepository;
    final StockRepository stockRepository;

    @Override
    public Product addProduct(Product product, Long idStock) {
        log.info("Attempting to add product: {} to stock with id: {}", product.getTitle(), idStock);
        Stock stock = stockRepository.findById(idStock).orElseThrow(() -> {
            log.error("Stock with id: {} not found", idStock);
            return new NullPointerException("Stock not found");
        });
        product.setStock(stock);
        Product savedProduct = productRepository.save(product);
        log.info("Product added successfully with id: {}", savedProduct.getIdProduct());
        return savedProduct;
    }

    @Override
    public Product retrieveProduct(Long id) {
        log.info("Retrieving product with id: {}", id);
        return productRepository.findById(id).orElseThrow(() -> {
            log.error("Product with id: {} not found", id);
            return new NullPointerException("Product not found");
        });
    }

    @Override
    public List<Product> retreiveAllProduct() {
        log.info("Retrieving all products");
        List<Product> products = productRepository.findAll();
        log.debug("Total products retrieved: {}", products.size());
        return products;
    }

    @Override
    public List<Product> retrieveProductByCategory(ProductCategory category) {
        log.info("Retrieving products by category: {}", category);
        List<Product> products = productRepository.findByCategory(category);
        log.debug("Total products in category {}: {}", category, products.size());
        return products;
    }

    @Override
    public void deleteProduct(Long id) {
        log.info("Attempting to delete product with id: {}", id);
        productRepository.deleteById(id);
        log.info("Product with id: {} deleted successfully", id);
    }

    @Override
    public List<Product> retreiveProductStock(Long id) {
        log.info("Retrieving products for stock with id: {}", id);
        List<Product> products = productRepository.findByStockIdStock(id);
        log.debug("Total products in stock with id {}: {}", id, products.size());
        return products;
    }
}