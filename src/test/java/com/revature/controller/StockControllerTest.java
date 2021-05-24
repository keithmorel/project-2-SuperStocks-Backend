package com.revature.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.revature.exception.AddStockException;
import com.revature.exception.BadParameterException;
import com.revature.exception.StockNotFoundException;
import com.revature.exception.UserNotFoundException;
import com.revature.model.Stock;
import com.revature.service.StockService;
import com.revature.service.UserService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class StockControllerTest {

	MockMvc mockMvc;

	@Mock
	HttpServletRequest request;

	@Mock
	StockService stockService;

	@Mock
	UserService userService;

	@InjectMocks
	StockController sc;

	@BeforeEach
	void setup() throws StockNotFoundException, BadParameterException, AddStockException, UserNotFoundException {
		HttpSession session = new MockHttpSession();

		when(request.getSession(true)).thenReturn(session);

		when(stockService.getStockBySymbol("AAPL"))
				.thenReturn(new Stock(1, "Apple Inc", "AAPL", "NASDAQ", 125.34, "Common Stock", new HashSet<>()));

		when(stockService.addStock(1, "Apple Inc", "AAPL", "NASDAQ", 125.34, "Common Stock"))
				.thenReturn(new Stock(1, "Apple Inc", "AAPL", "NASDAQ", 125.34, "Common Stock", new HashSet<>()));

		List<Stock> stocks = new ArrayList<Stock>();
		stocks.add(new Stock(1, "Apple Inc", "AAPL", "NASDAQ", 125.34, "Common Stock", new HashSet<>()));
		when(stockService.getAllStocks(1)).thenReturn(stocks);

		when(stockService.updateStockPrice(1, 225.52))
				.thenReturn(new Stock(1, "Apple Inc", "AAPL", "NASDAQ", 225.52, "Common Stock", new HashSet<>()));

		this.mockMvc = MockMvcBuilders.standaloneSetup(sc).build();
	}
	
	@Test
	void test() {
	}
}
