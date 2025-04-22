package com.remitlyproject.SwiftCodes.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

 // I've added lombok annotations to reduce boilerplate code(such as getters, setters and constructors).

//Data annotation  generates getters and setters for all fields in the Bank class
@Data

// AllArgsConstructor  generates a constructor with all the fields which makes code cleaner and helps with initializing
@AllArgsConstructor
// NoArgsConstructor  generates a default constructor without fields which is required for JPA and Hibernate
@NoArgsConstructor

@Entity
@Table(name="bank_swift_codes")
public class Bank {
    // Column annotation added specially to 'countryISO2' field because in PostgreSQL the column name is different('country_iso2_code').
    // This helps to avoid mapping errors.
    @Column(name="country_iso2_code")
    @NotBlank(message="Country iso2 code cannot be empty it must be two characters(e.g.'DE','PL') ")
    private String countryISO2;

    // Id annotation defines 'swiftCode' as  the primary key. The Column annotation specifies the name of the column in the database('swift_code') and makes sure it's unique.
    @Id
    @Column(name="swift_code" ,unique = true)
    @NotBlank(message = " Swift code cannot be empty")
    private String swiftCode;

    // Other fields represent various attributes of Bank Class
    private String codeType;

    // note: it didnt map properly  and returned null as bankName ,because i've missed column name was 'name'.
    // Added column annotation to  handle possible wrong mappings
    @Column(name="name")
    @NotBlank(message="bankName cannot be empty")
    private String bankName;

    @NotBlank(message="Address cannot be empty!")
    private String address;

    private String townName;

    private String timeZone;

    @NotBlank(message = "Country name cannot be empty.")
    private String countryName;

     // 'isHeadquarter' is boolean field that indicates bank is a hq or branch.
    @NotNull(message="Headquarter status must be specified( insert true for HQ , false for Branches")
    private Boolean isHeadquarter;

}
