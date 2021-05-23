package com.revature.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.revature.exception.StockNotFoundException;
import com.revature.exception.UserNotFoundException;
import com.revature.model.Stock;
import com.revature.model.User;

@ExtendWith(SpringExtension.class)
@ContextHierarchy({ @ContextConfiguration("classpath:applicationContext.xml"),
		@ContextConfiguration("classpath:dispatcherContext.xml") })
@WebAppConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
class StockDAOTest {

	@Autowired
	private StockDAO stockDAO;

	@Autowired
	private UserDAO userDAO;

	@Test
	@Transactional
	@Commit
	@Order(0)
	void test_addStock_Sucess() {

		userDAO.addUserRole(0, "Admin");
		userDAO.registerNewUser("user", "password", "test@admin.com", "user", "lastname", 1);

		Stock actual = stockDAO.addNewStock(1, "Apple Inc", "AAPL", "NASDAQ", 125.34, "Common Stock");
		Stock expected = new Stock(1, "Apple Inc", "AAPL", "NASDAQ", 125.34, "Common Stock", new HashSet<>());

		assertEquals(expected, actual);
	}

	@Test
	@Transactional
	@Commit
	@Order(1)
	void test_getStockBySymbol_Sucess() {

		Stock actual = stockDAO.getStock("AAPL");
		Stock expected = new Stock(1, "Apple Inc", "AAPL", "NASDAQ", 125.34, "Common Stock", new HashSet<>());

		assertEquals(expected, actual);
	}

	@Test
	@Transactional
	@Commit
	@Order(2)
	void test_getAllStock_Sucess() {
		User user = userDAO.getUserByUsernameAndPassword("user", "password");

		List<Stock> actual = stockDAO.getAllStocksOfUser(user.getId());
		List<Stock> expected = new ArrayList<Stock>();
		expected.add(new Stock(1, "Apple Inc", "AAPL", "NASDAQ", 125.34, "Common Stock", new HashSet<>()));
		assertEquals(expected, actual);
	}

	@Test
	@Transactional
	@Commit
	@Order(3)
	void test_updatePrice_Sucess() throws StockNotFoundException {

		Stock actual = stockDAO.updatePrice(1, 225.51);
		Stock expected = new Stock(1, "Apple Inc", "AAPL", "NASDAQ", 225.51, "Common Stock", new HashSet<>());

		assertEquals(expected, actual);
	}

	@Test
	@Transactional
	@Commit
	@Order(4)
	void test_deleteStockFromPortfolio_Sucess() throws StockNotFoundException, UserNotFoundException {

		
			stockDAO.deleteStockFromPortfolio(1, 1);
			stockDAO.getAllStocksOfUser(1);
			
			if(stockDAO.getAllStocksOfUser(1) == null) {
				fail("Stock was not deleted");
			}
	}

}
