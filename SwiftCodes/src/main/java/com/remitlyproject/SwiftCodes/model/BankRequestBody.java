package com.remitlyproject.SwiftCodes.model;


import lombok.Data;

// BankRequestBody class to match request structure of given Instructions.
// Body for request ENDPOINT 3
@Data
public class BankRequestBody {
    private  String address;
    private String bankName;
    private String countryISO2;
    private String countryName;
    private boolean isHeadquarter;
    private  String swiftCode;

}
