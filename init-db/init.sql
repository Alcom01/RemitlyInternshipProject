CREATE TABLE IF NOT EXISTS bank_swift_codes (
    country_iso2_code VARCHAR(255),
    swift_code VARCHAR(255) PRIMARY KEY,
    code_type VARCHAR(255),
    name VARCHAR(255),
    address VARCHAR(255),
    town_name VARCHAR(255),
    country_name VARCHAR(255),
    time_zone VARCHAR(255),
    is_headquarter BOOLEAN DEFAULT FALSE
);

COPY bank_swift_codes(
    country_iso2_code,
    swift_code,
    code_type,
    name,
    address,
    town_name,
    country_name,
    time_zone
)
FROM '/docker-entrypoint-initdb.d/bank_swift_codes.csv'
DELIMITER ','
CSV HEADER;

-- 3. Normalize text fields to UPPERCASE
UPDATE bank_swift_codes
SET country_iso2_code = UPPER(country_iso2_code),
    country_name = UPPER(country_name),
    town_name = UPPER(town_name),
    name = UPPER(name);

-- 4. Set headquarters flag where swift_code ends with 'XXX'
UPDATE bank_swift_codes
SET is_headquarter = TRUE
WHERE RIGHT(swift_code, 3) = 'XXX';
-- 5. Apply CHECK constraints

-- Ensure bank names are uppercase
ALTER TABLE bank_swift_codes
ADD CONSTRAINT bank_swift_codes_name_check
CHECK (name = UPPER(name));

-- Ensure town names are uppercase
ALTER TABLE bank_swift_codes
ADD CONSTRAINT bank_swift_codes_town_name_check
CHECK (town_name = UPPER(town_name));
-- Ensure swift codes match the SWIFT format (11 alphanumeric characters)
ALTER TABLE bank_swift_codes
ADD CONSTRAINT bank_swift_codes_swift_code_check
CHECK (swift_code ~ '^[A-Z0-9]{11}$');

-- Ensure code_type is always 'BIC11'
ALTER TABLE bank_swift_codes
ADD CONSTRAINT bank_swift_codes_code_type_check
CHECK (code_type = 'BIC11');