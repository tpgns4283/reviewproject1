package com.example.reviewproject.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class UserDTO {
	private String username;

	public UserDTO(User user) {
		this.username = user.getUsername();
	}
}
