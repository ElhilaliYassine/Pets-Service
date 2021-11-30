package com.ensa.ql.api;

import com.ensa.ql.api.dto.OwnerRequest;
import com.ensa.ql.api.dto.OwnerResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OwnerControllerTest {
    @Mock
    private OwnerController ownerController;
    @Test
    public void testGetOwnerById() {
        OwnerResponse owner = OwnerResponse.builder().firstName("John").lastName("Doe").build();
        when(ownerController.getOwnerById(1L)).thenReturn(owner);
        assertEquals(owner.getFirstName(), ownerController.getOwnerById(1L).getFirstName());
    }
    @Test
    public void testGetOwners() {
        List<OwnerResponse> owners = new ArrayList<>() {
            {
                add(OwnerResponse.builder().firstName("John").lastName("Doe").build());
            }
        };
        when(ownerController.getOwners()).thenReturn(owners);
        assertEquals(1, ownerController.getOwners().size());
    }
    @Test
    public void testCreateOwner() {
        OwnerRequest owner = OwnerRequest.builder().firstName("John").lastName("Doe").build();
        ownerController.createOwner(owner);
        verify(ownerController, times(1)).createOwner(owner);
    }
    @Test
    public void testUpdateOwner() {
        OwnerRequest owner = OwnerRequest.builder().firstName("John").lastName("Doe").build();
        ownerController.updateOwner(1L, owner);
        verify(ownerController, times(1)).updateOwner(1L,owner);
    }
    @Test
    public void testDeleteOwner() {
        ownerController.deleteOwner(1L);
        verify(ownerController, times(1)).deleteOwner(1L);
    }
}
