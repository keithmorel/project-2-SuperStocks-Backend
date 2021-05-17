package com.revature.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.exception.StockNotFoundException;
import com.revature.exception.UserNotFoundException;
import com.revature.model.Stock;
import com.revature.model.User;
import com.revature.model.User_Stock;
import com.revature.model.User_Stock_Key;

@Repository
public class StockDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public Stock getStock(int id) {

		Session session = sessionFactory.getCurrentSession();

		Stock stock = (Stock) session.createQuery("FROM Stock WHERE id=:id")
				.setParameter("id", id)
				.getSingleResult();

		return stock;

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
			stock = new Stock(0, name, symbol, exchange, price, type, new HashSet<User_Stock>());
		}
		
		User_Stock_Key key = new User_Stock_Key(user.getId(), stock.getId());
		User_Stock mapping = new User_Stock(key, user, stock);
		session.persist(mapping);
		
		return stock;
		
	}

	@Transactional
	public List<Stock> getAllStocksOfUser(int id) {

		Session session = sessionFactory.getCurrentSession();
		
		User user = session.get(User.class, id);
		
		List<User_Stock> mappings = session.createQuery("FROM User_Stock WHERE user=:user", User_Stock.class)
				.setParameter("user", user)
				.getResultList();
		
		List<Stock> stocks = new ArrayList<Stock>();
		for (User_Stock mapping: mappings) {
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
		if (stock.getPrice() == price) {
			System.out.println("New price is the same as the current price. No change made.");
			return stock;
		} else {
			System.out.println("Updating price with new value for stock. Initial value: \n" + stock);
			stock.setPrice(price);
			session.persist(stock);
			System.out.println("Updated stock: " + stock);
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
		
		User_Stock mapping = (User_Stock) session.createQuery("FROM User_Stock WHERE user=:user AND stock=:stock", User_Stock.class)
				.setParameter("user", user)
				.setParameter("stock", stock)
				.getSingleResult();
		
		session.delete(mapping);
		
	}

}
