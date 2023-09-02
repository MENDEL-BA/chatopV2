CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255) UNIQUE NOT NULL,
  name VARCHAR(255),
  password VARCHAR(255),
  created_at DATETIME,
  updated_at DATETIME
);

CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);
CREATE TABLE IF NOT EXISTS users_roles (
  user_id INT,
  role_id INT,
  PRIMARY KEY (user_id, role_id),
  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (role_id) REFERENCES roles (id)
);
CREATE TABLE IF NOT EXISTS rentals (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  surface NUMERIC,
  price NUMERIC,
  picture VARCHAR(255),
  description VARCHAR(2000),
  owner_id INT NOT NULL,
  created_at DATETIME,
  updated_at DATETIME,
  FOREIGN KEY (owner_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS messages (
  id INT AUTO_INCREMENT PRIMARY KEY,
  rental_id INT,
  user_id INT,
  message VARCHAR(2000),
  created_at DATETIME,
  updated_at DATETIME,
  FOREIGN KEY (rental_id) REFERENCES rentals (id),
  FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS rental_user_messages (
  rental_id INT,
  user_id INT,
  message_id INT,
  PRIMARY KEY (rental_id, user_id, message_id),
  FOREIGN KEY (rental_id) REFERENCES rentals (id),
  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (message_id) REFERENCES messages (id)
);

-- Insertion des rôles par défaut
INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
