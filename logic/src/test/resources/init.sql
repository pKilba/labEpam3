INSERT INTO
    certificates (id,name, description, price,create_date,last_update_date,
                  duration) VALUES (1,'test1','description',1.1,'2022-01-25 18:00:16','2022-01-25 18:00:16',5);

INSERT INTO
    certificates (id,name, description, price,create_date,last_update_date,
                  duration) VALUES (2,'test2','description',1.1,'2022-01-25 18:00:16','2022-01-25 18:00:16',5);
INSERT INTO tags (id, name) VALUES (1,'test1');
INSERT INTO tags (id, name) VALUES (2,'test2');
INSERT INTO users (id,name,spent_money) values (1,'user',1.1);
INSERT INTO users (id,name,spent_money) values (2,'user',1.1);