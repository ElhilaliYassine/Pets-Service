package com.ensa.ql.api.dto;

import lombok.*;

import java.time.LocalDate;

import com.ensa.ql.model.PetType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetResponse {
	private Long id;
	private String name;
	private LocalDate birthDate;
	private PetType petType;
}