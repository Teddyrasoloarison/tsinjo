-- Création des enums PostgreSQL

CREATE TYPE payment_method_enum AS ENUM (
    'CARD',
    'CASH',
    'MOBILE_MONEY',
    'OTHER'
);

CREATE TYPE payment_state_enum AS ENUM (
    'VERIFYING',
    'SUCCEEDED',
    'FAILED'
);

-- Table donor

CREATE TABLE donor (
                       id VARCHAR(36) PRIMARY KEY,
                       full_name TEXT NOT NULL,
                       email TEXT NOT NULL
);

-- Table beneficiary

CREATE TABLE beneficiary (
                             id VARCHAR(36) PRIMARY KEY,
                             full_name TEXT NOT NULL,
                             email TEXT NOT NULL
);

-- Table payment

CREATE TABLE payment (
                         id VARCHAR(36) PRIMARY KEY,
                         external_id TEXT,
                         state payment_state_enum NOT NULL,
                         amount NUMERIC(10,2) NOT NULL,
                         paymentMethod payment_method_enum NOT NULL,
                         paymentDate TIMESTAMP NOT NULL
);

-- Table donation

CREATE TABLE donation (
                          id VARCHAR(36) PRIMARY KEY,
                          donor_id VARCHAR(36) NOT NULL REFERENCES donor(id) ON DELETE CASCADE,
                          payment_id VARCHAR(36) NOT NULL REFERENCES payment(id) ON DELETE CASCADE,
                          created_at TIMESTAMP NOT NULL DEFAULT now()
);

-- Table help

CREATE TABLE help (
                      id VARCHAR(36) PRIMARY KEY,
                      beneficiary_id VARCHAR(36) NOT NULL REFERENCES beneficiary(id) ON DELETE CASCADE,
                      payment_id VARCHAR(36) NOT NULL REFERENCES payment(id) ON DELETE CASCADE,
                      accident_description TEXT NOT NULL,
                      created_at TIMESTAMP NOT NULL DEFAULT now()
);
