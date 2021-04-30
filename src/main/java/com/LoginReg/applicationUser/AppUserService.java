package com.LoginReg.applicationUser;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.LoginReg.registration.token.ConfirmationToken;
import com.LoginReg.registration.token.ConfirmationTokenService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

	private final static String USER_NOT_FOUND_MESSAGE = "user with this email %s not found !";
	private final AppUserRepository appUserRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final ConfirmationTokenService confirmationService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return appUserRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, email)));
	}

	public String singUpUser(AppUser appUser) {
		
		boolean userExists = appUserRepository.findByEmail(appUser.getEmail()).isPresent();
		if (userExists)
			if(appUser.getEnabled())
			throw new IllegalStateException("User exist and email already confirmed");
		// we can add here some code to verify if the user exists we and it try to change his information 
		// we can delete all Tokens with the UserId and save the new informations of this user and send a verication Token again
		String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
		appUser.setPassword(encodedPassword);
		appUserRepository.save(appUser);
		//  save the confirmation toekn in the DB and send it  :
		String token = UUID.randomUUID().toString();
		ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(15), appUser);
		confirmationService.saveConfirmationToken(confirmationToken);
		
		// send Email :
		 
		return token;
	}
	
	
	
	public void enableAppUser(String email) {
	AppUser appUser=	appUserRepository.findByEmail(email).orElseThrow(()->new IllegalStateException("User not found !"));
	appUser.setEnabled(true);
	appUserRepository.save(appUser);
	}

}
