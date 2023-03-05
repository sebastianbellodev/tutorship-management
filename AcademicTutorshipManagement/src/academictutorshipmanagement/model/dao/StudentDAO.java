/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 03, 2023.
 * Date of update: March 03, 2023.
 */
package academictutorshipmanagement.model.dao;

import academictutorshipmanagement.model.DatabaseConnection;
import academictutorshipmanagement.model.pojo.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentDAO {

    public static ArrayList<Student> getStudentsByAcademicPersonnel(int idEducationalProgram, int idAcademicPersonnel) {
        ArrayList<Student> students = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT registrationNumber, name, paternalSurname, maternalSurname\n"
                + "FROM student\n"
                + "WHERE idEducationalProgram = ? AND idAcademicPersonnel = ?\n"
                + "ORDER BY paternalSurname ASC";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idEducationalProgram);
            preparedStatement.setInt(2, idAcademicPersonnel);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setRegistrationNumber(resultSet.getString("registrationNumber"));
                student.setName(resultSet.getString("name"));
                student.setPaternalSurname(resultSet.getString("paternalSurname"));
                student.setMaternalSurname(resultSet.getString("maternalSurname"));
                students.add(student);
            }
        } catch (SQLException exception) {
            students = null;
        } finally {
            databaseConnection.close();
        }
        return students;
    }

}