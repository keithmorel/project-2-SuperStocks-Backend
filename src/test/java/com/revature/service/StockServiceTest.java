package com.revature.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.revature.dao.StockDAO;
import com.revature.dao.UserDAO;
import com.revature.exception.AddStockException;
import com.revature.exception.BadParameterException;
import com.revature.exception.StockNotFoundException;
import com.revature.exception.UserNotFoundException;
import com.revature.model.Stock;
import com.revature.model.User;
import com.revature.model.UserRole;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class StockServiceTest {

	@Mock
	private StockDAO stockDAO;

	@Mock
	private UserDAO userDAO;

	@InjectMocks
	private StockService stockService;

	@BeforeEach
	void setup() throws StockNotFoundException {

		UserRole role = new UserRole(1, "User");
		User user = new User(1, "username", "password", "test@email.com", "first", "last", role, new HashSet<>());

		when(userDAO.registerNewUser("username", "password", "test@email.com", "first", "last", 1)).thenReturn(user);

		when(stockDAO.addNewStock(1, "Apple Inc", "AAPL", "NASDAQ", 125.34, "Common Stock"))
				.thenReturn(new Stock(1, "Apple Inc", "AAPL", "NASDAQ", 125.34, "Common Stock", new HashSet<>()));

		when(stockDAO.getStock("AAPL"))
				.thenReturn(new Stock(1, "Apple Inc", "AAPL", "NASDAQ", 125.34, "Common Stock", new HashSet<>()));

		List<Stock> stocks = new ArrayList<Stock>();
		stocks.add(new Stock(1, "Apple Inc", "AAPL", "NASDAQ", 125.34, "Common Stock", new HashSet<>()));
		when(stockDAO.getAllStocksOfUser(1)).thenReturn(stocks);

		when(stockDAO.updatePrice(1, 225.52))
				.thenReturn(new Stock(1, "Apple Inc", "AAPL", "NASDAQ", 225.52, "Common Stock", new HashSet<>()));
	}

	@Test
	void test_addStock_success() throws BadParameterException, AddStockException {

		new UserRole(1, "User");
		userDAO.registerNewUser("username", "password", "test@email.com", "first", "last", 1);

		Stock actual = stockService.addStock(1, "Apple Inc", "AAPL", "NASDAQ", 125.34, "Common Stock");
		Stock expected = new Stock(1, "Apple Inc", "AAPL", "NASDAQ", 125.34, "Common Stock", new HashSet<>());

		assertEquals(expected, actual);
	}

	@Test
	void test_getStockBySymbol_success() throws StockNotFoundException {

		Stock actual = stockService.getStockBySymbol("AAPL");
		Stock expected = new Stock(1, "Apple Inc", "AAPL", "NASDAQ", 125.34, "Common Stock", new HashSet<>());

		assertEquals(expected, actual);
	}

	@Test
	void test_getAllStock() {

		List<Stock> actual = stockService.getAllStocks(1);
		List<Stock> expected = new ArrayList<Stock>();
		expected.add(new Stock(1, "Apple Inc", "AAPL", "NASDAQ", 125.34, "Common Stock", new HashSet<>()));

		assertEquals(expected, actual);
	}

	@Test
	void test_updateStockPrice_success() throws StockNotFoundException {
		Stock actual = stockService.updateStockPrice(1, 225.52);
		Stock expected = new Stock(1, "Apple Inc", "AAPL", "NASDAQ", 225.52, "Common Stock", new HashSet<>());

		assertEquals(expected, actual);
	}

	@Test
	void test_deleteStock_success() throws StockNotFoundException, UserNotFoundException {
		stockService.deleteStock(1, 1);
		stockService.getAllStocks(1);

		if (stockService.getAllStocks(1) == null) {
			fail("Stock was not deleted");
		}

	}

}
