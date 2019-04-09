CREATE DATABASE School;

USE School;

CREATE TABLE teacher (
    teacher_id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
    teacher_first_name VARCHAR(256) NOT NULL,
    teacher_last_name VARCHAR(256) NOT NULL
);

CREATE TABLE courses (
    course_id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
    course VARCHAR(256) NOT NULL,
    course_teacher_id INTEGER NULL
);

ALTER TABLE
courses
ADD CONSTRAINT
FK_course_teacher_id
FOREIGN KEY
(course_teacher_id)
REFERENCES
teacher (teacher_id)
ON DELETE NO ACTION;

CREATE TABLE student (
    student_id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
    student_first_name VARCHAR(256) NOT NULL,
    student_last_name VARCHAR(256) NOT NULL,
    student_course_id INTEGER NULL
);

ALTER TABLE
student
ADD CONSTRAINT
FK_student_course_id
FOREIGN KEY
(student_course_id)
REFERENCES
courses (course_id);

INSERT INTO teacher (teacher_first_name , teacher_last_name)
VALUES ('Ahmed' , 'Marzook'),
('Carl' , 'Jhones'),
('Carol' , 'Tunt'),
('Archer' , 'Sterling'),
('Lana' , 'Cane');

INSERT INTO courses (course , course_teacher_id)
VALUES('Computer-Science', 1),
('Physical-Education', 2),
('Art', 3), ('Music', 4),
('Food-Technolgy', 5);


INSERT INTO student (student_first_name , student_last_name, student_course_id)
VALUES ('Hassan' , 'Marzook', 1),
('Aadhil' , 'Rizvi', 2),
('Nabeel' , 'Nizamdeen', 3), ('Arham' , 'Murshid', 5), ('Ben' , 'Kearns', 4),
('Tom' , 'Hird', 5), ('Carlos' , 'Juan', 3), ('Jimmy' , 'Jones', 4), ('Laura' , 'Bridgett', 1),
('Sarah' , 'Kent', 2);

DROP procedure IF EXISTS `School`.`Student Teachers`;

DELIMITER $$
USE `School`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Student Teacher`()
BEGIN
SELECT s.student_first_name, s.student_last_name, t.teacher_first_name, t.teacher_last_name
FROM student s
INNER JOIN courses c ON c.course_id = s.student_course_id
INNER JOIN teacher t ON t.teacher_id = c.course_teacher_id;
END$$

DELIMITER ;
;

USE `School`;
DROP procedure IF EXISTS `School`.`Student Courses`;

DELIMITER $$
USE `School`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Student Course`()
BEGIN
SELECT s.student_first_name, s.student_last_name, c.course
FROM student s
INNER JOIN courses c ON s.student_course_id = c.course_id;
END$$

DELIMITER ;
;

USE `School`;
DROP procedure IF EXISTS `School`.`Teacher to Student`;

DELIMITER $$
USE `School`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Teacher to Student`(IN teacher_id int)
BEGIN
SELECT s.student_first_name, s.student_last_name, t.teacher_first_name, t.teacher_last_name, c.course
FROM student s
INNER JOIN courses c ON c.course_id = s.student_course_id
INNER JOIN teacher t ON t.teacher_id = c.course_teacher_id
WHERE t.teacher_id = teacher_id;
END$$

DELIMITER ;
;