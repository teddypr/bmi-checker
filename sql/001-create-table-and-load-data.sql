DROP TABLE IF EXISTS BMIs;

CREATE TABLE  BMIs(
 id int unsigned AUTO_INCREMENT,
 name VARCHAR(255) NOT NULL UNIQUE,
 age VARCHAR(255) NOT NULL,
 height VARCHAR(255) NOT NULL,
 weight VARCHAR(255) NOT NULL,
 PRIMARY KEY(id)
);

INSERT INTO BMIs (name, age, height, weight) VALUES ('田中一郎', 20, 171.5, 60.2);
INSERT INTO BMIs (name, age, height, weight) VALUES ('鈴木次郎', 18, 181.0, 88.0);
INSERT INTO BMIs (name, age, height, weight) VALUES ('向井三郎', 26, 167.9, 101.3);
