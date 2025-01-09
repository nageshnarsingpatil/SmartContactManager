package com.scm.services.impl;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.services.ImageService;

@Service
public class ImageServiceImpl implements ImageService {
	
	private Cloudinary cloudinary;
	
	
	public ImageServiceImpl(Cloudinary cloudinary) {
		super();
		this.cloudinary = cloudinary;
	}
	
	@Override
	public String uploadImage(MultipartFile profileImage) {
	    String filename = UUID.randomUUID().toString();  // Generate a unique filename
	    
	    try {
	        // Convert the MultipartFile to a byte array
	        byte[] data = profileImage.getBytes();
	        
	        // Upload the image to Cloudinary with the generated filename as public_id
	        Map uploadResult = cloudinary.uploader().upload(data, ObjectUtils.asMap(
	                "public_id", filename  // Use the UUID as the unique public_id
	        ));
	        
	        // Extract the URL from the upload result
	        String imageUrl = (String) uploadResult.get("url");
	        
	        return imageUrl;  // Return the URL of the uploaded image
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;  // In case of error, return null
	    }
	}
	/*
	 * @Override public String uploadImage(MultipartFile profileImage) { // TODO
	 * Auto-generated method stub
	 * 
	 * String filename=UUID.randomUUID().toString();
	 * 
	 * try {
	 * 
	 * //in below we can write code for upload the profile pitcture on cloud and it
	 * returns url byte[] data=new byte[profileImage.getInputStream().available()];
	 * 
	 * profileImage.getInputStream().read(data);
	 * 
	 * cloudinary.uploader().upload(data,ObjectUtils.asMap(
	 * "public_id",profileImage.getOriginalFilename()
	 * 
	 * 
	 * ));
	 * 
	 * return this.getUrlFromPublicId(filename);
	 * 
	 * } catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); return null; }
	 * 
	 * 
	 * }
	 */

	@Override
	public String getUrlFromPublicId(String publicId) {
		
		
		
		return cloudinary.url().transformation(new Transformation<>().width(500).height(500).crop("fill")).generate(publicId);
	}
	

}
