package com.remitlyproject.SwiftCodes.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankRepository extends JpaRepository<Bank,String> {
    List<Bank> findBySwiftCodeStartingWithAndIsHeadquarterFalse(String swiftCodePrefix);
    List<Bank> findByCountryISO2(String countryISO2);


}
