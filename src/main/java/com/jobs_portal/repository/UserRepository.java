package com.jobs_portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobs_portal.entity.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {

	Users findByEmailAndPassword(String email, String password);

}
