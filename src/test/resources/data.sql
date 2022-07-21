-- setting testing user
INSERT INTO app_user (username, email, password, fullname) VALUES ('test_user', 'user@test.com', 'pswd', 'User');
INSERT INTO app_role (role_name) VALUES ('USER'), ('ADMIN');
INSERT INTO user_role (user_id, role_id) VALUES (1, 1);

-- setting currency
INSERT INTO app_currency (currency_code) VALUES ('RUB');