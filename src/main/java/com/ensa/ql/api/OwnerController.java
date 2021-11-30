package com.ensa.ql.api;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ensa.ql.api.dto.OwnerRequest;
import com.ensa.ql.api.dto.OwnerResponse;
import com.ensa.ql.api.dto.PetRequest;
import com.ensa.ql.api.dto.PetResponse;
import com.ensa.ql.services.OwnerService;
import com.ensa.ql.services.PetService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("owners")
public class OwnerController {
	private OwnerService ownerService;
	private PetService petService;

	@GetMapping("/{id}")
	public OwnerResponse getOwnerById(@PathVariable("id") Long id) {
		return ownerService.getOwnerById(id);
	}

	@GetMapping
	public List<OwnerResponse> getOwners() {
		return ownerService.getOwners();
	}

	@PostMapping
	public ResponseEntity<OwnerResponse> createOwner(@RequestBody OwnerRequest ownerRequest) {
		var owner = ownerService.createOwner(ownerRequest);
		return new ResponseEntity<>(owner, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<OwnerResponse> updateOwner(@PathVariable("id") Long id,
			@RequestBody OwnerRequest ownerRequest) {
		var owner = ownerService.updateOwner(id, ownerRequest);
		return new ResponseEntity<>(owner, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteOwner(@PathVariable("id") Long id) {
		ownerService.deleteOwner(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}/pets")
	public List<PetResponse> getPetsByOwnerId(@PathVariable("id") Long id) {
		return petService.getPetsByOwnerId(id);
	}

	@PostMapping("/{id}/pets")
	public ResponseEntity<PetResponse> createPet(@RequestBody PetRequest petRequest, @PathVariable("id") Long ownerId) {
		var pet = petService.createPet(petRequest, ownerId);
		return new ResponseEntity<>(pet, HttpStatus.CREATED);
	}

}
