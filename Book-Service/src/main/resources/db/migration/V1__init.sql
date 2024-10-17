CREATE TABLE Books (
    id CHAR(36) NOT NULL PRIMARY KEY,  -- UUID stored as a CHAR(36)
    isbn VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    publicationYear VARCHAR(255) NOT NULL
);
