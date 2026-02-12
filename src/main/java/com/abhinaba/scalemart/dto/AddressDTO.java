package com.abhinaba.scalemart.dto;

import com.abhinaba.scalemart.model.AddressType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AddressDTO {
    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "Zipcode is required")
    @Pattern(regexp = "^\\d{5,6}$", message = "Zipcode must be 5 or 6 digits")
    private String zipcode;

    @NotNull(message = "Address type is required")
    private AddressType type;

    @JsonProperty("isDefault")
    private boolean isDefault;
}