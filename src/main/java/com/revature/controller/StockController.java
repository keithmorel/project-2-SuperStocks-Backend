package com.revature.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.revature.annotation.LoggedInOnly;
import com.revature.exception.AddStockException;
import com.revature.exception.BadParameterException;
import com.revature.exception.StockNotFoundException;
import com.revature.model.Stock;
import com.revature.model.User;
import com.revature.service.StockService;
import com.revature.template.MessageTemplate;
import com.revature.template.StockTemplate;

import jakarta.validation.Valid;

@Controller
public class StockController {

	@Autowired
	private StockService stockService;
	
	@Autowired
	HttpServletRequest request;

	@GetMapping(path = "stock/{id}")
	@LoggedInOnly
	public ResponseEntity<Stock> getStockById(@PathVariable("id") int id) throws StockNotFoundException {

		Stock stock = stockService.getStockById(id);

		return ResponseEntity.status(200).body(stock);

	}
	
	@PostMapping(path = "stock")
	@LoggedInOnly
	public ResponseEntity<Object> addStock(@RequestBody @Valid StockTemplate stockTemplate) throws BadParameterException, AddStockException {

		HttpSession session = request.getSession(true);
		User loggedIn = (User) session.getAttribute("loggedInUser");
		
		Stock stock = stockService.addStock(loggedIn.getId(), stockTemplate.getName(), stockTemplate.getSymbol(), stockTemplate.getExchange(),
				stockTemplate.getPrice(), stockTemplate.getType());

		return ResponseEntity.status(201).body(stock);

	}

	@GetMapping(path = "stock")
	@LoggedInOnly
	public ResponseEntity<Object> getAllStocks() {
		
		HttpSession session = request.getSession(true);
		User loggedIn = (User) session.getAttribute("loggedInUser");

		List<Stock> stocks = stockService.getAllStocks(loggedIn.getId());

		return ResponseEntity.status(200).body(stocks);

	}
	
	@PatchMapping(path = "stock/{id}")
	@LoggedInOnly
	public ResponseEntity<Object> updateStockPrice(@RequestBody @Valid StockTemplate stockTemplate, @PathVariable("id") int id) throws StockNotFoundException {

		Stock stock = stockService.updateStockPrice(id, stockTemplate.getPrice());

		return ResponseEntity.status(201).body(stock);

	}
	
	@DeleteMapping(path = "stock/{id}")
	@LoggedInOnly
	public ResponseEntity<Object> deleteStock(@PathVariable("id") int id) throws StockNotFoundException {

		stockService.deleteStock(id);

		return ResponseEntity.status(201).body(new MessageTemplate("Successfully deleted the stock from your portfolio."));

	}

}
