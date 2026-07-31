package com.jobs_portal.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.jobs_portal.application_dto.ApplicationDTO;
import com.jobs_portal.entity.Applications;
import com.jobs_portal.entity.Jobs;
import com.jobs_portal.entity.Users;
import com.jobs_portal.repository.ApplicationRepository;
import com.jobs_portal.repository.JobRepository;
import com.jobs_portal.repository.UserRepository;

@RestController
@CrossOrigin
public class ApplicationController {

	@Autowired
	private ApplicationRepository appRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private JobRepository jobRepo;

	// APPLY - full application form (name, email, phone, cover letter, resume link)
	@PostMapping("/apply")
	public ResponseEntity<String> apply(@RequestBody Applications application) {

		boolean exist = appRepo.existsByUserIdAndJobId(application.getUserId(), application.getJobId());

		if (exist) {
			return ResponseEntity.badRequest().body("You have already applied to this job!");
		}

		if (!jobRepo.existsById(application.getJobId())) {
			return ResponseEntity.badRequest().body("Job not found!");
		}

		application.setApplicationId(0);
		application.setStatus("Applied");

		appRepo.save(application);

		return ResponseEntity.ok("Applied Successfully!");
	}

	// ADMIN: all raw applications
	@GetMapping("/application")
	public List<Applications> getAll() {
		return appRepo.findAll();
	}

	// ADMIN: all applications with job + applicant details (job management)
	@GetMapping("/applications/details")
	public List<ApplicationDTO> getDetails() {

		List<Applications> list = appRepo.findAll();

		return toDTOList(list);
	}

	// USER: my applications (job management - user side)
	@GetMapping("/applications/user/{userId}")
	public List<ApplicationDTO> getByUser(@PathVariable int userId) {

		List<Applications> list = appRepo.findByUserId(userId);

		return toDTOList(list);
	}

	// ADMIN: applications for a specific job
	@GetMapping("/applications/job/{jobId}")
	public List<ApplicationDTO> getByJob(@PathVariable int jobId) {

		List<Applications> list = appRepo.findByJobId(jobId);

		return toDTOList(list);
	}

	// ADMIN: update application status (job management)
	@PutMapping("/applications/{id}/status")
	public ResponseEntity<String> updateStatus(@PathVariable int id, @RequestParam String status) {

		Optional<Applications> existing = appRepo.findById(id);

		if (existing.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Applications a = existing.get();
		a.setStatus(status);
		appRepo.save(a);

		return ResponseEntity.ok("Status Updated to " + status);
	}

	// USER: withdraw application (job management - user side)
	@DeleteMapping("/applications/{id}")
	public ResponseEntity<String> withdraw(@PathVariable int id) {

		if (!appRepo.existsById(id)) {
			return ResponseEntity.notFound().build();
		}

		appRepo.deleteById(id);

		return ResponseEntity.ok("Application Withdrawn!");
	}

	private List<ApplicationDTO> toDTOList(List<Applications> list) {

		List<ApplicationDTO> result = new ArrayList<>();

		for (Applications a : list) {

			String name = a.getApplicantName();
			Optional<Users> user = userRepo.findById(a.getUserId());
			if ((name == null || name.isBlank()) && user.isPresent()) {
				name = user.get().getName();
			}

			Optional<Jobs> job = jobRepo.findById(a.getJobId());
			String jobTitle = job.map(Jobs::getJobTitle).orElse("Job Removed");
			String company = job.map(Jobs::getCompany).orElse("-");
			String location = job.map(Jobs::getLocation).orElse("-");

			result.add(new ApplicationDTO(a.getApplicationId(), a.getJobId(), name, a.getApplicantEmail(),
					a.getApplicantPhone(), a.getCoverLetter(), a.getResumeLink(), jobTitle, company, location,
					a.getStatus(), a.getAppliedDate()));
		}
		return result;
	}
}
