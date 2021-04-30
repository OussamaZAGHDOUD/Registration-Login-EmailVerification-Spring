package com.LoginReg.registration.token;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.LoginReg.applicationUser.AppUser;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String token;
	@Column(nullable = false)
	private LocalDateTime createdAt;
	
	private LocalDateTime expiresAt;
	private LocalDateTime confirmedAt;
	@ManyToOne
	@JoinColumn(
			nullable = false,
			name="app_user_id"
			)
	private AppUser appUser;
	
	public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt,
			AppUser appUser) {
		super();
		this.token = token;
		this.createdAt = createdAt;
		this.expiresAt = expiresAt;
		this.appUser=appUser;
	}
	
	
}
