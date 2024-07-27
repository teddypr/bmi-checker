DROP TABLE IF EXISTS BMIs;

CREATE TABLE  BMIs(
 id INT unsigned AUTO_INCREMENT,
 name VARCHAR(255) NOT NULL UNIQUE,
 age INT NOT NULL,
 height FLOAT(5,1) NOT NULL,
 weight FLOAT(5,1) NOT NULL,
 PRIMARY KEY(id)
);

INSERT INTO BMIs (name, age, height, weight) VALUES ('タナカ　イチロウ', 20, 171.5, 60.2);
INSERT INTO BMIs (name, age, height, weight) VALUES ('スズキ　ジロウ', 18, 181.0, 88.0);
INSERT INTO BMIs (name, age, height, weight) VALUES ('ムラタ　サブロウ', 26, 167.9, 101.3);

