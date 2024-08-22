package poov.controledoacaosangue.Controller;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import poov.controledoacaosangue.Model.Doacao;
import poov.controledoacaosangue.Model.Doador;
import poov.controledoacaosangue.Model.RH;
import poov.controledoacaosangue.Model.TipoSanguineo;

public class TelaCadastroDoacao implements Initializable{

    @FXML
    private Button buttonCancelar;

    @FXML
    private Button buttonCriar;

    @FXML
    private DatePicker datePickerData;

    @FXML
    private TextField textFieldHora;

    @FXML
    private TextField textFieldVolume;


    //indica que os dados da janela sao validos
    private boolean valido = false;

    //guarda os dados entrados pelo usuario
    private Doador doador;
    private Doacao nova;

    @FXML
    void buttonAlterarClicado(ActionEvent event) {
        if(true){
            valido = true;
            nova = new Doacao();
            nova.setDoador(doador);
            nova.setVolume(Long.parseLong(textFieldVolume.getText()));
            nova.setData(datePickerData.getValue());
            nova.setHora(LocalTime.parse(textFieldHora.getText()));
            System.out.println(nova.toString());
            ((Button)event.getSource()).getScene().getWindow().hide();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Aviso");
            alert.setContentText("Um dos campos está vazio");
            alert.showAndWait();
        }
    }
    public Doacao getDoacao(){
        return nova;

    }
    public void setDoador(Doador doador){
        this.doador = doador;
    }

    @FXML
    void buttonCancelarClicado(ActionEvent event) {
        valido = false;
        ((Button)event.getSource()).getScene().getWindow().hide();
    }
    public boolean isValido() {
        return valido;
    }

    private boolean validarCampos() {
        return !textFieldVolume.getText().isEmpty() &&
               !textFieldHora.getText().isEmpty();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TextFormatter<String> formatterApenasDigitos = new TextFormatter<>(change -> {
            if (!change.isContentChange()) {
                return change;
            }
            String text = change.getControlNewText();
            System.out.println(text);
        
            if (text.length() == 0) { // permite campo vazio
                return change;
            }
        
            // Verifica se o texto contém apenas dígitos e ':'
            if (text.matches("[0-9:]*")) {
                // Verifica se o formato está correto
                if (text.length() <= 5) { // Limita a entrada a no máximo 5 caracteres (HH:MM)
                    if (text.length() == 3 && !text.endsWith(":")) {
                        // Adiciona ':' após os dois primeiros dígitos
                        change.setText(text + ":");
                        change.setRange(0, text.length());
                    }
                    return change;
                }
            }
        
            return null; 
        });
        textFieldHora.setTextFormatter(formatterApenasDigitos);

        textFieldVolume.setText("");
        textFieldHora.setText("");
    }
}
