package org.example.studentslist;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class StudentDAO {

    private DataSource ds;

    public StudentDAO() throws NamingException {
        ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/students");
    }

    public void addStudent(Student student) {
        String sql = "INSERT INTO student (email, lastname, firstname, birthday) VALUES (?, ?, ?, ?)";

        try (Connection connection = ds.getConnection();
             PreparedStatement instruction = connection.prepareStatement(sql)) {


            instruction.setString(1, student.getEmail());
            instruction.setString(2, student.getLastName());
            instruction.setString(3, student.getFirstName());
            instruction.setDate(4, java.sql.Date.valueOf(student.getBirthday())); // must be java.sql.Date

            instruction.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteStudent(int id) {
        String sql = "DELETE FROM student WHERE id = ?";

        try (Connection connection = ds.getConnection();
             PreparedStatement instruction = connection.prepareStatement(sql)) {

            instruction.setString(1, String.valueOf(id));

            instruction.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateStudent(Student student) {
        String sql = "UPDATE student SET email = ?, lastname = ?, firstname = ?, birthday = ? WHERE id = ?";

        try (Connection connection = ds.getConnection();
             PreparedStatement instruction = connection.prepareStatement(sql)) {

            instruction.setString(1, student.getEmail());
            instruction.setString(2, student.getLastName());
            instruction.setString(3, student.getFirstName());
            instruction.setDate(4, java.sql.Date.valueOf(student.getBirthday()));
            instruction.setInt(5, student.getId());

            instruction.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT id, email, lastname, firstname, birthday FROM student ORDER BY lastname";

        try (Connection connection = ds.getConnection();
             PreparedStatement instruction = connection.prepareStatement(sql);
             ResultSet rs = instruction.executeQuery()) {

            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setEmail(rs.getString("email"));
                student.setLastName(rs.getString("lastname"));
                student.setFirstName(rs.getString("firstname"));
                student.setBirthday(rs. getDate("birthday").toLocalDate());

                students.add(student);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return students;
    }
    public boolean studentExists(String email) {
        String sql = "SELECT COUNT(*) FROM student WHERE email = ?";

        try (Connection connection = ds.getConnection();
             PreparedStatement instruction = connection.prepareStatement(sql)) {

            instruction.setString(1, email);

            try (ResultSet rs = instruction.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}
