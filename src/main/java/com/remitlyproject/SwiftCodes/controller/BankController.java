package com.remitlyproject.SwiftCodes.controller;

import com.remitlyproject.SwiftCodes.entity.Bank;
import com.remitlyproject.SwiftCodes.entity.BankRequestBody;
import com.remitlyproject.SwiftCodes.service.BankService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
// Annotating class as REST Controller which combines functionality of @Controller + @ResponseBody to simplify creation of web services
//Will automatically convert  return value to JSON.
@RestController
@RequestMapping(path="/v1/swift-codes")
public class BankController {
    private final BankService bankService;

    @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }
    // END POINT 1
    // Purpose: Getting bank by  SWIFT code and response depends on given swift code is headquarter or branch.
    // Given SWIFT code is 'Headquarter' it'll return JSON containing relevant info and branches  that  related to headquarters.
    // Given SWIFT code is  'Branch' it'll return JSON containing info.
    @GetMapping("/{swiftCode}")
    public Map<String,Object> getBankDetails(@PathVariable  @Valid String swiftCode){
        return bankService.getBankDetails(swiftCode);
    }
    // END POINT 2
    //Return JSON  by 'CountryISO2Code(E.G 'PL','MC', 'CL)' that list banks info of given country.
    @GetMapping("/country/{countryISO2}")
    public Map<String,Object> getSwiftCodesByCountryISO2(@PathVariable @Valid String countryISO2){
        return bankService.getSwiftCodesByCountryISO2(countryISO2);
    }
    // END POINT 3
    // Adds new Bank entry to database
    @PostMapping
    // Created a BankRequest class that will correspond to Request Structure that is given in Instructions.
    public ResponseEntity<String> addSwiftCode(@RequestBody @Valid BankRequestBody bankRequestBody){
        // Creating a bank object to save Incoming Request.
        Bank bank = new Bank();
        bank.setAddress(bankRequestBody.getAddress());
        bank.setBankName(bankRequestBody.getBankName());
        bank.setCountryISO2(bankRequestBody.getCountryISO2());
        bank.setCountryName(bankRequestBody.getCountryName());
        bank.setIsHeadquarter(bankRequestBody.getIsHeadquarter());
        bank.setSwiftCode(bankRequestBody.getSwiftCode());
        bank.setCodeType("BIC11"); // In our Database only 11 character SWIFT codes allowed so code type will be always "BIC11"

        bankService.addSwiftCode(bank);

        // Returning a http response modified with appropriate success message and HTTPStatus.
        return new ResponseEntity<>("Swift Code added successfully.", HttpStatus.OK);
    }



    // END POINT 4;
    // deletes existing bank from database by swiftCode
    @DeleteMapping("/{swiftCode}")
    public ResponseEntity<String> deleteSwiftCode(@PathVariable @Valid String swiftCode){
        // deleting bank that matches given swiftCode code by client.
        bankService.deleteSwiftCode(swiftCode);
        // Returning a HTTP response modified with appropriate success message and HTTPStatus.
        return new ResponseEntity<>("SWIFT Code deleted successfully.",HttpStatus.OK);

    }


}
