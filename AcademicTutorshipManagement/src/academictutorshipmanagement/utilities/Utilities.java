/**
 * Name(s) of the programmer(s): sebastián Bello Trejo, María José Torres Igartua.
 * Date of creation: March 01, 2023.
 * Date of update: May 18, 2023.
 */
package academictutorshipmanagement.utilities;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import javafx.scene.control.Alert;

public class Utilities {

    public static String changeDateFormat(Date date) {
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    public static boolean compareGeneralFieldLength(Integer fieldLength) {
        return fieldLength.compareTo(Constants.GENERAL_FIELD_LENGTH) <= 0;
    }

    public static boolean compareRegistrationNumberLength(Integer registrationNumberLenght) {
        return registrationNumberLenght == Constants.REGISTRATION_NUMBER_LENGTH;
    }

    public static boolean compareStudentEmailAddressLength(Integer emailAddressLength) {
        return emailAddressLength == Constants.STUDENT_EMAIL_ADDRESS_LENGTH;
    }

    public static String computeSHA256Hash(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static Date convertDate(LocalDate localDate) {
        Date date = Date.from(Instant.from(localDate.atStartOfDay(ZoneId.systemDefault())));
        return date;
    }

    public static String convertLocalDateToDate(LocalDate localDate) {
        Date date = Date.from(Instant.from(localDate.atStartOfDay(ZoneId.systemDefault())));
        return new SimpleDateFormat("yyyy-MM-DD").format(date);
    }

    public static String generateRandomSixDigitCode() {
        Random random = new Random();
        int randomSixDigitCode = random.nextInt(999999);
        return String.valueOf(randomSixDigitCode);
    }

    public static boolean sendEmail(String subject, String message, String emailAddress) throws IOException {
        boolean isSent;
        try {
            Properties properties = new Properties();
            try (FileInputStream credentials = new FileInputStream(new File("src\\academictutorshipmanagement\\utilities\\Credentials.txt"))) {
                properties.load(credentials);
            }
            String username = properties.getProperty("USERNAME");
            String password = properties.getProperty("PASSWORD");
            Email from = new Email(username);
            Email to = new Email(emailAddress);
            Content content = new Content("text/plain", message);
            Mail mail = new Mail(from, subject, to, content);
            SendGrid sendGrid = new SendGrid(password);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sendGrid.api(request);
            isSent = true;
        } catch (Exception exception) {
            isSent = false;
        }
        return isSent;
    }

    public static void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}