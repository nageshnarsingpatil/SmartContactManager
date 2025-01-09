package com.scm.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
@Entity(name="user")
@Table(name="user")
public class User implements UserDetails{



	


	@Id
	private String userId;
	@Column(name="username",nullable=false)
	private String name;
	@Column(unique=true,nullable=false)
	private String email;
	private String password;
	@Column(length=1000)
	private String about;
	@Column(length=1000)
	private String profilePic;
	private String phoneNumber;
	private boolean enabled=false;
	private boolean emailVerified=false;
	private boolean phoneVerified=false;

	
	public User(String userId, String name, String email, String password, String about, String profilePic,
			String phoneNumber, boolean enabled, boolean emailVerified, boolean phoneVerified, Providers provider,
			Providers providerUserId) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.about = about;
		this.profilePic = profilePic;
		this.phoneNumber = phoneNumber;
		this.enabled = enabled;
		this.emailVerified = emailVerified;
		this.phoneVerified = phoneVerified;
		this.provider = provider;
		this.providerUserId = providerUserId;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", email=" + email + ", password=" + password + ", about="
				+ about + ", profilePic=" + profilePic + ", phoneNumber=" + phoneNumber + ", enabled=" + enabled
				+ ", emailVerified=" + emailVerified + ", phoneVerified=" + phoneVerified + ", provider=" + provider
				+ ", providerUserId=" + providerUserId + "]";
	}
	@Enumerated(value=EnumType.STRING)
	private Providers provider=Providers.SELF;

	private Providers providerUserId;

	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	private List<Contact> contacts =new ArrayList<>();


	private String emailToken;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public boolean isPhoneVerified() {
		return phoneVerified;
	}

	public void setPhoneVerified(boolean phoneVerified) {
		this.phoneVerified = phoneVerified;
	}

	public Providers getProvider() {
		return provider;
	}

	public void setProvider(Providers provider) {
		this.provider = provider;
	}

	public Providers getProviderUserId() {
		return providerUserId;
	}

	public void setProviderUserId(Providers providerUserId) {
		this.providerUserId = providerUserId;
	}


	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	@ElementCollection(fetch=FetchType.EAGER)
	private List<String> roleList=new ArrayList<>();
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
	//	Collection<SimpleGrantedAuthority>  roles= roleList.stream().map(role->new SimpleGrantedAuthority(role).collect(Collectors.toList()));
	//	return roles;
		return null;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
	
	@Override
	public String getPassword() {
		return this.password;
	}

	public String getEmailToken() {
		return emailToken;
	}

	public void setEmailToken(String emailToken) {
		this.emailToken = emailToken;
	}


}
