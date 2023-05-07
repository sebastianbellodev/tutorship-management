/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package academictutorshipmanagement.utilities;

import javafx.scene.control.Alert;

/**
 *
 * @author oband
 */
public class MessagesAlerts {
    public static void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public static void showAlert(String title,String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void showFailureLoadWindow(){ //Usually using in IOException
        MessagesAlerts.showAlert("Perdida de Conexión",
            "No se pudo mostrar la ventana "+
            "Por favor comuniquese con soporte técnico para mayor apoyo.", 
            Alert.AlertType.ERROR);
    }
    
    
    
    public static void showDataBaseLostConnectionAlert(){ //Usually using in SQLException
       MessagesAlerts.showAlert("Perdida de Conexión",
            "No se pudo conectar con la base de datos. "+
            "Por favor, inténtelo más tarde.", Alert.AlertType.ERROR);
    }
    
    public static void showBlankFieldsAlert(){  
        MessagesAlerts.showAlert("Campos Vacios",
            "Campos en blanco. "+
            "Por favor, verifique que todos los campos contengan información.", Alert.AlertType.WARNING);
    }

    public static void showAccessDenied() {
        Utilities.showAlert("No tiene los permisos necesarios para realizar esta acción.\n\n"
                    + "Por favor, vuelva a iniciar sesión e inténtelo nuevamente.\n",
                    Alert.AlertType.INFORMATION);
    }
}
