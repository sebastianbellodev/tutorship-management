/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 03, 2023.
 * Date of update: March 05, 2023.
 */
package academictutorshipmanagement.model.dao;

import academictutorshipmanagement.model.DatabaseConnection;
import academictutorshipmanagement.model.pojo.AcademicPersonnel;
import academictutorshipmanagement.model.pojo.Student;
import academictutorshipmanagement.utilities.Constants;
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
    
    public static ArrayList<Student> getStudentsByAcademicPersonnel(int idAcademicPersonnel) {
        ArrayList<Student> students = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT registrationNumber, name, paternalSurname, maternalSurname\n"
                + "FROM student\n"
                + "WHERE idAcademicPersonnel = ?\n"
                + "ORDER BY paternalSurname ASC";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idAcademicPersonnel);
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

    public static ArrayList<Student> getStudentsByEducationalProgram(int idEducationalProgram) {
        ArrayList<Student> students = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT *\n"
                + "FROM student\n"
                + "WHERE idEducationalProgram = ?\n"
                + "ORDER BY paternalSurname ASC";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idEducationalProgram);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setRegistrationNumber(resultSet.getString("registrationNumber"));
                student.setName(resultSet.getString("name"));
                student.setPaternalSurname(resultSet.getString("paternalSurname"));
                student.setMaternalSurname(resultSet.getString("maternalSurname"));
                student.setEmailAddress(resultSet.getString("emailAddress"));
                students.add(student);
            }
        } catch (SQLException exception) {
            students = null;
        } finally {
            databaseConnection.close();
        }
        return students;
    }
    
    public static ArrayList<Student> getStudentsUnassigned(int idEducationalProgram) {
        ArrayList<Student> students = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT *\n"
                + "FROM student\n"
                + "WHERE idEducationalProgram = ? AND idAcademicPersonnel is null\n"
                + "ORDER BY paternalSurname ASC";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idEducationalProgram);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setRegistrationNumber(resultSet.getString("registrationNumber"));
                student.setName(resultSet.getString("name"));
                student.setPaternalSurname(resultSet.getString("paternalSurname"));
                student.setMaternalSurname(resultSet.getString("maternalSurname"));
                student.setEmailAddress(resultSet.getString("emailAddress"));
                students.add(student);
            }
        } catch (SQLException exception) {
            students = null;
        } finally {
            databaseConnection.close();
        }
        return students;
    }

    public static ArrayList<Student> getStudentsByAcademicTutorshipReport(int idAcademicTutorshipReport) {
        ArrayList<Student> students = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT student.registrationNumber, student.name, student.paternalSurname, student.maternalSurname,\n"
                + "academicTutorshipReportStudent.attendedBy, academicTutorshipReportStudent.atRisk\n"
                + "FROM student\n"
                + "INNER JOIN academicTutorshipReportStudent\n"
                + "ON student.registrationNumber = academicTutorshipReportStudent.registrationNumber\n"
                + "WHERE idAcademicTutorshipReport = ?\n"
                + "ORDER BY student.paternalSurname ASC";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idAcademicTutorshipReport);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setRegistrationNumber(resultSet.getString("registrationNumber"));
                student.setName(resultSet.getString("name"));
                student.setPaternalSurname(resultSet.getString("paternalSurname"));
                student.setMaternalSurname(resultSet.getString("maternalSurname"));
                student.setAttendedBy(resultSet.getBoolean("attendedBy"));
                student.setAtRisk(resultSet.getBoolean("atRisk"));
                students.add(student);
            }
        } catch (SQLException exception) {
            students = null;
        } finally {
            databaseConnection.close();
        }
        return students;
    }

    public static int logStudentByAcademicTutorshipReport(Student student, int idAcademicTutorshipReport) {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "INSERT INTO academicTutorshipReportStudent\n"
                + "(attendedBy, atRisk, idAcademicTutorshipReport, registrationNumber)\n"
                + "VALUES(?, ?, ?, ?)";
        try (Connection connection = databaseConnection.open()) {
            boolean attendedBy = student.isAttendedBy();
            boolean atRisk = student.isAtRisk();
            String registrationNumber = student.getRegistrationNumber();
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setBoolean(1, attendedBy);
            preparedStatement.setBoolean(2, atRisk);
            preparedStatement.setInt(3, idAcademicTutorshipReport);
            preparedStatement.setString(4, registrationNumber);
            int numberOfRowsAffected = preparedStatement.executeUpdate();
            responseCode = (numberOfRowsAffected >= Constants.MINIUM_NUMBER_OF_ROWS_AFFECTED_PER_DATABASE_UPDATE) ? Constants.CORRECT_OPERATION_CODE : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException exception) {
            responseCode = Constants.NO_DATABASE_CONNECTION_CODE;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }

    public static int updateStudentByAcademicTutorshipReport(Student student) {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "UPDATE academicTutorshipReportStudent\n"
                + "SET attendedBy = ?, atRisk = ?\n"
                + "WHERE registrationNumber = ?";
        try (Connection connection = databaseConnection.open()) {
            boolean attendedBy = student.isAttendedBy();
            boolean atRisk = student.isAtRisk();
            String registrationNumber = student.getRegistrationNumber();
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setBoolean(1, attendedBy);
            preparedStatement.setBoolean(2, atRisk);
            preparedStatement.setString(3, registrationNumber);
            int numberOfRowsAffected = preparedStatement.executeUpdate();
            responseCode = (numberOfRowsAffected >= Constants.MINIUM_NUMBER_OF_ROWS_AFFECTED_PER_DATABASE_UPDATE) ? Constants.CORRECT_OPERATION_CODE : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException exception) {
            responseCode = Constants.NO_DATABASE_CONNECTION_CODE;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }

    public static int updateStudent(Student student, String registrationNumber) {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "UPDATE student\n"
                + "SET registrationNumber = ?, name = ?, paternalSurname = ?, maternalSurname = ?, emailAddress = ?\n"
                + "WHERE registrationNumber = ?";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setString(1, student.getRegistrationNumber());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setString(3, student.getPaternalSurname());
            preparedStatement.setString(4, student.getMaternalSurname());
            preparedStatement.setString(5, student.getEmailAddress());
            preparedStatement.setString(6, registrationNumber);
            int numberOfRowsAffected = preparedStatement.executeUpdate();
            responseCode = (numberOfRowsAffected >= Constants.MINIUM_NUMBER_OF_ROWS_AFFECTED_PER_DATABASE_UPDATE) ? Constants.CORRECT_OPERATION_CODE : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException exception) {
            responseCode = Constants.NO_DATABASE_CONNECTION_CODE;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }
    
    public static int updateStudentAcademicPersonnel(Student student, AcademicPersonnel academicPersonnel) {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "UPDATE student\n"
                + "SET idAcademicPersonnel = ?\n"
                + "WHERE registrationNumber = ?";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setInt(1, academicPersonnel.getIdAcademicPersonnel());
            preparedStatement.setString(2, student.getRegistrationNumber());
            int numberOfRowsAffected = preparedStatement.executeUpdate();
            responseCode = (numberOfRowsAffected >= Constants.MINIUM_NUMBER_OF_ROWS_AFFECTED_PER_DATABASE_UPDATE) ? Constants.CORRECT_OPERATION_CODE : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException exception) {
            responseCode = Constants.NO_DATABASE_CONNECTION_CODE;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }

    public static int logStudent(Student student) {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "INSERT INTO student\n"
                + "(registrationNumber, name, paternalSurname, maternalSurname, emailAddress, idEducationalProgram)\n"
                + "VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection connection = databaseConnection.open()) {
            String registrationNumber = student.getRegistrationNumber();
            String name = student.getName();
            String paternalSurname = student.getPaternalSurname();
            String maternalSurname = student.getMaternalSurname();
            String emailAddress = student.getEmailAddress();
            int idEducationalProgram = student.getEducationalProgram().getIdEducationalProgram();
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setString(1, registrationNumber);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, paternalSurname);
            preparedStatement.setString(4, maternalSurname);
            preparedStatement.setString(5, emailAddress);
            preparedStatement.setInt(6, idEducationalProgram);
            int numberOfRowsAffected = preparedStatement.executeUpdate();
            responseCode = (numberOfRowsAffected >= Constants.MINIUM_NUMBER_OF_ROWS_AFFECTED_PER_DATABASE_UPDATE) ? Constants.CORRECT_OPERATION_CODE : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException exception) {
            responseCode = Constants.NO_DATABASE_CONNECTION_CODE;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }

    public static int checkStudent(String registrationNumber) {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "SELECT *\n"
                + "FROM student\n"
                + "WHERE registrationNumber = ?";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setString(1, registrationNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            responseCode = (resultSet.next()) ? Constants.MINIUM_NUMBER_OF_ROWS_RETURNED_PER_DATABASE_SELECT : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException exception) {
            responseCode = Constants.NO_DATABASE_CONNECTION_CODE;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }
    
    public static int deleteStudent(String registrationNumber) {
        int responseCode;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String sentence = "DELETE "
                + "FROM student\n"
                + "WHERE registrationNumber = ?";
        try (Connection connection = databaseConnection.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setString(1, registrationNumber);
            int numberOfRowsAffected = preparedStatement.executeUpdate();
            responseCode = (numberOfRowsAffected >= Constants.MINIUM_NUMBER_OF_ROWS_AFFECTED_PER_DATABASE_UPDATE) ? Constants.CORRECT_OPERATION_CODE : Constants.NO_DATABASE_CONNECTION_CODE;
        } catch (SQLException exception) {
            responseCode = Constants.NO_DATABASE_CONNECTION_CODE;
        } finally {
            databaseConnection.close();
        }
        return responseCode;
    }

}
