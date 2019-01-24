package com.iktpreobuka.final_project.util;

import org.dom4j.util.UserDataElement;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.iktpreobuka.final_project.entities.User;

public class Encryption {

	
public static String getPassEncoded(String pass) { 
		
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(); 
		return bCryptPasswordEncoder.encode(pass); 
		}
	
	
	public static void main(String[] args) { 
		System.out.println(getPassEncoded("jasnaJ")); 
		
	
	}
}
