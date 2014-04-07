INSERT INTO contact (city, flat, home, street, date_of_birth, email, middle_name, name, surname) VALUES ('City', 'Flat', 'Home', 'Street', '1990-12-31', 'mail@mail.com', 'Name', 'Name', 'Name');
INSERT INTO contact (city, flat, home, street, date_of_birth, email, middle_name, name, surname) VALUES ('City', 'Flat', 'Home', 'Street', '1990-12-31', 'mail@mail.com', 'Name', 'Name', 'Name');
INSERT INTO contact (city, flat, home, street, date_of_birth, email, middle_name, name, surname) VALUES ('City', 'Flat', 'Home', 'Street', '1990-12-31', 'mail@mail.com', 'Name', 'Name', 'Name');
INSERT INTO contact (city, flat, home, street, date_of_birth, email, middle_name, name, surname) VALUES ('City', 'Flat', 'Home', 'Street', '1990-12-31', 'mail@mail.com', 'Name', 'Name', 'Name');
INSERT INTO contact (city, flat, home, street, date_of_birth, email, middle_name, name, surname) VALUES ('City', 'Flat', 'Home', 'Street', '1990-12-31', 'mail@mail.com', 'Name', 'Name', 'Name');

INSERT INTO user (password, role, username, contact_id) VALUES ('1234', 'ADMINISTRATOR', 'admin', '1');
INSERT INTO user (password, role, username, contact_id) VALUES ('1234', 'SUPERVISOR', 'user1', '2');
INSERT INTO user (password, role, username, contact_id) VALUES ('1234', 'ORDER_MANAGER', 'user2', '3');
INSERT INTO user (password, role, username, contact_id) VALUES ('1234', 'SECOND_MANAGER', 'user3', '4');
INSERT INTO user (password, role, username, contact_id) VALUES ('1234', 'COURIER', 'user4', '5');

INSERT INTO orders (cost, date, description, state, customer_id, delivery_manager_id, processing_manager_id, reception_manager_id, recipient_id) VALUES ('150', '1990-12-31', 'no', 'ACCEPTED', '1', '5', '4', '3', '1');
INSERT INTO orders (cost, date, description, state, customer_id, delivery_manager_id, processing_manager_id, reception_manager_id, recipient_id) VALUES ('150', '1990-12-31', 'no', 'ACCEPTED', '1', '5', '4', '3', '1');
INSERT INTO orders (cost, date, description, state, customer_id, delivery_manager_id, processing_manager_id, reception_manager_id, recipient_id) VALUES ('150', '1990-12-31', 'no', 'ACCEPTED', '1', '5', '4', '3', '1');
INSERT INTO orders (cost, date, description, state, customer_id, delivery_manager_id, processing_manager_id, reception_manager_id, recipient_id) VALUES ('150', '1990-12-31', 'no', 'ACCEPTED', '1', '5', '4', '3', '1');
INSERT INTO orders (cost, date, description, state, customer_id, delivery_manager_id, processing_manager_id, reception_manager_id, recipient_id) VALUES ('150', '1990-12-31', 'no', 'ACCEPTED', '1', '5', '4', '3', '1');

INSERT INTO phone (comment, country_code, number, operator_code, type, owner_id) VALUES ('comment', '123', '23', '4445887', 'HOME', '1');
INSERT INTO phone (comment, country_code, number, operator_code, type, owner_id) VALUES ('comment', '123', '23', '4445887', 'HOME', '1');
INSERT INTO phone (comment, country_code, number, operator_code, type, owner_id) VALUES ('comment', '123', '23', '4445887', 'HOME', '1');
INSERT INTO phone (comment, country_code, number, operator_code, type, owner_id) VALUES ('comment', '123', '23', '4445887', 'HOME', '1');
INSERT INTO phone (comment, country_code, number, operator_code, type, owner_id) VALUES ('comment', '123', '23', '4445887', 'HOME', '1');

INSERT INTO order_change (comment, date, new_state, order_id, user_changed_status_id) VALUES ('no', '1990-12-31', 'NEW', '1', '1');
INSERT INTO order_change (comment, date, new_state, order_id, user_changed_status_id) VALUES ('no', '1990-12-31', 'NEW', '1', '1');
INSERT INTO order_change (comment, date, new_state, order_id, user_changed_status_id) VALUES ('no', '1990-12-31', 'NEW', '1', '1');
INSERT INTO order_change (comment, date, new_state, order_id, user_changed_status_id) VALUES ('no', '1990-12-31', 'NEW', '1', '1');
INSERT INTO order_change (comment, date, new_state, order_id, user_changed_status_id) VALUES ('no', '1990-12-31', 'NEW', '1', '1');