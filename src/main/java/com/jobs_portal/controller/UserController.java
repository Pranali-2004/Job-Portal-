package com.jobs_portal.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobs_portal.entity.Users;
import com.jobs_portal.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepo;

	@PostMapping("/addUsers")
	public String addUser(@RequestBody Users users) {

		userRepo.save(users);
		return "User registered!";
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> getUser(@RequestBody Users user) {

		Users u = userRepo.findByEmailAndPassword(user.getEmail(), user.getPassword());

		Map<String, Object> resp = new HashMap<>();

		if (u != null) {
			resp.put("message", "Login Successful!");
			resp.put("id", u.getId());
			resp.put("name", u.getName());
			resp.put("email", u.getEmail());
			return ResponseEntity.ok(resp);
		} else {
			resp.put("message", "Invalid Credentials!");
			return ResponseEntity.status(401).body(resp);
		}
	}

	@PostMapping("/adminLogin")
	public String getAdmin(@RequestBody Map<String, String> admin) {

		if ("admin@gmail.com".equals(admin.get("email")) && "admin".equals(admin.get("password"))) {
			return "Login Successful!";
		} else {
			return "Invalid Credentials!";
		}
	}
}
