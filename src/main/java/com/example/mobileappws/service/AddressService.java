package com.example.mobileappws.service;

import java.util.List;

import com.example.mobileappws.shared.dto.AddressDTO;

public interface AddressService {
	List<AddressDTO> getAddresses(String userId);
	AddressDTO getAddress(String addressId);
}
