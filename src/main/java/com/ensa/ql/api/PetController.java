package com.ensa.ql.api;

import com.ensa.ql.api.dto.OwnerResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ensa.ql.api.dto.PetRequest;
import com.ensa.ql.api.dto.PetResponse;
import com.ensa.ql.services.PetService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("pets")
public class PetController {

    private PetService petService;

    @GetMapping("/{petId}")
    public PetResponse getPetById(@PathVariable("petId") Long petId) {
        return petService.getPetById(petId);
    }

    @GetMapping
    public List<PetResponse> getPets() {
        return petService.getPets();
    }

    @PostMapping
    public ResponseEntity<PetResponse> createPet(@RequestBody PetRequest petRequest, @PathVariable("id") Long ownerId) {
        var pet = petService.createPet(petRequest, ownerId);
        return new ResponseEntity(pet, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetResponse> updatePet(@PathVariable("id") Long id, @RequestBody PetRequest petRequest) {
        var pet = petService.updatePet(id, petRequest);
        return new ResponseEntity(pet, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePet(@PathVariable("id") Long id) {
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }
}
