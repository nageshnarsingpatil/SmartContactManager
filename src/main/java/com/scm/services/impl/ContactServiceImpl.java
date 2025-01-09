package com.scm.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.helper.ResourceNotFoundException;
import com.scm.repositories.ContactRepo;
import com.scm.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService{

	@Autowired
	private ContactRepo contactRepo;
	
	@Override
	public Contact save(Contact contact) {
		String contactId=UUID.randomUUID().toString();
		contact.setId(contactId);
		return contactRepo.save(contact);
	}

	@Override
	public Contact update(Contact contact) {
		
		var contactOld=  contactRepo.findById(contact.getId()).orElseThrow(()->new ResourceNotFoundException("Contact Not Found"));
		
		contactOld.setName(contact.getName());
		contactOld.setEmail(contact.getEmail());
		contactOld.setPhoneNumber(contact.getPhoneNumber());
		contactOld.setAddress(contact.getAddress());
		contactOld.setDescription(contact.getDescription());
		contactOld.setFavorite(contact.isFavorite());
		contactOld.setLinkedInLink(contact.getLinkedInLink());
		contactOld.setWebsiteLink(contact.getWebsiteLink());
		contactOld.setPicture(contact.getPicture());
		contactOld.setCloudinaryImagePublicId(contact.getCloudinaryImagePublicId());
		
		return contactRepo.save(contactOld);
	}

	@Override
	public List<Contact> getAll() {
		// TODO Auto-generated method stub
		return contactRepo.findAll();
	}

	@Override
	public Contact getById(String id) {
		// TODO Auto-generated method stub
		return contactRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("contact not found with given id"+id)) ;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		var contact=contactRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("contact not found with given id"+id)) ;
		contactRepo.delete(contact);
	}

//	@Override
//	public List<Contact> search(String name, String email, String phoneNumber) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public List<Contact> getByUserId(String userId) {
		
		return contactRepo.findByUserId(userId);
	}

	@Override
	public Page<Contact> getByUser(User user,int page,int size, String sortBy,String direction) {
		// TODO Auto-generated method stub
		Sort sort=direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		
		
		var pageaable=PageRequest.of(page, size,sort);
		return contactRepo.findByUser(user,pageaable);
	}

	@Override
	public Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order,User user) {
		
		
		Sort sort=order.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		var pageable=PageRequest.of(page, size,sort);
		return contactRepo.findByUserAndNameContaining(user,nameKeyword,pageable );		
		
	}

	@Override
	public Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order,User user) {
		Sort sort=order.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		var pageable=PageRequest.of(page, size,sort);
		return contactRepo.findByUserAndEmailContaining(user,emailKeyword,pageable );
	}

	@Override
	public Page<Contact> searchByPhone(String phoneKeyword, int size, int page, String sortBy, String order,User user) {
		Sort sort=order.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		var pageable=PageRequest.of(page, size,sort);
		return contactRepo.findByUserAndPhoneNumberContaining(user,phoneKeyword,pageable );
	}

	
}
