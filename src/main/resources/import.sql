-- Inserção de usuários
INSERT INTO tb_user (name, email, password) VALUES ('Alex', 'alex@gmail.com', '$2a$10$JFESwIzg.lO9rqEoxcxDE.y98/z3RUr50ViIY1wpc1swbAWb6p2N');
INSERT INTO tb_user (name, email, password) VALUES ('Maria', 'maria@gmail.com', '$2a$10$JFESwIzg.lO9rqEoxcxDE.y98/z3RUr50ViIY1wpc1swbAWb6p2N');

-- Inserção de produtos
INSERT INTO tb_product (name) VALUES ('TV');
INSERT INTO tb_product (name) VALUES ('Computer');

-- Inserção de roles (perfis de usuário)
INSERT INTO tb_role (authority) VALUES ('ROLE_OPERATOR');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

-- Associação entre usuários e seus roles
INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);
