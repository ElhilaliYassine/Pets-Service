package com.ensa.ql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ensa.ql.model.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

}