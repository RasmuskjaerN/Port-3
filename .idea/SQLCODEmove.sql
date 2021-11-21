DROP TABLE IF EXISTS Students;
CREATE TABLE IF NOT EXISTS Students(
                                       studentID INTEGER NOT NULL PRIMARY KEY,
                                       firstName TEXT NOT NULL,
                                       lastName TEXT NOT NULL,
                                       GPA FLOAT,
                                       City TEXT,
                                       Country TEXT,
                                       FOREIGN KEY (City) REFERENCES Cities(City) ON DELETE RESTRICT ON UPDATE CASCADE,
                                       FOREIGN KEY (Country) REFERENCES Countries(countryCode) ON DELETE RESTRICT ON UPDATE CASCADE
);

DROP TABLE IF EXISTS Cities;
CREATE TABLE IF NOT EXISTS Cities(
                                     City TEXT PRIMARY KEY,
                                     zipCode INTEGER,
                                     countryCode TEXT,
                                     FOREIGN KEY (countryCode) REFERENCES Countries(countryCode) ON DELETE RESTRICT ON UPDATE CASCADE
);

DROP TABLE IF EXISTS Countries;
CREATE TABLE IF NOT EXISTS Countries(
                                        countryCode TEXT NOT NULL PRIMARY KEY,
                                        Country TEXT
);


DROP TABLE IF EXISTS courseRegistration;
CREATE TABLE IF NOT EXISTS courseRegistration(
                                                 studentID INTEGER NOT NULL,
                                                 courseID INTEGER NOT NULL,
                                                 Grade INTEGER,
                                                 FOREIGN KEY (courseID) REFERENCES Course(courseID) ON DELETE RESTRICT ON UPDATE CASCADE,
                                                 FOREIGN KEY (studentID) REFERENCES Students(studentID) ON DELETE RESTRICT ON UPDATE CASCADE,
                                                 PRIMARY KEY (courseID,studentID)
);
DROP TABLE IF EXISTS Course;
CREATE TABLE IF NOT EXISTS Course(
                                     courseID INTEGER NOT NULL PRIMARY KEY,
                                     courseName TEXT NOT NULL,
                                     Semester TEXT,
                                     courseGPA FLOAT,
                                     teacherID INTEGER NOT NULL,
                                     FOREIGN KEY (teacherID) REFERENCES Teacher(teacherID) ON DELETE RESTRICT ON UPDATE CASCADE
);
DROP TABLE IF EXISTS Teacher;
CREATE TABLE IF NOT EXISTS Teacher(
                                      teacherID INTEGER NOT NULL PRIMARY KEY,
                                      firstName TEXT NOT NULL,
                                      lastName TEXT
);

INSERT INTO Students VALUES (1,'Aisha', 'Lincoln', null, 'Nykøbing F','DK'),
                            (2, 'Anya', 'Nielsen', null, 'Nykøbing F','DK'),
                            (3, 'Alfred', 'Jensen', null, 'Karlskrona','SE'),
                            (4, 'Berta', 'Bertelsen', null, 'Billund','DK'),
                            (5, 'Albert', 'Antonsen', null, 'Sorø','DK'),
                            (6, 'Eske', 'Eriksen', null, 'Eskildstrup','DK'),
                            (7, 'Olaf', 'Olesen', null, 'Odense','DK'),
                            (8, 'Salma', 'Simonsen', null, 'Stockholm','SE'),
                            (9, 'Theis', 'Thomassen', null, 'Tølløse','DK'),
                            (10, 'Janet', 'Jensen', null, 'Jyllinge','DK'),
                            (11,'Egon', 'Damgon', null, 'Roskilde','DK');

INSERT INTO Cities VALUES ('Nykøbing F', 4800, 'DK'),
                          ('Karlskrona', null, 'SE'),
                          ('Billund', 7190, 'DK'),
                          ('Sorø', 4180, 'DK'),
                          ('Eskildstrup', 4863, 'DK'),
                          ('Odense', 5000, 'DK'),
                          ('Stockholm', null, 'SE'),
                          ('Tølløse', 4340, 'DK'),
                          ('Jyllinge', 4040, 'DK'),
                          ('Roskilde', 4000, 'DK');


INSERT INTO Countries VALUES ('Danmark','DK'),
                             ('Sweden','SE');


INSERT INTO courseRegistration VALUES(1,1,12),
                                     (1,3,10),
                                     (2,2,null),
                                     (2,3,12),
                                     (3,1,7),
                                     (3,3,10),
                                     (4,2,null),
                                     (4,3,2),
                                     (5,1,10),
                                     (5,3,7),
                                     (6,2,null),
                                     (6,3,10),
                                     (7,1,4),
                                     (7,3,12),
                                     (8,2,null),
                                     (8,3,12),
                                     (9,1,12),
                                     (9,3,12),
                                     (10,2,null),
                                     (10,3,7),
                                     (11,1,null),
                                     (11,2,02);

INSERT INTO Course VALUES (1,'SD','Autumn 2020',null,1),
                          (2,'SD','Autumn 2021',null,1),
                          (3,'ES1','Autumn 2020',null,2);

INSERT INTO Teacher VALUES (1, 'Line', 'Reinhardt'),
                           (2,'Bo', 'Holst');

SELECT AVG(Grade) FROM courseRegistration GROUP BY studentID;
SELECT AVG(Grade) FROM courseRegistration GROUP BY courseID;

--Show All students name and id that that have the given course.

SELECT S.StudentID as StudentID, S.firstName as FirstName, S.lastName as LastName,
       cR.CourseID as CourseID, C.courseName as CourseName, cR.Grade as Grade
FROM Students as S
         JOIN courseRegistration as cR ON S.StudentID=cR.StudentID
         JOIN Course C on C.CourseID = cR.CourseID
WHERE C.courseID=1;