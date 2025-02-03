
package canastafeliz;
import controller.Controller;
import static javax.swing.JOptionPane.showMessageDialog;
import view.Screen;
import model.Model;
import view.Login;

public class CanastaFeliz {

    public static void main(String[] args) {
        Model modelo = new Model();
        Screen vista = new Screen();      
        Login login = new Login();
        Controller controlador = new Controller( vista, modelo, login );
        
        controlador.iniciarVistaLogin();
       
    }
    
}
