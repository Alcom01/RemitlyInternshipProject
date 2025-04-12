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

---

> More to come. This journal helps track the journey and reflect on progress step-by-step.
