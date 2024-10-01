package com.castilloreyeskm.voglio.request;

import lombok.Data;

@Data
public class UserAddRequest {
	private String firstName;
    private String lastName;
    private String email;
    private String password;
}
