INSERT INTO country (id, name, iso2, iso3, code, created_at, updated_at) VALUES
  ('1', 'United States', 'US', 'USA', '001', now(), now()),
  ('2', 'Germany', 'DE', 'DEU', '049', now(), now()),
  ('3', 'Japan', 'JP', 'JPN', '081', now(), now());

INSERT INTO currency (id, name, code, symbol, created_at, updated_at) VALUES
  ('1', 'US Dollar', 'USD', '-', now(), now()),
  ('2', 'Euro', 'EUR', '-', now(), now()),
  ('3', 'Japanese Yen', 'JPY', '-', now(), now());

INSERT INTO country_currency (country_id, currency_id) VALUES ('1', '1');
INSERT INTO country_currency (country_id, currency_id) VALUES ('2', '2');
INSERT INTO country_currency (country_id, currency_id) VALUES ('3', '3');