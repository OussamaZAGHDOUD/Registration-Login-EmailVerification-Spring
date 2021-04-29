package com.LoginReg.registration;

import org.springframework.stereotype.Service;

import com.LoginReg.applicationUser.AppUser;
import com.LoginReg.applicationUser.AppUserRole;
import com.LoginReg.applicationUser.AppUserService;

import antlr.CharQueue;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
public class RegistrationService {

	private final AppUserService appUserService;
	
	public String register(RegistrationRequest request) {
		
		
		
		return appUserService.singUpUser(new AppUser(request.getFirstName(),request.getLastName(),request.getEmail(),request.getPassword(),AppUserRole.USER));
	}

}
