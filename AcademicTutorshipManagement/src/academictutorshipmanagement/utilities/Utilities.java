/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 01, 2023.
 * Date of update: March 03, 2023.
 */
package academictutorshipmanagement.utilities;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import javafx.scene.control.Alert;

public class Utilities {

    public static String changeDateFormat(Date date) {
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    public static String computeSHA256Hash(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(bytes);
    }
    
    public static boolean compareRegistrationNumberLength(Integer registrationNumber) {
        return registrationNumber.equals(Constants.REGISTRATION_NUMBER_FIELD);
    }
    
    public static boolean compareStudentEmailAddressLength(Integer emailAddress) {
        return emailAddress.equals(Constants.STUDENT_EMAIL_ADDRESS_FIELD);
    }
    
    public static boolean compareGeneralFieldLength(Integer field) {
        int result = field.compareTo(Constants.GENERAL_FIELD);
        return (result <= 0) ? true : false;
    }
    
    public static void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
