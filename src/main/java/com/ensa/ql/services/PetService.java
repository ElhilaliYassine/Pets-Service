package com.ensa.ql.services;

import com.ensa.ql.api.dto.OwnerResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import com.ensa.ql.api.dto.PetRequest;
import com.ensa.ql.api.dto.PetResponse;
import com.ensa.ql.exceptions.NotFoundException;
import com.ensa.ql.model.Pet;
import com.ensa.ql.repositories.OwnerRepository;
import com.ensa.ql.repositories.PetRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class PetService {
    private PetRepository petRepository;
    private OwnerRepository ownerRepository;
    private ModelMapper modelMapper;

    public PetResponse getPetById(Long petId) {
        var pet = petRepository.findById(petId).orElseThrow(() -> {
            throw new NotFoundException("Pet ID is not found");
        });
        return modelMapper.map(pet, PetResponse.class);
    }

    public List<PetResponse> getPets() {
        var listOfPets = petRepository.findAll();
        return modelMapper.map(listOfPets, new TypeToken<List<PetResponse>>() {
        }.getType());
    }

    public List<PetResponse> getPetsByOwnerId(Long id) {
        var owner = ownerRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Owner Id is not found");
        });
        var listOfPets = petRepository.findAllByOwner(owner);
        return modelMapper.map(listOfPets, new TypeToken<List<PetResponse>>() {
        }.getType());
    }

    public PetResponse createPet(PetRequest petRequest, Long ownerId) {
        if (ownerId == null)
            throw new NotFoundException("Owner ID is not provided");
        var owner = ownerRepository.findById(ownerId).orElseThrow(() -> {
            throw new NotFoundException("Owner ID is not found");
        });
        ownerRepository.findById(ownerId);
        var pet = modelMapper.map(petRequest, Pet.class);
        pet.setOwner(owner);
        petRepository.save(pet);
        return modelMapper.map(pet, PetResponse.class);
    }

    public PetResponse updatePet(Long petId, PetRequest PetRequest) {
        var pet = petRepository.findById(petId).map(o -> {
            if (PetRequest.getName() != null)
                o.setName(PetRequest.getName());
            if (PetRequest.getBirthDate() != null)
                o.setBirthDate(PetRequest.getBirthDate());
            if (PetRequest.getPetType() != null)
                o.setPetType(PetRequest.getPetType());
            return o;
        }).orElseThrow(NotFoundException::new);
        return modelMapper.map(pet, PetResponse.class);
    }

    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }
}