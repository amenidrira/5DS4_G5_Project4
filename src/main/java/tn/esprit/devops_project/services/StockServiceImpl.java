package tn.esprit.devops_project.services;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import tn.esprit.devops_project.services.Iservices.IStockService;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.StockRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class StockServiceImpl implements IStockService {

    private static final Logger logger = LogManager.getLogger(StockServiceImpl.class);
    private final StockRepository stockRepository;

    @Override
    public Stock addStock(Stock stock) {
        logger.info("Adding stock: {}", stock);
        Stock savedStock = stockRepository.save(stock);
        logger.debug("Stock saved successfully with ID: {}", savedStock.getIdStock());
        return savedStock;
    }

    @Override
    public Stock retrieveStock(Long id) {
        logger.info("Retrieving stock with ID: {}", id);
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("Stock not found"));
        logger.debug("Stock retrieved: {}", stock);
        return stock;
    }

    @Override
    public List<Stock> retrieveAllStock() {
        logger.info("Retrieving all stocks");
        List<Stock> stocks = stockRepository.findAll();
        logger.debug("Total stocks retrieved: {}", stocks.size());
        return stocks;
    }
}
