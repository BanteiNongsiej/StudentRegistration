package com.example.StudentRegistration.Repository;

import com.example.StudentRegistration.Model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SubjectRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Subject>findByCourseId(int course_id){
        String sql="SELECT s.subject_id, s.subject_name FROM subjects s JOIN course_subjects cs WHERE s.course_id=cs.course_id WHERE cs.course_id= ?";
        return jdbcTemplate.query(sql,new Object[]{course_id},new SubjectRowMapper());
    }

    public int save(Subject subject){
        String sql="INSERT INTO subjects (name) VALUES (?) ";
        return jdbcTemplate.update(sql,subject.getSubject_name());
    }

    public List<Subject> findAll(){
        String sql="SELECT * FROM subjects";
        return jdbcTemplate.query(sql,new SubjectRowMapper());
    }

    public int update(Subject subject){
        String sql="UPDATE subjects SET subject_name=? WHERE subject_id=?";
        return jdbcTemplate.update(sql,subject.getSubject_name(),subject.getSubject_id());
    }

    public int deleteById(int subject_id){
        String sql="DELETE FROM subjects WHERE subject_id=?";
        return jdbcTemplate.update(sql,subject_id);
    }

    public static class SubjectRowMapper implements RowMapper<Subject>{
        @Override
        public Subject mapRow(ResultSet rs,int rownum) throws SQLException{
            Subject s=new Subject();
            s.setSubject_id(rs.getLong("subject_id"));
            s.setSubject_name(rs.getString("subject_name"));
            return s;
        }
    }
}
