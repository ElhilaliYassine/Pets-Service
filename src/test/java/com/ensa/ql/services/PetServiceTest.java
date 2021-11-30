package com.ensa.ql.services;

import com.ensa.ql.api.dto.OwnerRequest;
import com.ensa.ql.api.dto.PetRequest;
import com.ensa.ql.exceptions.NotFoundException;
import com.ensa.ql.model.PetType;
import com.ensa.ql.repositories.PetRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PetServiceTest {

    @Autowired
    private PetService petService;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private OwnerService ownerService;

    @AfterEach
    public void after() {
        petRepository.deleteAll();
    }

    @Test
    public void testGetPetById() {
        var ownerRequest = OwnerRequest.builder()
                .address("31 Spooner Street")
                .city("Quahog")
                .firstName("Peter")
                .lastName("Griffin")
                .telephone("0000000000")
                .build();
        var pet = PetRequest.builder()
                .birthDate(LocalDate.now())
                .name("Brian")
                .petType(PetType.DOG)
                .build();
        var owner = ownerService.createOwner(ownerRequest);
        var persisted = petService.createPet(pet, owner.getId());
        var actual = petService.getPetById(persisted.getId());
        assertEquals(actual.getName(), pet.getName());
    }

    @Test
    public void testGetPets() {
        var ownerRequest = OwnerRequest.builder()
                .address("31 Spooner Street")
                .city("Quahog")
                .firstName("Peter")
                .lastName("Griffin")
                .telephone("0000000000")
                .build();
        var pet = PetRequest.builder()
                .birthDate(LocalDate.now())
                .name("Brian")
                .petType(PetType.DOG)
                .build();
        var owner = ownerService.createOwner(ownerRequest);
        petService.createPet(pet, owner.getId());
        var actual = ownerService.getOwners();
        assertTrue(actual.size() != 0);
    }

    @Test
    public void testCreatePet() {
        var ownerRequest = OwnerRequest.builder()
                .address("31 Spooner Street")
                .city("Quahog")
                .firstName("Peter")
                .lastName("Griffin")
                .telephone("0000000000")
                .build();
        var expectedRequest = PetRequest.builder()
                .birthDate(LocalDate.now())
                .name("Brian")
                .petType(PetType.DOG)
                .build();
        var owner = ownerService.createOwner(ownerRequest);
        var expectedResponse = petService.createPet(expectedRequest, owner.getId());
        var actual = petService.getPetById(expectedResponse.getId());
        assertEquals(expectedRequest.getName(), actual.getName());
    }

    @Test
    public void testUpdatePet() {
        var ownerRequest = OwnerRequest.builder()
                .address("31 Spooner Street")
                .city("Quahog")
                .firstName("Peter")
                .lastName("Griffin")
                .telephone("0000000000")
                .build();
        var pet = PetRequest.builder()
                .birthDate(LocalDate.now())
                .name("Brian")
                .petType(PetType.DOG)
                .build();
        var owner = ownerService.createOwner(ownerRequest);
        var expectedResponse = petService.createPet(pet, owner.getId());
        pet.setName("Snowball");
        petService.updatePet(expectedResponse.getId(), pet);
        assertEquals("Snowball", petService.getPetById(expectedResponse.getId()).getName());
    }

    @Test
    public void testDeletePet() {
        var ownerRequest = OwnerRequest.builder()
                .address("31 Spooner Street")
                .city("Quahog")
                .firstName("Peter")
                .lastName("Griffin")
                .telephone("0000000000")
                .build();
        var pet = PetRequest.builder()
                .birthDate(LocalDate.now())
                .name("Brian")
                .petType(PetType.DOG)
                .build();
        var owner = ownerService.createOwner(ownerRequest);
        var expectedResponse = petService.createPet(pet, owner.getId());
        petService.deletePet(expectedResponse.getId());
        assertThrows(NotFoundException.class, () -> {
            petService.getPetById(expectedResponse.getId());
        });
    }
}
