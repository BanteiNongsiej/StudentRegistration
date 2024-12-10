package com.example.StudentRegistration.Repository;

import com.example.StudentRegistration.Model.Course;
import com.example.StudentRegistration.Model.Student;
import com.example.StudentRegistration.Model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(Student s){
        String sql="INSERT INTO students (student_name,student_class,student_roll,course_id,subject_id) VALUES (?,?,?,?,?)";
        return jdbcTemplate.update(sql,s.getStudent_name(),s.getStudent_class(),s.getStudent_roll(),s.getCourse_id(),s.getSubject_id());
    }

    public int update(Student s){
        String sql="UPDATE students SET student_name=?, student_class=?, student_roll=?, course_id=?,subject_id=? WHERE student_id=?";
        return jdbcTemplate.update(sql,s.getStudent_name(),s.getStudent_class(),s.getStudent_roll(),s.getCourse_id(),s.getSubject_id(),s.getStudent_id());
    }

    public int deleteById(Long student_id){
        String sql="DELETE FROM students WHERE student_id=? ";
        return jdbcTemplate.update(sql,student_id);
    }

    public List<Student> findByCourseId (Long course_id){
        String sql="SELECT * FROM students WHERE course_id=?";
        return jdbcTemplate.query(sql,new Object[]{course_id},new StudentRowMapper());
    }

    public List<Student> findAll(){
        String sql="SELECT s.*, c.course_name, c.course_id, sub.subject_id, sub.subject_name  FROM students s JOIN courses c ON s.course_id = c.course_id JOIN subjects sub ON s.subject_id = sub.subject_id";
        return jdbcTemplate.query(sql,new StudentRowMapper());
    }

    public Optional<Student> findById(Long student_id){
        String sql="SELECT s.*, c.course_name, sub.subject_name FROM students s JOIN courses c ON s.course_id = c.course_id JOIN subjects sub ON s.subject_id = sub.subject_id WHERE student_id = ?";
        Student student = jdbcTemplate.queryForObject(sql, new Object[]{student_id}, new StudentRowMapper());
        return Optional.ofNullable(student);
    }

    private static class StudentRowMapper implements RowMapper<Student>{
        @Override
        public Student mapRow(ResultSet rs,int rowNum) throws SQLException{
            Student student=new Student();
            student.setStudent_id(rs.getLong("student_id"));
            student.setStudent_name(rs.getString("student_name"));
            student.setStudent_class(rs.getString("student_class"));
            student.setStudent_roll(rs.getString("student_roll"));
            student.setCourse_id(rs.getLong("course_id"));
            student.setSubject_id(rs.getLong("subject_id"));
            Course course=new Course();
            course.setCourse_id(rs.getLong("course_id"));
            course.setCourse_name(rs.getString("course_name"));
            Subject subject=new Subject();
            subject.setSubject_id(rs.getLong("subject_id"));
            subject.setSubject_name(rs.getString("subject_name"));
            return student;
        }

    }
}
