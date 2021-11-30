package com.ensa.ql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ensa.ql.model.Owner;
import com.ensa.ql.model.Pet;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
	List<Pet> findAllByOwner(Owner owner);
}
