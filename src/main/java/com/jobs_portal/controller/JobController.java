package com.jobs_portal.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobs_portal.entity.Jobs;
import com.jobs_portal.repository.JobRepository;

@RestController
@CrossOrigin
public class JobController {

	@Autowired
	private JobRepository jobRepo;

	// Add Jobs
	@PostMapping("/addJobs")
	public String addJob(@RequestBody Jobs job) {

		jobRepo.save(job);
		return "Job Added Successfully!";
	}

	// pagination
	@GetMapping("/jobs")
	public Page<Jobs> getJobs(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

		PageRequest pageable = PageRequest.of(page, size);

		return jobRepo.findAll(pageable);
	}

	// job search - by keyword (title/company/description) and/or location
	@GetMapping("/jobs/search")
	public Page<Jobs> searchJobs(@RequestParam(defaultValue = "") String keyword,
			@RequestParam(defaultValue = "") String location, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		PageRequest pageable = PageRequest.of(page, size);

		return jobRepo.searchJobs(keyword, location, pageable);
	}

	// get single job (used by application form)
	@GetMapping("/jobs/{id}")
	public ResponseEntity<Jobs> getJob(@PathVariable int id) {

		Optional<Jobs> job = jobRepo.findById(id);

		return job.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	// edit / update job (job management)
	@PutMapping("/jobs/{id}")
	public ResponseEntity<String> updateJob(@PathVariable int id, @RequestBody Jobs job) {

		Optional<Jobs> existing = jobRepo.findById(id);

		if (existing.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Jobs j = existing.get();
		j.setJobTitle(job.getJobTitle());
		j.setCompany(job.getCompany());
		j.setLocation(job.getLocation());
		j.setSalary(job.getSalary());
		j.setImageUrl(job.getImageUrl());
		j.setJobDescription(job.getJobDescription());

		jobRepo.save(j);

		return ResponseEntity.ok("Job Updated Successfully!");
	}

	// delete

	@DeleteMapping("/jobs/{id}")
	public String deleteJob(@PathVariable int id) {
		jobRepo.deleteById(id);
		return "Deleted Sucessfully!";
	}

}
