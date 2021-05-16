package com.revature.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.exception.StockNotFoundException;
import com.revature.model.Stock;

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
	public Stock addNewStock(String name, String symbol, String exchange, Double price, String type) {

		Session session = sessionFactory.getCurrentSession();
		
		Stock stock = new Stock(0, name, symbol, exchange, price, type);
		session.persist(stock);
		
		return stock;
		
	}

	@Transactional
	public List<Stock> getAllStocksOfUser(int id) {

		Session session = sessionFactory.getCurrentSession();
		
		List<Stock> stocks = session.createQuery("FROM Stock", Stock.class)
				.getResultList();
		
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
	public void deleteStockFromPortfolio(int id) throws StockNotFoundException {

		Session session = sessionFactory.getCurrentSession();
		Stock stock = session.get(Stock.class, id);
		
		if (stock == null) {
			throw new StockNotFoundException("Failed to update stock. Stock not found.");
		}
		
		session.delete(stock);
		
	}

}
