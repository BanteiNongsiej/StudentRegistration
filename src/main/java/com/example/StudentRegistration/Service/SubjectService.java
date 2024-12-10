package com.example.StudentRegistration.Service;

import com.example.StudentRegistration.Model.Subject;
import com.example.StudentRegistration.Repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    public  void save(Subject subject){
        subjectRepository.save(subject);
    }

    public void update(Subject subject){
        subjectRepository.save(subject);
    }

    public void deleteById(int subject_id){
        subjectRepository.deleteById(subject_id);
    }

    public List<Subject> findAllSubject(){
        return subjectRepository.findAll();
    }

    public List<Subject> getCourseByCourseId(int course_id){
        return subjectRepository.findByCourseId(course_id);
    }
}
