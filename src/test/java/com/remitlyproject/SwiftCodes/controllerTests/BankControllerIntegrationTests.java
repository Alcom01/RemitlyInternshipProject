package com.remitlyproject.SwiftCodes.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.remitlyproject.SwiftCodes.entity.Bank;
import com.remitlyproject.SwiftCodes.repository.BankRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
 class BankControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private  BankRepository bankRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Bank testBank;


    @BeforeEach
    void setUp(){
        testBank = new Bank();
        testBank.setSwiftCode("KJKSZPJJXXX");
        testBank.setIsHeadquarter(true);
        testBank.setAddress("ELM STREET");
        testBank.setBankName("LOS SANTOS BANK");
        testBank.setCountryISO2("US");
        testBank.setCountryName("UNITED STATES");

        bankRepository.save(testBank);

    }

    @Test
    void getBankDetails_ShouldReturnBankDetails() throws  Exception{
        mockMvc.perform(get("/v1/swift-codes/{swiftCode}","KJKSZPJJXXX"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bankName").value("LOS SANTOS BANK"))
                .andExpect(jsonPath("$.isHeadquarter").value(true));

    }
    @Test
    void getSwiftCodesByCountryISO2_shouldReturnBanksInCountry() throws Exception {
        mockMvc.perform(get("/v1/swift-codes/country/{countryISO2}", "US"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.countryISO2").value("US"))
                .andExpect(jsonPath("$.swiftCodes[0].swiftCode").value("KJKSZPJJXXX"));
    }

    @Test
    void addSwiftCode_shouldCreateNewBank() throws Exception {
        Bank newBank = new Bank();
        newBank.setSwiftCode("KJKSZPJJ999");
        newBank.setIsHeadquarter(false);
        newBank.setAddress("GROVE STREET");
        newBank.setBankName("GROVE BANK");
        newBank.setCountryISO2("US");
        newBank.setCountryName("UNITED STATES");

        mockMvc.perform(post("/v1/swift-codes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBank)))
                .andExpect(status().isOk());

        Optional<Bank> saved = bankRepository.findBySwiftCode("KJKSZPJJ999");
        assert(saved.isPresent());
    }

    @Test
    void deleteSwiftCode_shouldRemoveBank() throws Exception {
        mockMvc.perform(delete("/v1/swift-codes/{swiftCode}", "KJKSZPJJXXX"))
                .andExpect(status().isOk());

        Optional<Bank> deleted = bankRepository.findBySwiftCode("KJKSZPJJXXX");
        assert(deleted.isEmpty());
    }
}



