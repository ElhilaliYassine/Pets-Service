package com.ensa.ql.services;

import com.ensa.ql.api.dto.OwnerRequest;
import com.ensa.ql.exceptions.NotFoundException;
import com.ensa.ql.model.Owner;
import com.ensa.ql.repositories.OwnerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class OwnerServiceTest {

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private OwnerRepository ownerRepository;

    @AfterEach
    public void after() {
        ownerRepository.deleteAll();
    }

    @Test
    public void testGetOwnerById() {
        var expected = Owner.builder()
                .address("31 Spooner Street")
                .city("Quahog")
                .firstName("Peter")
                .lastName("Griffin")
                .telephone("0000000000")
                .pets(new ArrayList<>())
                .build();
        var expectedRequest = OwnerRequest.builder()
                .address("31 Spooner Street")
                .city("Quahog")
                .firstName("Peter")
                .lastName("Griffin")
                .telephone("0000000000")
                .build();

        var expectedResponse = ownerService.createOwner(expectedRequest);
        var actual = ownerService.getOwnerById(expectedResponse.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
    }

    @Test
    public void testGetOwners() {
        var expectedRequest = OwnerRequest.builder()
                .address("31 Spooner Street")
                .city("Quahog")
                .firstName("Peter")
                .lastName("Griffin")
                .telephone("0000000000")
                .build();
        ownerService.createOwner(expectedRequest);
        var actual = ownerService.getOwners();
        assertEquals(1, actual.size());
    }

    @Test
    public void testCreateOwner() {
        var expectedRequest = OwnerRequest.builder()
                .address("31 Spooner Street")
                .city("Quahog")
                .firstName("Peter")
                .lastName("Griffin")
                .telephone("0000000000")
                .build();
        var expectedResponse = ownerService.createOwner(expectedRequest);
        var actual = ownerService.getOwnerById(expectedResponse.getId());
        assertEquals(expectedRequest.getLastName(), actual.getLastName());
    }

    @Test
    public void testUpdateOwner() {
        var expectedRequest = OwnerRequest.builder()
                .address("31 Spooner Street")
                .city("Quahog")
                .firstName("Peter")
                .lastName("Griffin")
                .telephone("0000000000")
                .build();
        var expectedResponse = ownerService.createOwner(expectedRequest);
        expectedRequest.setCity("Springfield");
        ownerService.updateOwner(expectedResponse.getId(), expectedRequest);
        assertEquals("Springfield", expectedRequest.getCity());
    }

    @Test
    public void testDeleteOwner() {
        var expectedRequest = OwnerRequest.builder()
                .address("31 Spooner Street")
                .city("Quahog")
                .firstName("Peter")
                .lastName("Griffin")
                .telephone("0000000000")
                .build();
        var expectedResponse = ownerService.createOwner(expectedRequest);
        ownerService.deleteOwner(expectedResponse.getId());
        assertThrows(NotFoundException.class, () -> {
            ownerService.getOwnerById(expectedResponse.getId());
        });
    }
}
