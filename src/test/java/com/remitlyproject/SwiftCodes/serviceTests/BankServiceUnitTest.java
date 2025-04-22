package com.remitlyproject.SwiftCodes.serviceTests;

import com.remitlyproject.SwiftCodes.exceptions.InvalidCountryISO2Exception;
import com.remitlyproject.SwiftCodes.exceptions.InvalidSwiftCodeException;
import com.remitlyproject.SwiftCodes.entity.Bank;
import com.remitlyproject.SwiftCodes.repository.BankRepository;
import com.remitlyproject.SwiftCodes.service.BankService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankServiceUnitTest {

    // creating a mock object of BankRepository.
    @Mock
    private BankRepository bankRepository;

    // injecting bankRepository mock object to bankService in order use methods comes with bankRepository.
    @InjectMocks
    private BankService bankService;

    // Creating a Bank Entity object called exampleBank
    private Bank exampleBank;

    // Adding @BeforeEach annotation to set up our entity before every test.
    @BeforeEach
    void setUp(){
        exampleBank = new Bank();
        exampleBank.setSwiftCode("KJKSZPJJXXX");
        exampleBank.setIsHeadquarter(true);
        exampleBank.setAddress("ELM STREET");
        exampleBank.setBankName("LOS SANTOS BANK");
        exampleBank.setCountryISO2("US");
        exampleBank.setCountryName("UNITED STATES");
    }
   // Unit test1 for getBankDetails method
    // Scenario : when given swiftCode is HeadQuarter method should return  branch info along with hq info
    @Test
    void getBankDetails_shouldReturnBankInfoAndBranches_whenisHqTrue(){
        // when swiftCode exits in our database then returning bank object
        when(bankRepository.findById("KJKSZPJJXXX")).thenReturn(Optional.of(exampleBank));
        // when first 8 characters of other swiftCodes in our database is same as given swiftCode returning list of branches.
        when(bankRepository.
                findBySwiftCodeStartingWithAndIsHeadquarterFalse("KJKSZPJJ"))
                .thenReturn(new ArrayList<>());
       // Creating a Map that will hold  data about our branches.
        Map<String,Object> result = bankService.getBankDetails("KJKSZPJJXXX");

        // asserting expected bank name is equal to actual name.
        assertEquals("LOS SANTOS BANK",result.get("bankName"));
        // asserting isHeadquarter TRUE since given swiftCode is headquarter.
        assertTrue((Boolean)result.get("isHeadquarter"));
    }
   // Unit test2 for getBankDetails method
    // Scenario: When given swiftCode is invalid or does not exist in our database.
    @Test
    void getBankDetails_shouldThrowException_whenInvalidSwift(){
        // when given swiftCode invalid(or does not exist in our database), cannot return bank object
        when(bankRepository.findById("INVALID")).thenReturn(Optional.empty());
        // asserting   method will throw InvalidSwiftCodeException since given swiftCode is Invalid.
        assertThrows(InvalidSwiftCodeException.class,() ->bankService.getBankDetails("INVALID"));
    }

    // Unit test3 for getSwiftCodesByCountryISO2
    // Scenario: when given countryISO2 exists in our database
    @Test
    void getSwiftCodesByCountryISO2_shouldReturnList(){
        // when given countryISO2 exists, return list of banks.
        when(bankRepository.findByCountryISO2("US")).thenReturn(List.of(exampleBank));

        // Map that will hold data about all banks in that country.
        Map<String,Object> result = bankService.getSwiftCodesByCountryISO2("US");
        // asserting expected countryISO2 is equal to  result's countryISO2.
        assertEquals("US",result.get("countryISO2"));

    }
     //Unit test4 for getSwiftCodesByCountryISO2
    // Scenario: when given countryISO2  does not exist in our database
    @Test
    void getSwiftCodesByCountryISO2_shouldThrowException_whenIso2NotFound(){
        // when given countryISO2 is not found , return empty list.
        when(bankRepository.findByCountryISO2("ZZ")).thenReturn(Collections.emptyList());
        // asserting method will throw InvalidCountryISO2Exception since given countryISO2 does not exist in our database.
        assertThrows(InvalidCountryISO2Exception.class, () -> bankService.getSwiftCodesByCountryISO2("ZZ"));

    }
    //Unit test5 for addSwiftCode method
    // Scenario: add swiftCode to database when given swiftCode is valid and not headquarter.
    @Test
    void addSwiftCode_shouldAddSwiftCode_whenSwiftValidAndNotHq(){
        // setting isHeadquarter to false
        exampleBank.setIsHeadquarter(false);
        // since it is a branch it should end with anything but 'XXX'
        exampleBank.setSwiftCode("KJKSZPJJ345");

        // when given swiftCode   not already exists, return empty optional object that will ensure given swiftCode does not exist.
        when(bankRepository.findBySwiftCode("KJKSZPJJ345")).thenReturn(Optional.empty());

        // after validating given swiftCode  does not exist in database, call addSwiftCode method to add swiftCode to database.
        bankService.addSwiftCode(exampleBank);

        // finally verify bank added to database by calling bankRepository method.
        verify(bankRepository).save(exampleBank);
    }
    // Unit test6 for addSwiftCode method
    // Scenario : add swiftCode to database when given swiftCode is valid and headquarter
    @Test
    void addSwiftCode_shouldAddSwiftCode_whenSwiftValidAndHq(){
        // setting isHeadquarter to true
        exampleBank.setIsHeadquarter(true);
        // since it is a headquarters  it should end with 'XXX'
        exampleBank.setSwiftCode("KJKSZPJJXXX");

        //when given swiftCode   not already exists, return empty optional object
        when(bankRepository.findBySwiftCode("KJKSZPJJXXX")).thenReturn(Optional.empty());

        //after validating given swiftCode  does not exist in database, call addSwiftCode method to add swiftCode to database.
        bankService.addSwiftCode(exampleBank);
         // finally verify bank added to the database by calling bankRepository method.
        verify(bankRepository).save(exampleBank);
    }


    // Unit test7 for addSwiftCode method
    // Scenario: don't add swiftCode to database when it is already exists.
    @Test
    void addSwiftCode_shouldThrowException_whenDuplicate(){
        // Indicating given swiftCode already exists.
        exampleBank.setSwiftCode("ALREADY EXISTS");
        // when given swiftCode already exists, return bank object with given swiftCode
        when(bankRepository.findBySwiftCode("ALREADY EXISTS")).thenReturn(Optional.of(exampleBank));
        // throw InvalidSwiftCodeException when given swiftCode already exists.
        assertThrows(InvalidSwiftCodeException.class,() -> bankService.addSwiftCode(exampleBank));


    }
    // Unit test8 for addSwiftCode method
    // Scenario: don't add swiftCode to database if it is not exactly 11 characters long.
    @Test
    void addSwiftCode_shouldThrowException_whenLengthNot11(){
        exampleBank.setSwiftCode("NOT EXACTLY 11 CHARACTERS");
        // throw  InvalidSwiftCodeException
        assertThrows(InvalidSwiftCodeException.class,() -> bankService.addSwiftCode(exampleBank));


    }
    // Unit test9 for addSwiftCode method
    // Scenario: don't add swiftCode to database if it is ending  with 'XXX' and declared as branch.
    @Test
    void addSwiftCode_shouldThrowException_whenBranchEndsWithXXX(){
        // swift code ends with XXX
        exampleBank.setSwiftCode("KJKSZPJJXXX");
        // isHeadquarter set to false
        exampleBank.setIsHeadquarter(false);
        // throw InvalidSwiftCodeException that indicates swiftCode ends with XXX should be declared as headquarter.
        assertThrows(InvalidSwiftCodeException.class, () -> bankService.addSwiftCode(exampleBank));
    }

    // Unit test10 for addSwiftCode method
    // Scenario: don't add SwiftCode to database if it is not and ending with 'XXX' and declared as headquarter.

    @Test
    void addSwiftCode_shouldThrowException_whenHqNotEndsWithXXX(){
        // swiftCode does not end with 'XXX'
        exampleBank.setSwiftCode("KJKSZPJJ345");
        // declaring as headquarter
        exampleBank.setIsHeadquarter(true);
        // throw InvalidSwiftCodeException that indicates swiftCode does not end with XXX should be branch.
        assertThrows(InvalidSwiftCodeException.class, () -> bankService.addSwiftCode(exampleBank));
    }

  // Unit test11 for deleteSwiftCode method
    // Scenario: delete bank if given swiftCode exist in our database.
    @Test
    void deleteSwiftCode_shouldDelete_whenSwiftExists(){
        //when given swiftCode exits in our database, return bank object that matches to given swiftCode.
        when(bankRepository.findById("KJKSZPJJXXX")).thenReturn(Optional.of(exampleBank));

        // call  deleteSwiftCode method to delete existing bank object with matching swiftCode.
        bankService.deleteSwiftCode("KJKSZPJJXXX");

         // finally verify it by calling method deleteById.
        verify(bankRepository).deleteById("KJKSZPJJXXX");
    }

    // Unit test1 for deleteSwiftCode method
    // Scenario: throw exception if given swiftCode does not exist in our database.
    @Test
    void deleteSwiftCode_shouldThrowException_whenSwiftNotExists(){
        // when given swiftCode not exists, in our database return empty Optional object that indicates given swiftCode does  not exist in our database.
        when(bankRepository.findById("NOT EXISTS")).thenReturn(Optional.empty());
        // throw InvalidSwiftCodeException  since given swiftCode not exists, nothing to delete.
        assertThrows(InvalidSwiftCodeException.class,() -> bankService.deleteSwiftCode("NOT EXISTS"));
    }
}