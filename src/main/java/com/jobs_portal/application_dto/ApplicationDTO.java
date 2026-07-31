package com.jobs_portal.application_dto;

import java.time.LocalDateTime;

public class ApplicationDTO {

	private int applicationId;
	private int jobId;
	private String username;
	private String applicantEmail;
	private String applicantPhone;
	private String coverLetter;
	private String resumeLink;
	private String JobTitle;
	private String company;
	private String location;
	private String status;
	private LocalDateTime appliedDate;

	public ApplicationDTO() {
		super();
	}

	public ApplicationDTO(int applicationId, int jobId, String username, String applicantEmail, String applicantPhone,
			String coverLetter, String resumeLink, String jobTitle, String company, String location, String status,
			LocalDateTime appliedDate) {
		super();
		this.applicationId = applicationId;
		this.jobId = jobId;
		this.username = username;
		this.applicantEmail = applicantEmail;
		this.applicantPhone = applicantPhone;
		this.coverLetter = coverLetter;
		this.resumeLink = resumeLink;
		this.JobTitle = jobTitle;
		this.company = company;
		this.location = location;
		this.status = status;
		this.appliedDate = appliedDate;
	}

	public int getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getApplicantEmail() {
		return applicantEmail;
	}

	public void setApplicantEmail(String applicantEmail) {
		this.applicantEmail = applicantEmail;
	}

	public String getApplicantPhone() {
		return applicantPhone;
	}

	public void setApplicantPhone(String applicantPhone) {
		this.applicantPhone = applicantPhone;
	}

	public String getCoverLetter() {
		return coverLetter;
	}

	public void setCoverLetter(String coverLetter) {
		this.coverLetter = coverLetter;
	}

	public String getResumeLink() {
		return resumeLink;
	}

	public void setResumeLink(String resumeLink) {
		this.resumeLink = resumeLink;
	}

	public String getJobTitle() {
		return JobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.JobTitle = jobTitle;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getAppliedDate() {
		return appliedDate;
	}

	public void setAppliedDate(LocalDateTime appliedDate) {
		this.appliedDate = appliedDate;
	}

}
