package com.abhinaba.scalemart.service;

import com.abhinaba.scalemart.dto.AddressDTO;
import com.abhinaba.scalemart.dto.UserDTO;
import com.abhinaba.scalemart.model.Address;
import com.abhinaba.scalemart.model.Users;
import com.abhinaba.scalemart.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Users registerUser(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already taken!");
        }

        Users user = new Users();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());

        if (userDTO.getAddresses() != null && !userDTO.getAddresses().isEmpty()) {
            List<Address> addresses = new ArrayList<>();
            for (AddressDTO addressDTO : userDTO.getAddresses()) {
                Address address = new Address();
                address.setCity(addressDTO.getCity());
                address.setState(addressDTO.getState());
                address.setCountry(addressDTO.getCountry());
                address.setZipcode(addressDTO.getZipcode());
                address.setType(addressDTO.getType());
                address.setDefault(addressDTO.isDefault());
                address.setUsers(user); // Set the bi-directional relationship
                addresses.add(address);
            }
            user.setAddresses(addresses);
        }

        return userRepository.save(user);
    }

    public Users getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
    }

    public Users getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));
    }
}