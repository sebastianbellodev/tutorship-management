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

    public static boolean compareStudentEmailAddressLength(Integer emailAddressLength) {
        return emailAddressLength == Constants.STUDENT_EMAIL_ADDRESS_LENGTH;
    }

    public static boolean compareGeneralFieldLength(Integer fieldLength) {
        return fieldLength.compareTo(Constants.GENERAL_FIELD_LENGTH) <= 0;
    }

    public static boolean compareRegistrationNumberLength(Integer registrationNumberLenght) {
        return registrationNumberLenght == Constants.REGISTRATION_NUMBER_LENGTH;
    }

    public static void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}