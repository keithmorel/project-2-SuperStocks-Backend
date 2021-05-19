package com.revature.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.exception.StockNotFoundException;
import com.revature.exception.UserNotFoundException;
import com.revature.model.Stock;
import com.revature.model.User;
import com.revature.model.UserStock;
import com.revature.model.UserStockKey;

@Repository
public class StockDAO {
	
	private static Logger logger = LoggerFactory.getLogger(StockDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public Stock getStock(int id) {

		Session session = sessionFactory.getCurrentSession();

		return session.createQuery("FROM Stock WHERE id=:id", Stock.class)
				.setParameter("id", id)
				.getSingleResult();

	}

	@Transactional
	public Stock addNewStock(int id, String name, String symbol, String exchange, Double price, String type) {

		Session session = sessionFactory.getCurrentSession();
		User user = session.get(User.class, id);
		
		Stock stock;
		try { // Check if stock exists. If so, use it
			stock = (Stock) session.createQuery("FROM Stock WHERE name=:name AND symbol=:symbol")
					.setParameter("name", name)
					.setParameter("symbol", symbol)
					.getSingleResult();
		} catch (NoResultException e) { // If stock doesn't exist, make it.
			stock = new Stock(0, name, symbol, exchange, price, type, new HashSet<>());
		}
		
		UserStockKey key = new UserStockKey(user.getId(), stock.getId());
		UserStock mapping = new UserStock(key, user, stock);
		session.persist(mapping);
		
		return stock;
		
	}

	@Transactional
	public List<Stock> getAllStocksOfUser(int id) {

		Session session = sessionFactory.getCurrentSession();
		
		User user = session.get(User.class, id);
		
		List<UserStock> mappings = session.createQuery("FROM User_Stock WHERE user=:user", UserStock.class)
				.setParameter("user", user)
				.getResultList();
		
		List<Stock> stocks = new ArrayList<>();
		for (UserStock mapping: mappings) {
			stocks.add(mapping.getStock());
		}
		
		return stocks;
		
	}

	@Transactional
	public Stock updatePrice(int id, Double price) throws StockNotFoundException {

		Session session = sessionFactory.getCurrentSession();
		Stock stock = session.get(Stock.class, id);
		
		if (stock == null) {
			throw new StockNotFoundException("Failed to update stock. Stock not found.");
		}
		if (stock.getPrice().equals(price)) {
			logger.info("New price is the same as the current price. No change made.");
			return stock;
		} else {
			
			String formattedString = String.format("Updating price with new value for stock. Initial value: %s", stock.getPrice());
			logger.info(formattedString);
			
			stock.setPrice(price);
			session.persist(stock);
			
			String formattedStringUpdate = String.format("Updated stock: %s ", stock.getPrice());
			logger.info(formattedStringUpdate);
			
			return stock;
		}
		
	}

	@Transactional
	public void deleteStockFromPortfolio(int userId, int stockId) throws StockNotFoundException, UserNotFoundException {

		Session session = sessionFactory.getCurrentSession();
		User user = session.get(User.class, userId);
		Stock stock = session.get(Stock.class, stockId);
		
		if (stock == null) {
			throw new StockNotFoundException("Failed to update stock. Stock not found.");
		} else if (user == null) {
			throw new UserNotFoundException("Failed to update stock. User not found.");
		}
		
		UserStock mapping = session.createQuery("FROM User_Stock WHERE user=:user AND stock=:stock", UserStock.class)
				.setParameter("user", user)
				.setParameter("stock", stock)
				.getSingleResult();
		
		session.delete(mapping);
		
	}

}
