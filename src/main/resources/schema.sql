CREATE SCHEMA IF NOT EXISTS banking;

-- DROP TABLE IF EXISTS banking.transaction;
-- DROP TABLE IF EXISTS banking.account_balance;
-- DROP TABLE IF EXISTS banking.account;
-- DROP TABLE IF EXISTS banking.customer;

-- Create types IF NOT EXISTS (Postgres doesn't support CREATE TYPE IF NOT EXISTS ...)
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'currency_enum') THEN
        CREATE TYPE CURRENCY_ENUM AS ENUM ('EUR', 'SEK', 'GBP', 'USD');
    END IF;
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'direction_enum') THEN
        CREATE TYPE DIRECTION_ENUM AS ENUM ('IN', 'OUT');
    END IF;
END$$;

CREATE TABLE IF NOT EXISTS banking.customer(
    id SERIAL PRIMARY KEY,
    name VARCHAR (255) NOT NULL,
    email VARCHAR (255) UNIQUE NOT NULL
    -- could have other information here, but simplifying...
);

CREATE TABLE IF NOT EXISTS banking.account(
    id SERIAL PRIMARY KEY, -- to simplify things, account number and id will be the same
    fk_customer_id INT NOT NULL,
    country VARCHAR (255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT banking_account_fk_customer_id FOREIGN KEY (fk_customer_id) REFERENCES banking.customer(id)
);

CREATE TABLE IF NOT EXISTS banking.account_balance(
    fk_account_id INT NOT NULL,
    currency_type CURRENCY_ENUM NOT NULL,
    available_amount NUMERIC (19, 2) NOT NULL DEFAULT 0.00,
    CONSTRAINT banking_account_balance_fk_account_id FOREIGN KEY (fk_account_id) REFERENCES banking.account(id) ON DELETE CASCADE,
    PRIMARY KEY (fk_account_id, currency_type)
);

CREATE TABLE IF NOT EXISTS banking.transaction(
    id SERIAL PRIMARY KEY,
    fk_account_id INT NOT NULL,
    currency_type CURRENCY_ENUM NOT NULL,
    amount DECIMAL(19, 2) NOT NULL CHECK (amount > 0), -- enforcing to always be greater than 0, since we have direction that controls if is addition or subtraction.
    direction DIRECTION_ENUM,
    description TEXT NOT NULL CHECK (description <> '' AND description !~ '^\s*$'), -- if is an empty string or only composed by blank spaces, then fail insertion.
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT banking_transaction_fk_account_balance FOREIGN KEY (fk_account_id, currency_type) REFERENCES banking.account_balance(fk_account_id, currency_type)
);

CREATE OR REPLACE FUNCTION update_balance_trigger_before_insert_transaction() 
RETURNS TRIGGER AS $$
BEGIN
  -- Check if account and currency exist
  IF NOT EXISTS (SELECT 1 FROM banking.account_balance WHERE fk_account_id = NEW.fk_account_id AND currency_type = NEW.currency_type) THEN
    RAISE EXCEPTION 'Account or currency does not exist';
  -- Check for sufficient balance for outgoing transactions
  ELSIF NEW.direction = 'OUT' AND EXISTS (SELECT 1 FROM banking.account_balance WHERE fk_account_id = NEW.fk_account_id AND currency_type = NEW.currency_type AND available_amount < NEW.amount) THEN
    RAISE EXCEPTION 'Insufficient balance for transaction';
  END IF;

  -- Update balance based on transaction direction
  IF NEW.direction = 'IN' THEN
    UPDATE banking.account_balance
    SET available_amount = available_amount + NEW.amount
    WHERE fk_account_id = NEW.fk_account_id AND currency_type = NEW.currency_type;
  ELSIF NEW.direction = 'OUT' THEN
    UPDATE banking.account_balance
    SET available_amount = available_amount - NEW.amount
    WHERE fk_account_id = NEW.fk_account_id AND currency_type = NEW.currency_type;
  END IF;

  RETURN NEW;
END$$ language plpgsql;

DROP TRIGGER IF EXISTS update_balance_trigger
  ON banking.transaction;
CREATE TRIGGER update_balance_trigger
BEFORE INSERT ON banking.transaction
FOR EACH ROW
EXECUTE FUNCTION update_balance_trigger_before_insert_transaction();
