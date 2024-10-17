CREATE TABLE Patrons (
    id CHAR(36) NOT NULL PRIMARY KEY,  -- UUID stored as a CHAR(36)
    name VARCHAR(255) NOT NULL,
    contactInformation VARCHAR(255) NOT NULL
);
