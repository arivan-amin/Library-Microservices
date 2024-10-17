CREATE TABLE Borrows (
    id CHAR(36) NOT NULL PRIMARY KEY,  -- UUID stored as a CHAR(36)
    bookId CHAR(36) NOT NULL,
    patronId CHAR(36) NOT NULL,
    borrow_date_time DATETIME NOT NULL,
    return_date_time DATETIME
);
