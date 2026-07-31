package com.jobs_portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobs_portal.entity.Applications;

public interface ApplicationRepository extends JpaRepository<Applications, Integer> {

	boolean existsByUserIdAndJobId(int userId, int jobId);

	List<Applications> findByUserId(int userId);

	List<Applications> findByJobId(int jobId);

}
