package com.jobs_portal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jobs_portal.entity.Jobs;

public interface JobRepository extends JpaRepository<Jobs, Integer> {

	@Query("SELECT j FROM Jobs j WHERE "
			+ "(:keyword IS NULL OR :keyword = '' OR LOWER(j.jobTitle) LIKE LOWER(CONCAT('%', :keyword, '%')) "
			+ "OR LOWER(j.company) LIKE LOWER(CONCAT('%', :keyword, '%')) "
			+ "OR LOWER(j.jobDescription) LIKE LOWER(CONCAT('%', :keyword, '%'))) "
			+ "AND (:location IS NULL OR :location = '' OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%')))")
	Page<Jobs> searchJobs(@Param("keyword") String keyword, @Param("location") String location, Pageable pageable);

}
