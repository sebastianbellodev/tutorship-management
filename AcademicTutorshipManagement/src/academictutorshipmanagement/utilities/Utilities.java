/**
 * Name(s) of the programmer(s): María José Torres Igartua.
 * Date of creation: March 01, 2023.
 * Date of update: March 01, 2023.
 *
 */
package academictutorshipmanagement.utilities;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javafx.scene.control.Alert;

public class Utilities {

    public static String computeSHA256Hash(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static void showAlert(String message, Alert.AlertType type) {
        Alert popUpWindow = new Alert(type);
        popUpWindow.setTitle(null);
        popUpWindow.setHeaderText(null);
        popUpWindow.setContentText(message);
        popUpWindow.showAndWait();
    }

}