package com.example.StudentRegistration.Repository;

import com.example.StudentRegistration.Model.Course;
import com.example.StudentRegistration.Model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

@Repository
public class CourseRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Course> findAll() {
        String sql = "SELECT * FROM courses";
        return jdbcTemplate.query(sql,new CourseRowMapper());
    }

    public Course findById(int id){
        String sql="INSERT INTO courses WHERE course_id=?";
        return jdbcTemplate.queryForObject(sql,new Object[]{id},new CourseRowMapper());
    }

    public int save(Course course){
        String sql="INSERT INTO courses (course_name) VALUES (?)";
        return jdbcTemplate.update(sql,course.getCourse_name());
    }

    public int update(Course course){
        String sql="UPDATE courses SET course_name= ? where course_id= ?";
        return jdbcTemplate.update(sql,course.getCourse_name(),course.getCourse_id());
    }

    public int deleteById(int course_id){
        String sql="DELETE FROM courses where course_id=?";
        return jdbcTemplate.update(sql,course_id);
    }

    public Set<Subject> findSubjectByCourseId(int course_id){
        String sql="SELECT s.subject_id, s.subject_name FROM subjects s JOIN course_subject cs ON s.subject_id=cs.subject_id WHERE cs.course_id=?";
        return new HashSet<>(jdbcTemplate.query(sql,new Object[]{course_id},new SubjectRowMapper()));
    }

    private static class CourseRowMapper implements RowMapper<Course>{
        @Override
        public Course mapRow(ResultSet rs,int rowNum) throws SQLException{
            Course course=new Course();
            course.setCourse_id(rs.getLong("course_id"));
            course.setCourse_name(rs.getString("course_name"));
            return course;
        }
    }
    private static class SubjectRowMapper implements RowMapper<Subject>{
        @Override
        public Subject mapRow(ResultSet rs,int rowNum) throws SQLException{
            Subject subject =new Subject();
            subject.setSubject_id(rs.getLong("subject_id"));
            subject.setSubject_name(rs.getString("subject_name"));
            return subject;
        }
    }
}
