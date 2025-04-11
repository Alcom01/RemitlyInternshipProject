package com.remitlyproject.SwiftCodes.model;



import org.springframework.stereotype.Service;

import java.util.*;

// Annotating class as Service
@Service
public class BankService {
    // creating a instance field type of BankRepository
    private final BankRepository bankRepository;

    // Initializing it with constructor in order to utilize BankRepository.
    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

  // Service Method for Endpoint1:
    public Map<String,Object> getBankDetails(String swiftCode){
        //Checking if given SWIFT code exists in our database.
        Bank bank = bankRepository.findById(swiftCode).orElseThrow(() -> new RuntimeException("SWIFT code does not exist."));
         // creating a HashMap that will hold our data according to instructions
        Map<String,Object> responseBody = new HashMap<>();
        responseBody.put("address",bank.getAddress());
        responseBody.put("bankName",bank.getBankName());
        responseBody.put("countryISO2",bank.getCountryISO2());
        responseBody.put("countryName",bank.getCountryName());
        responseBody.put("isHeadquarter",bank.isHeadquarter());
        responseBody.put("swiftCode",bank.getSwiftCode());

        // Checking if  given bank is HQ
        if(bank.isHeadquarter()){
            // Extracting first 8 characthers to determine given swiftcodes is related to branch
            String prefix = swiftCode.substring(0,8);
            // Creating a List of Bank objects that will hold branches related to HQ
            List<Bank> branches = bankRepository.findBySwiftCodeStartingWithAndIsHeadquarterFalse(prefix);

            // Creating a ArrayList of Map(that will hold data about our branches)
            List<Map<String,Object>> branchList = new ArrayList<>();
             // for each that will traverse in branches List
            for(Bank branch : branches){
                Map<String,Object> branchData = new HashMap<>();
                // putting relevant key/value pairs in order.
                branchData.put("address",branch.getAddress());
                branchData.put("bankName",branch.getBankName());
                branchData.put("countryName",branch.getCountryName());
                branchData.put("isHeadquarter",branch.isHeadquarter());
                branchData.put("swiftCode",branch.getSwiftCode());
                // adding branchList to branchData
                branchList.add(branchData);
            }
            // adding branchList to our responseBody
            responseBody.put("branches",branchList);
        }
  // finally returning responseBody
    return responseBody;
    }
// Service Method for Endpoint2:
    public Map<String,Object> getSwiftCodesByCountryISO2(String countryISO2){
        Bank bank = bankRepository.findByCountryISO2(countryISO2)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Country ISO2 code is wrong or not exists."));

        List<Bank> banks = bankRepository.findByCountryISO2(countryISO2);
        // Initially I was using Hashmap although response was corrected it didn't meet the requirements:
        // it was first showing list of  'swiftcodes', then 'countryISO2' and 'countryName' in the end of the page, so I've came up with using LinkedHashmap .
           Map<String, Object> responseBody = new LinkedHashMap<>();

           // Created a List of Map that will hold:" address, bankName, countryISO2,isHeadquarter and swiftCode'
           // In key/value pairs(e.g  address: -> getAddress  bankName: -> getBankName
           List<Map<String,Object>> swiftList = new ArrayList<>();
           for(Bank bankBySwift : banks ){
               Map<String,Object> swifts = new HashMap<>();
               swifts.put("address",bankBySwift.getAddress());
               swifts.put("bankName",bankBySwift.getBankName());
               swifts.put("countryISO2",bankBySwift.getCountryISO2());
               swifts.put("isHeadquarter",bankBySwift.isHeadquarter());
               swifts.put("swiftCode",bankBySwift.getSwiftCode());
               // adding swifts list to responseBody
               swiftList.add(swifts);
           }
           // Note!!: Initially ive used hashmap then I realized it is not considering the order  I put values in the HashMap
          // Then switched LinkedHashMap that ill consider order I 'put' values.

        // putting data according to instructions
        responseBody.put("countryISO2",bank.getCountryISO2());
        responseBody.put("countryName",bank.getCountryName());
        responseBody.put("swiftCodes",swiftList);

 // finally returning responseBody
           return responseBody;
    }
// Service Method for Endpoint3:
    public void  addSwiftCode(Bank bank){
        //Getting SWIFT Code.
        String swiftCode = bank.getSwiftCode();
        boolean headquarter = bank.isHeadquarter();

          //NOTE!!: Right now   im only throwing RuntimeException to test edge cases I'll use Global Exception Handler
        // to send client more meaningful error Response.
          // Condition 1 : If it is set to be headquarter  AND NOT ends with "XXX".
            if ((headquarter) && !(swiftCode.substring(8).equals("XXX"))) {
                throw new RuntimeException("ERROR!,SWIFT CODE DOES NOT ENDS WITH XXX");

            }
            // Condition 2: If it is not set to  be headquarter AND ends with "XXX".
            if (!(headquarter) && (swiftCode.substring(8).equals("XXX"))) {
            throw new RuntimeException("ERROR!, SWIFT CODE ENDS WITH XXX MUST BE DECLARED AS HQ.");

           }
            // If first 2 validation is not evaluated add bank to database.
            else {
                bankRepository.save(bank);
            }
        }




    }









