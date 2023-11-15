package com.api.blog;

import org.apache.naming.java.javaURLContextFactory;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.Advice.This;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.api.blog.config.AppConstants;
import com.api.blog.entities.Role;
import com.api.blog.repositories.RoleRepo;

import jakarta.validation.constraints.AssertFalse.List;

@SpringBootApplication
public class BloggingApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	
	public static void main(String[] args) {
		SpringApplication.run(BloggingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		try {
			Role adminRole = new Role();
			adminRole.setId(AppConstants.ADMIN_ROLE);
			adminRole.setName(AppConstants.ADMIN_USER);
			
			Role normalRole = new Role();
			normalRole.setId(AppConstants.NORMAL_ROLE);
			normalRole.setName(AppConstants.NORMAL_USER);
			
			
			java.util.List<Role> roles = this.roleRepo.saveAll(java.util.List.of(adminRole, normalRole));
			
			roles.forEach(role->System.out.println(role));
			
			System.out.println(this.passwordEncoder.encode("12345"));
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
