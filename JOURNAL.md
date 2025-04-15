# 📓 Remitly Internship Project – Development Journal

## 🗓️ April 11, 2025
- ✅ **Started** working on Remitly internship take-home project.
- ✅ **Read and analyzed** the full task description and judgment criteria.
- ✅ **Parsed** SWIFT codes CSV file locally.
- ✅ **Designed PostgreSQL table** `bank_swift_codes` with constraints:
  - `swift_code`: checked for 11 uppercase letters/numbers using regex.
  - `code_type`: fixed value `'BIC11'`.
  - Ensured all names, country codes, and time zones are stored in **uppercase**.
- ✅ **Inserted** 1061 entries successfully using `\COPY` (resolved constraint issues).
- 🧠 *Learned*: How to troubleshoot constraint errors and use PostgreSQL's `\COPY` for large CSV imports.

---

## 🗓️ April 12, 2025
- ✅ **Created Spring Boot project** with required dependencies:
  - Spring Web
  - Spring Data JPA
  - PostgreSQL Driver
  - Lombok
- ✅ **Defined `Bank` entity** and mapped it to `bank_swift_codes` table.
- ✅ Used **Lombok annotations** (`@Data`, `@AllArgsConstructor`, `@NoArgsConstructor`) to reduce boilerplate.
- ✅ Marked `swiftCode` as the **primary key** in both PostgreSQL and Java.
- ✅ Added `is_headquarter` column via SQL and updated values based on `XXX` suffix.
- ✅ **Uploaded screenshots** documenting each milestone to `images/` directory in the repo.
- ✅ Started this **journal** to track daily development and thoughts.
- 🧠 *Learned*: Entity mapping with JPA, smart use of Lombok, and importance of keeping database and code structure in sync.


## 🗓️ April 13, 2025
- ✅ **Defined  `BankRepository` interface** that extends `JpaRepository` interface which helped to perfom `CRUD` operations.
- ✅ **Created a `BankService ` class** that utilizes `BankRepository` to perform listed opearations below by methods:
  - `getBankDetails()` method to retrieve data about bank's headquarter and their related branches.
  - `getSwiftCodesByCountryISO2()` method to retrieve given country's bank details such as address swiftcodes , headquarter.
  - `addSwiftCode()` method to add banks to our database handles few edge cases.


## 🗓️ April 14, 2025
- ✅ **Created a class `BankController`** which is a `RestController Class` that will handle following endpoints:
  - **Endpoint 1:** `GET: /v1/swift-codes/{swift-code}`
  - **Endpoint 2:** `GET: /v1/swift-codes/country/{countryISO2code}`
  - **Endpoint 3:** `POST: /v1/swift-codes`
- ✅  **Added `Swagger` to dependencies** to visualize the process.



## 🗓️ April 15, 2025(IN PROGRESS)
- **Planning to handle `Exceptions`** by using `Global Exception Handler`
- **Thinking about possible edge cases,errors and solutions**
  
---

> More to come. This journal helps track the journey and reflect on progress step-by-step.
