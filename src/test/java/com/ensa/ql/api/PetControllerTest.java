package com.ensa.ql.api;

import com.ensa.ql.api.dto.PetRequest;
import com.ensa.ql.api.dto.PetResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PetControllerTest {
    @Mock
    private PetController petController;
    @Test
    public void testGetPetById() {
        PetResponse pet = PetResponse.builder().name("Brian").build();
        when(petController.getPetById(1L)).thenReturn(pet);
        assertEquals(pet.getName(), petController.getPetById(1L).getName());
    }
    @Test
        public void testGetPets() {
        List<PetResponse> pets = new ArrayList<>() {
            {
                add(PetResponse.builder().name("Brian").build());
            }
        };
        when(petController.getPets()).thenReturn(pets);
        assertEquals(1, petController.getPets().size());
    }
    @Test
    public void testCreatePet() {
        PetRequest pet = PetRequest.builder().name("Brian").build();
        petController.createPet(pet, 1l);
        verify(petController, times(1)).createPet(pet, 1L);
    }
    @Test
    public void testUpdatePet() {
        PetRequest pet = PetRequest.builder().name("Brian").build();
        petController.updatePet(1L, pet);
        verify(petController, times(1)).updatePet(1L, pet);
    }
    @Test
    public void testDeletePet() {
        petController.deletePet(1L);
        verify(petController, times(1)).deletePet(1L);
    }
    
}
