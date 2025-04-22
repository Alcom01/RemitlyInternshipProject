package com.remitlyproject.SwiftCodes.entity;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// BankRequestBody class to match request structure of given Instructions.
// Body for request ENDPOINT 3
@Data
public class BankRequestBody {
    @NotBlank(message="Address cannot be empty!")
    private  String address;

    @NotBlank(message="bankName cannot be empty")
    private String bankName;

    @NotBlank(message="Country iso2 code cannot be empty it must be two characters(e.g.'DE','PL') ")
    private String countryISO2;

    @NotBlank(message = "Country name cannot be empty.")
    private String countryName;

    @NotNull(message="Headquarter status must be specified( insert true for HQ , false for Branches")
    private Boolean isHeadquarter;

    @NotBlank(message = "Swift code cannot be empty")
    private  String swiftCode;

}
