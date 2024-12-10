package com.example.StudentRegistration.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.StudentRegistration.Model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Method to register a new user
    public int registerUser(User user) {
        String sql = "INSERT INTO users(name, email, password) VALUES(?, ?, ?)";
        return jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword());
    }

    public User getUserById(int id){
        String sql="SELECT * FROM users where id=?";
        return jdbcTemplate.queryForObject(sql,new Object[]{id},new UserRowMapper());
    }

    public List<User> getAllUsers(){
        String sql="SELECT * FROM users";
        return jdbcTemplate.query(sql,new UserRowMapper());
    }

    // Method to find user by email
    public User findByEmail(String email) {
    	System.out.println("Searching for user with email: " + email); 
        String sql = "SELECT * FROM users WHERE email = ?";
        System.out.println("Attempting to find user with email: " + email);  // Detailed logging

        try {
            User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), email);
            System.out.println("User found: " + user);  // Debugging output
            return user;
        } catch (EmptyResultDataAccessException e) {
            System.err.println("No user found with email: " + email);  // Detailed error logging
            return null;
        }
    }
    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    }


}
