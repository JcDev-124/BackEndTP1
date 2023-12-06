package com.educandoweb.course.controllers;

import com.educandoweb.course.entities.OrderItem;
import com.educandoweb.course.services.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/ordersitem")
public class OrderItemController {
	@Autowired
	private OrderItemService service;
	
	@GetMapping
	public ResponseEntity<List<OrderItem>> findAll(){
		
		List<OrderItem> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<OrderItem> findById(@PathVariable Long id){
		OrderItem obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	public ResponseEntity<OrderItem> saveOrderItem(@RequestBody OrderItem orderItem){
		service.save(orderItem);
		return ResponseEntity.ok(orderItem);
	}
}