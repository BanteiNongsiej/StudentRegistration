package com.example.StudentRegistration.Service;

import com.example.StudentRegistration.Model.Course;
import com.example.StudentRegistration.Model.Subject;
import com.example.StudentRegistration.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getAllCourse(){
        return courseRepository.findAll();
    }

    public void createCourse(Course course){
        courseRepository.save(course);
    }

    public void update(Course course){
        courseRepository.update(course);
    }

    public void deleteCourse(int course_id){
        courseRepository.deleteById(course_id);
    }

    public Optional<Course> getCourseById(int course_id){
        return Optional.ofNullable(courseRepository.findById(course_id));
    }

    public Set<Subject> getSubjectsByCourseId(int course_id){
        return courseRepository.findSubjectByCourseId(course_id);
    }
}
