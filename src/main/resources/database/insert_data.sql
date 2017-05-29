USE payservice;

INSERT INTO regions(region_name) VALUES ('Алматы');
INSERT INTO regions(region_name) VALUES ('Астана');
INSERT INTO regions(region_name) VALUES ('Караганда');
INSERT INTO regions(region_name) VALUES ('Петропавловск');

INSERT INTO wallets(balance) VALUES (10000.0);
INSERT INTO wallets(id) VALUES (id);

INSERT INTO roles(role_name) VALUES ('admin');
INSERT INTO roles(role_name) VALUES ('user');

INSERT INTO categories(category_name) VALUES ('Мобильный');

INSERT INTO providers(provider_name,category_id) VALUES ('Beeline',1);
INSERT INTO providers(provider_name,category_id) VALUES ('Tele2',1);
INSERT INTO providers(provider_name,category_id) VALUES ('Activ',1);
INSERT INTO providers(provider_name,category_id) VALUES ('Kcell',1);
INSERT INTO providers(provider_name,category_id) VALUES ('C1ty',1);

INSERT INTO users(phone_number,user_name,date_of_birth,password,wallet_id,region_id,role_id) VALUES ('+7 (000) 000-00-00','Аdmin','1994-08-30','b0dc5be3a5ac52902229474086a4725',1,3,1);
INSERT INTO users(phone_number,user_name,date_of_birth,password,wallet_id,region_id,role_id) VALUES ('+7 (777) 777-77-77','Василий','1995-09-28','14f1843b1de252184a9d2f73f4e65aa',2,3,2);