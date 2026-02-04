package com.abhinaba.scalemart.service;

import com.abhinaba.scalemart.dto.AddressDTO;
import com.abhinaba.scalemart.model.Address;
import com.abhinaba.scalemart.model.Users;
import com.abhinaba.scalemart.repository.AddressRepository;
import com.abhinaba.scalemart.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    public List<Address> getAddressesByUserId(Long userId) {
        return addressRepository.findByUsersId(userId);
    }

    @Transactional
    public Address addAddress(Long userId, AddressDTO addressDTO) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        // If this is set as default, unset other default addresses for this user efficiently
        if (addressDTO.isDefault()) {
            addressRepository.unsetDefaultAddressesForUser(userId);
        }

        Address address = new Address();
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setCountry(addressDTO.getCountry());
        address.setZipcode(addressDTO.getZipcode());
        address.setType(addressDTO.getType());
        address.setDefault(addressDTO.isDefault());
        address.setUsers(user);

        return addressRepository.save(address);
    }
    
    @Transactional
    public void deleteAddress(Long addressId) {
        if (!addressRepository.existsById(addressId)) {
            throw new EntityNotFoundException("Address not found with id: " + addressId);
        }
        addressRepository.deleteById(addressId);
    }
}