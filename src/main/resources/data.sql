--INSERT INTO users (name, email, password) VALUES
--('Alice', 'alice@example.com', 'password'),
--('Bob', 'bob@example.com', 'password');
 INSERT INTO courses (course_id,course_name) VALUES
 (1,'SCIENCE'),(2,'COMMERCE'),(3,'ARTS');
 INSERT INTO subjects (subject_id,subject_name) VALUES
 (1,'BIOLOGY'),(2,'COMPUTER SCIENCE'),(3,'EDUCATION'),(4,'BUSINESS');
 INSERT INTO course_subjects(course_id,subject_id) VALUES
 (1,1),(1,2),(2,2),(2,4),(3,2),(3,3);