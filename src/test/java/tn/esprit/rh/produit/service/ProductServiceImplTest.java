package tn.esprit.rh.produit.service;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;
import tn.esprit.devops_project.services.ProductServiceImpl;

public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddProduct_Success() {
        // Arrange
        Product product = new Product();
        product.setTitle("Test Product");

        Stock stock = new Stock();
        stock.setIdStock(1L);

        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));
        when(productRepository.save(product)).thenReturn(product);

        // Act
        Product savedProduct = productService.addProduct(product, 1L);

        // Assert
        assertNotNull(savedProduct);
        assertEquals("Test Product", savedProduct.getTitle());
        verify(stockRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testAddProduct_StockNotFound() {
        // Arrange
        Product product = new Product();
        product.setTitle("Test Product");

        when(stockRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NullPointerException.class, () -> productService.addProduct(product, 1L));
        verify(stockRepository, times(1)).findById(1L);
        verify(productRepository, never()).save(any());
    }

    @Test
    public void testRetrieveProduct_Success() {
        // Arrange
        Product product = new Product();
        product.setIdProduct(1L);
        product.setTitle("Test Product");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Act
        Product retrievedProduct = productService.retrieveProduct(1L);

        // Assert
        assertNotNull(retrievedProduct);
        assertEquals("Test Product", retrievedProduct.getTitle());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void testRetrieveProduct_ProductNotFound() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NullPointerException.class, () -> productService.retrieveProduct(1L));
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void testRetrieveAllProduct() {
        // Arrange
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        products.add(new Product());

        when(productRepository.findAll()).thenReturn(products);

        // Act
        List<Product> result = productService.retreiveAllProduct();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testRetrieveProductByCategory() {
        // Arrange
        ProductCategory category = ProductCategory.ELECTRONICS;
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        when(productRepository.findByCategory(category)).thenReturn(products);

        // Act
        List<Product> result = productService.retrieveProductByCategory(category);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productRepository, times(1)).findByCategory(category);
    }

    @Test
    public void testDeleteProduct() {
        // Act
        productService.deleteProduct(1L);

        // Assert
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testRetrieveProductStock() {
        // Arrange
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        when(productRepository.findByStockIdStock(1L)).thenReturn(products);

        // Act
        List<Product> result = productService.retreiveProductStock(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productRepository, times(1)).findByStockIdStock(1L);
    }
}