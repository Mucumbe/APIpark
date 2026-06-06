INSERT INTO usuarios (id, password, role, username)
VALUES (100, '$2a$12$TldWBJqYq4xzwc8TyQCZM.sDWj5RtBs3CzR78rYQKN9NwuINGgPzi', 'ROLE_CLIENTE', 'bland@kk.co');
INSERT INTO usuarios (id, password, role, username)
VALUES (101, '$2a$12$TldWBJqYq4xzwc8TyQCZM.sDWj5RtBs3CzR78rYQKN9NwuINGgPzi', 'ROLE_ADMIN', 'boas@kk.co');
INSERT INTO usuarios (id, password, role, username)
VALUES (102, '$2a$12$TldWBJqYq4xzwc8TyQCZM.sDWj5RtBs3CzR78rYQKN9NwuINGgPzi', 'ROLE_CLIENTE', 'nada@kk.co');
INSERT INTO usuarios (id, password, role, username)
VALUES (103, '$2a$12$7BwiJfrRp8KE0BLqVem3..djaR6UBZ5tEMMmwKiA/OO7OQAjfkpNC', 'ROLE_CLIENTE', 'kapa@kk.co');

INSERT INTO clientes ( nome, nuit, id_usuario)
VALUES ( 'Blandino junior', '1334567', 100);

INSERT INTO clientes ( nome, nuit, id_usuario)
VALUES ( 'Mauro Mondlane', '2468101', 102);