package com.abhinaba.scalemart.repository;

import com.abhinaba.scalemart.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUsersId(Long userId);

    @Modifying
    @Query("UPDATE Address a SET a.isDefault = false WHERE a.users.id = :userId")
    void unsetDefaultAddressesForUser(@Param("userId") Long userId);
}