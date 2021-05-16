package com.revature.service;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.dao.StockDAO;
import com.revature.exception.AddStockException;
import com.revature.exception.BadParameterException;
import com.revature.exception.StockNotFoundException;
import com.revature.model.Stock;

@Service
public class StockService {
	
	@Autowired
	private StockDAO stockDAO;

	@Transactional(rollbackFor = { StockNotFoundException.class })
	public Stock getStockById(int id) throws StockNotFoundException {
		
		try {
			return stockDAO.getStock(id);
		}catch(NoResultException e) {
			throw new StockNotFoundException("Stock does not exist.");
		}
	}

	@Transactional(rollbackFor = { BadParameterException.class, AddStockException.class })
	public Stock addStock(String name, String symbol, String exchange, Double price, String type) throws BadParameterException, AddStockException {

		if (name.trim().equals("") || symbol.trim().equals("") || exchange.trim().equals("") || type.trim().equals("")) {
			throw new BadParameterException("All stock information is required.");
		}
		
		try {
			return stockDAO.addNewStock(name, symbol, exchange, price, type);
		} catch (PersistenceException e) {
			throw new AddStockException("Failed to add stock. That stock is already in your portfolio.");
		}
		
	}

	@Transactional
	public List<Stock> getAllStocks(int id) {
		
		return stockDAO.getAllStocksOfUser(id);
		
	}

	@Transactional(rollbackFor = { StockNotFoundException.class })
	public Stock updateStockPrice(int id, Double price) throws StockNotFoundException {

		return stockDAO.updatePrice(id, price);
		
	}

	@Transactional(rollbackFor = { StockNotFoundException.class })
	public void deleteStock(int id) throws StockNotFoundException {

		stockDAO.deleteStockFromPortfolio(id);
		
	}

}
