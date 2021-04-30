package com.LoginReg.registration.token;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

	private final ConfirmationTokenRepository confirmationTokenRepository;
	
	public void saveConfirmationToken(ConfirmationToken token) {
		confirmationTokenRepository.save(token);
	}
	
	public ConfirmationToken getToken(String token) {
		ConfirmationToken conf=confirmationTokenRepository.findByToken(token).orElseThrow(()-> new IllegalStateException("Token not found !"));
		return conf; 
	}
	
	public ConfirmationToken setConfirmedAt(String token) {
		ConfirmationToken conf=confirmationTokenRepository.findByToken(token).orElseThrow(()-> new IllegalStateException("Token not found !"));
		conf.setConfirmedAt(LocalDateTime.now());
		confirmationTokenRepository.save(conf);
		return conf; 
	}
}
