package com.educandoweb.course.services;

import com.educandoweb.course.entities.User;
import com.educandoweb.course.repositories.UserRepository;
import com.educandoweb.course.services.exceptions.DataBaseException;
import com.educandoweb.course.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
	@Autowired
	private UserRepository repository;

	public List<User> findAll() {
		return repository.findAll();
	}

	public User findById(Long id) {
		if (id == null || id <= 0) {
			throw new IllegalArgumentException("Invalid user ID");
		}

		Optional<User> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Transactional
	public User insert(User obj) {
		if (obj == null) {
			throw new DataBaseException("User object cannot be null");
		}

		if (obj.getId() < 0) {
			throw new DataBaseException("User ID must be null to insert a new user");
		}

		return repository.save(obj);
	}

	@Transactional
	public boolean delete(Long id) {
		try {
			if (id == null || id <= 0) {
				throw new IllegalArgumentException("Invalid user ID");
			}

			repository.deleteById(id);
			return true;
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("User deletion violated database integrity");
		}
	}

	@Transactional
	public User update(Long id, User obj) {
		try {
			if (id == null || id <= 0) {
				throw new IllegalArgumentException("Invalid user ID");
			}

			User entity = repository.getOne(id);
			updateData(entity, obj);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(User entity, User obj) {
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setPhone(obj.getPhone());
	}
}
