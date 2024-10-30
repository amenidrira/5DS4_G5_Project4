import tn.esprit.devops_project.services.StockServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.StockRepository;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StockServiceImplTest {

    @InjectMocks
    private StockServiceImpl stockService;

    @Mock
    private StockRepository stockRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addStock() {
        // Arrange
        Stock stock = new Stock();
        stock.setIdStock(1L);
        when(stockRepository.save(stock)).thenReturn(stock);

        // Act
        Stock savedStock = stockService.addStock(stock);

        // Assert
        assertNotNull(savedStock);
        assertEquals(1L, savedStock.getIdStock());
        verify(stockRepository, times(1)).save(stock);
    }

    @Test
    void retrieveStock_existingId() {
        // Arrange
        Stock stock = new Stock();
        stock.setIdStock(1L);
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));

        // Act
        Stock retrievedStock = stockService.retrieveStock(1L);

        // Assert
        assertNotNull(retrievedStock);
        assertEquals(1L, retrievedStock.getIdStock());
        verify(stockRepository, times(1)).findById(1L);
    }

    @Test
    void retrieveStock_nonExistingId() {
        // Arrange
        when(stockRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NullPointerException.class, () -> stockService.retrieveStock(2L));
        verify(stockRepository, times(1)).findById(2L);
    }

    @Test
    void retrieveAllStock() {
        // Arrange
        List<Stock> stocks = List.of(new Stock(), new Stock());
        when(stockRepository.findAll()).thenReturn(stocks);

        // Act
        List<Stock> retrievedStocks = stockService.retrieveAllStock();

        // Assert
        assertNotNull(retrievedStocks);
        assertEquals(2, retrievedStocks.size());
        verify(stockRepository, times(1)).findAll();
    }
}
