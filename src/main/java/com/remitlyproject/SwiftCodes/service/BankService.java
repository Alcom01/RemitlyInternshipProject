package com.remitlyproject.SwiftCodes.service;



import com.remitlyproject.SwiftCodes.repository.BankRepository;
import com.remitlyproject.SwiftCodes.exceptions.InvalidCountryISO2Exception;
import com.remitlyproject.SwiftCodes.exceptions.InvalidSwiftCodeException;
import com.remitlyproject.SwiftCodes.entity.Bank;
import org.springframework.stereotype.Service;

import java.util.*;

// Annotating class as Service
@Service
public class BankService {
    // creating an instance field type of BankRepository
    private final BankRepository bankRepository;

    // Initializing it with constructor in order to utilize BankRepository.
    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    // Service Method for Endpoint1:
    public Map<String, Object> getBankDetails(String swiftCode) {
        //Checking if given SWIFT code exists in our database.
        Bank bank = bankRepository.findById(swiftCode).orElseThrow(() -> new InvalidSwiftCodeException("SWIFT code does not exist."));
        // creating a HashMap that will hold our data according to instructions
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("address", bank.getAddress());
        responseBody.put("bankName", bank.getBankName());
        responseBody.put("countryISO2", bank.getCountryISO2());
        responseBody.put("countryName", bank.getCountryName());
        responseBody.put("isHeadquarter", bank.getIsHeadquarter());
        responseBody.put("swiftCode", bank.getSwiftCode());

        // Checking if  given bank is HQ
        if (bank.getIsHeadquarter()) {
            // Extracting first 8 characters to determine given SWIFT code is related to branch
            // if first 8 characters of branch is same as hq then it is one of the hq branch.
            String prefix = swiftCode.substring(0, 8);
            // Creating a List of Bank objects that will hold branches related to HQ
            List<Bank> branches = bankRepository.findBySwiftCodeStartingWithAndIsHeadquarterFalse(prefix);

            // Creating a ArrayList of Map String -> Object(that will hold data about our branches)
            List<Map<String, Object>> branchList = new ArrayList<>();
            // for each that will traverse in branches List
            for (Bank branch : branches) {
                Map<String, Object> branchData = new HashMap<>();
                // putting relevant key/value pairs in order.
                branchData.put("address", branch.getAddress());
                branchData.put("bankName", branch.getBankName());
                branchData.put("countryName", branch.getCountryName());
                branchData.put("isHeadquarter", branch.getIsHeadquarter());
                branchData.put("swiftCode", branch.getSwiftCode());
                // adding branchList to branchData
                branchList.add(branchData);
            }
            // adding branchList to our responseBody
            responseBody.put("branches", branchList);
        }
        // finally returning responseBody
        return responseBody;
    }

    // Service Method for Endpoint2:
    public Map<String, Object> getSwiftCodesByCountryISO2(String countryISO2) {
        Bank bank = bankRepository.findByCountryISO2(countryISO2)
                .stream()
                .findFirst()
                .orElseThrow(() -> new InvalidCountryISO2Exception("Country ISO2 code is wrong or not exists."));

        List<Bank> banks = bankRepository.findByCountryISO2(countryISO2);
        // Initially I was using Hashmap although response was corrected it didn't meet the requirements:
        // it was first showing list of  'swiftcodes', then 'countryISO2' and 'countryName' in the end of the page, so I've came up with using LinkedHashmap .
        Map<String, Object> responseBody = new LinkedHashMap<>();

        // Created a List of Map that will hold: " address, bankName, countryISO2,isHeadquarter and swiftCode'"
        // In key/value pairs(e.g.  address: -> getAddress  bankName: -> getBankName
        List<Map<String, Object>> swiftList = new ArrayList<>();
        for (Bank bankBySwift : banks) {
            Map<String, Object> swifts = new HashMap<>();
            swifts.put("address", bankBySwift.getAddress());
            swifts.put("bankName", bankBySwift.getBankName());
            swifts.put("countryISO2", bankBySwift.getCountryISO2());
            swifts.put("isHeadquarter", bankBySwift.getIsHeadquarter());
            swifts.put("swiftCode", bankBySwift.getSwiftCode());
            // adding swifts list to responseBody
            swiftList.add(swifts);
        }
        // Note!!: Initially ive used hashmap then I realized it is not considering the order  I put values in the HashMap
        // Then switched LinkedHashMap that will consider order I 'put' values.

        // putting data according to instructions
        responseBody.put("countryISO2", bank.getCountryISO2());
        responseBody.put("countryName", bank.getCountryName());
        responseBody.put("swiftCodes", swiftList);

        // finally returning responseBody
        return responseBody;
    }

    // Service Method for Endpoint3:
    public void addSwiftCode(Bank bank) {


        //Getting SWIFT Code.
        String swiftCode = bank.getSwiftCode();
        Boolean headquarter = bank.getIsHeadquarter();


        //Checking if entered SWIFT Code exists in our database.
        Optional<Bank> isExistingBank = bankRepository.findBySwiftCode(swiftCode);

        if (isExistingBank.isPresent()) {
            throw new InvalidSwiftCodeException("SWIFT Code already exists.");
        }


        // Condition 1 : SWIFT Code must be exact 11 characters since we are using BIC11 code type.
        if (swiftCode.length() != 11) {
            throw new InvalidSwiftCodeException("ERROR!,Please enter exactly 11 characters for SWIFT CODE.");
        }
        // Condition 1.a : If it is set to be headquarter  AND NOT ends with "XXX".
        if ((headquarter) && !(swiftCode.substring(8).equals("XXX"))) {
            throw new InvalidSwiftCodeException("ERROR!,SWIFT CODE DOES NOT ENDS WITH XXX");

        }
        // Condition 1.b: If it is not set to  be headquarter AND ends with "XXX".
        if (!(headquarter) && (swiftCode.substring(8).equals("XXX"))) {
            throw new InvalidSwiftCodeException("ERROR!, SWIFT CODE ENDS WITH XXX MUST BE DECLARED AS HQ.");

        }
        // If entered SWIFT CODE 11 characters long and  meets the  requirements, add  bank to database.

        bankRepository.save(bank);
    }


    //Service Method for Endpoint4:
    public void deleteSwiftCode(String swiftCode) {
        Optional<Bank> isExistingBank = bankRepository.findById(swiftCode);

        // Checking if bank by given entry exists.
        if (isExistingBank.isEmpty()) {
            throw new InvalidSwiftCodeException("SWIFT Code does not exists.");
        } else {
            bankRepository.deleteById(swiftCode);
        }

    }
}












