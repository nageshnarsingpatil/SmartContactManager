package com.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import com.scm.validators.ValidFile;

import jakarta.validation.constraints.NotBlank;

public class ContactForm {
	
	@NotBlank(message="Name field is not blank.")
	private String name;
	private String email;
	private String phoneNumber;
	private String address;
	private String description;
	private boolean favorite;
	private String websiteLink;
	private String linkedInLink;
	
	//we can create the annotation for file validate 
	//size
	//resolution
	//@ValidFile(message="invalid image")
	private MultipartFile profileImage;
	
	private String pitcture;
	
	
	
	public ContactForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ContactForm(String name, String email, String phoneNumber, String address, String description,
			boolean favorite, String websiteLink, String linkedInLink, MultipartFile profileImage) {
		super();
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.description = description;
		this.favorite = favorite;
		this.websiteLink = websiteLink;
		this.linkedInLink = linkedInLink;
		this.profileImage = profileImage;
	}

	@Override
	public String toString() {
		return "ContactForm [name=" + name + ", email=" + email + ", phoneNumber=" + phoneNumber + ", address="
				+ address + ", description=" + description + ", favorite=" + favorite + ", websiteLink=" + websiteLink
				+ ", linkedInLink=" + linkedInLink + ", profileImage=" + profileImage + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isFavorite() {
		return favorite;
	}
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	public String getWebsiteLink() {
		return websiteLink;
	}
	public void setWebsiteLink(String websiteLink) {
		this.websiteLink = websiteLink;
	}
	public String getLinkedInLink() {
		return linkedInLink;
	}
	public void setLinkedInLink(String linkedInLink) {
		this.linkedInLink = linkedInLink;
	}
	public MultipartFile getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(MultipartFile profileImage) {
		this.profileImage = profileImage;
	}

	public String getPitcture() {
		return pitcture;
	}

	public void setPitcture(String pitcture) {
		this.pitcture = pitcture;
	}
	
	

}
