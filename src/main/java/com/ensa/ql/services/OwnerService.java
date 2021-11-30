package com.ensa.ql.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.ensa.ql.api.dto.OwnerRequest;
import com.ensa.ql.api.dto.OwnerResponse;
import com.ensa.ql.exceptions.NotFoundException;
import com.ensa.ql.model.Owner;
import com.ensa.ql.repositories.OwnerRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class OwnerService {
	private OwnerRepository ownerRepository;
	private ModelMapper modelMapper;

	public OwnerResponse getOwnerById(@PathVariable("id") Long id) {
		var owner = ownerRepository.findById(id).orElseThrow(NotFoundException::new);
		return modelMapper.map(owner, OwnerResponse.class);
	}

	public List<OwnerResponse> getOwners() {
		var listOfOwners = ownerRepository.findAll();
		return modelMapper.map(listOfOwners, new TypeToken<List<OwnerResponse>>() {
		}.getType());
	}

	public OwnerResponse createOwner(OwnerRequest ownerRequest) {
		var retrievedOwner = modelMapper.map(ownerRequest, Owner.class);
		var owner = ownerRepository.save(retrievedOwner);
		return modelMapper.map(owner, OwnerResponse.class);
	}

	public OwnerResponse updateOwner(Long id, OwnerRequest ownerRequest) {
		var owner = ownerRepository.findById(id).map(o -> {
			if (ownerRequest.getFirstName() != null)
				o.setFirstName(ownerRequest.getFirstName());
			if (ownerRequest.getLastName() != null)
				o.setLastName(ownerRequest.getLastName());
			if (ownerRequest.getAddress() != null)
				o.setAddress(ownerRequest.getAddress());
			if (ownerRequest.getCity() != null)
				o.setCity(ownerRequest.getCity());
			if (ownerRequest.getTelephone() != null)
				o.setTelephone(ownerRequest.getTelephone());
			return o;
		}).orElseThrow(NotFoundException::new);
		return modelMapper.map(owner, OwnerResponse.class);
	}

	public void deleteOwner(Long id) {
		ownerRepository.deleteById(id);
	}
}
