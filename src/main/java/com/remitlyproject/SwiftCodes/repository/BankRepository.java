package com.remitlyproject.SwiftCodes.repository;

import com.remitlyproject.SwiftCodes.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<Bank,String> {
    List<Bank> findBySwiftCodeStartingWithAndIsHeadquarterFalse(String swiftCodePrefix);
    List<Bank> findByCountryISO2(String countryISO2);
    Optional<Bank> findBySwiftCode(String swiftCode);


}
