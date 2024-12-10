package com.example.StudentRegistration.Service;

import com.example.StudentRegistration.Model.Student;
import com.example.StudentRegistration.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public int saveStudent(Student student){
        return studentRepository.save(student);
    }

    public int updateStudent (Student student){
         return studentRepository.update(student);
    }

    public void deleteStudent(Long student_id){
        studentRepository.deleteById(student_id);
    }

    public List<Student> getAllStudent(){
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long student_id){
        return studentRepository.findById(student_id);
    }

    public List<Student> getStudentByCourseId(Long course_id){
        return studentRepository.findByCourseId(course_id);
    }

}
