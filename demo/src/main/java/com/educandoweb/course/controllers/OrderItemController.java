package com.educandoweb.course.controllers;

import com.educandoweb.course.entities.OrderItem;
import com.educandoweb.course.services.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/ordersitem")
public class OrderItemController {
	@Autowired
	private OrderItemService service;

	@GetMapping
	public ResponseEntity<List<OrderItem>> findAll() {
		try {
			List<OrderItem> list = service.findAll();
			return ResponseEntity.ok().body(list);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<OrderItem> findById(@PathVariable Long id) {
		try {
			if (id == null || id <= 0) {
				return ResponseEntity.badRequest().build();
			}

			OrderItem obj = service.findById(id);

			if (obj == null) {
				return ResponseEntity.notFound().build();
			}

			return ResponseEntity.ok().body(obj);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping
	public ResponseEntity<OrderItem> saveOrderItem(@RequestBody OrderItem orderItem) {
		try {
			// Adicione aqui as validações necessárias para o objeto OrderItem antes de salvá-lo

			service.save(orderItem);
			return ResponseEntity.ok(orderItem);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
