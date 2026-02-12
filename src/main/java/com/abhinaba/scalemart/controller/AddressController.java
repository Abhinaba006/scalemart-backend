package com.abhinaba.scalemart.controller;

import com.abhinaba.scalemart.dto.AddressDTO;
import com.abhinaba.scalemart.model.Address;
import com.abhinaba.scalemart.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<List<Address>> getUserAddresses(@PathVariable Long userId) {
        List<Address> addresses = addressService.getAddressesByUserId(userId);
        return ResponseEntity.ok(addresses);
    }

    @PostMapping
    public ResponseEntity<Address> addAddress(@PathVariable Long userId, @Valid @RequestBody AddressDTO addressDTO) {
        Address createdAddress = addressService.addAddress(userId, addressDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAddress);
    }
    
    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long userId, @PathVariable Long addressId) {
        // In a real app, we should check if the address belongs to the user
        addressService.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }
}