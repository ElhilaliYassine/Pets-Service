package com.ensa.ql.api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OwnerRequest {
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String telephone;
}