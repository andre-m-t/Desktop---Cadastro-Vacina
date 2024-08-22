package poov.controledoacaosangue.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import poov.controledoacaosangue.Model.Doador;
import poov.controledoacaosangue.Model.RH;
import poov.controledoacaosangue.Model.TipoSanguineo;

public class TelaCadastroVacinaController implements Initializable{

    @FXML
    private Button buttonCadastrar;

    @FXML
    private Button buttonCancelar;

    @FXML
    private RadioButton rhNegativoRadio;

    @FXML
    private RadioButton rhPositivoRadio;

    @FXML
    private RadioButton rhQualquerRadio;

    @FXML
    private ToggleGroup rhToggleGroup;

    @FXML
    private TextField textFieldContato;

    @FXML
    private TextField textFieldCpf;

    @FXML
    private TextField textFieldNome;

    @FXML
    private ToggleGroup tipoSanguineoToggleGroup;

    @FXML
    private RadioButton tpABRadio;

    @FXML
    private RadioButton tpARadio;

    @FXML
    private RadioButton tpBRadio;

    @FXML
    private RadioButton tpORadio;

    @FXML
    private RadioButton tpQualquerRadio;

     //indica que os dados da janela sao validos
     private boolean valido = false;

     //guarda os dados entrados pelo usuario
     private Doador doador;
 
    public Doador getDoador(){
        return doador;
    }

    @FXML
    void buttonCadastrarClicado(ActionEvent event) {
        if (validarCampos()) {
            valido = true;
            doador = new Doador();
            doador.setNome(textFieldNome.getText());
            doador.setCpf(textFieldCpf.getText());
            doador.setContato(textFieldContato.getText());
            // Capturando Tipo sanguineo
            RadioButton dtTipoSanguineo = (RadioButton) tipoSanguineoToggleGroup.getSelectedToggle();
            if (dtTipoSanguineo != null) {
                doador.setTipoSanguineo((TipoSanguineo) dtTipoSanguineo.getUserData());
            }
            // Capturando tipo de rh
            RadioButton dtTipoRh = (RadioButton) rhToggleGroup.getSelectedToggle();
            if (dtTipoRh != null) {
                doador.setRh((RH) dtTipoRh.getUserData());
            }


            ((Button)event.getSource()).getScene().getWindow().hide();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Aviso");
            alert.setContentText("Um dos campos está vazio");
            alert.showAndWait();
        }

    }

    @FXML
    void buttonCancelarClicado(ActionEvent event) {
        valido = false;
        ((Button)event.getSource()).getScene().getWindow().hide();

    }
    public void limpar() {
        valido = false;
        textFieldNome.clear();
        textFieldCpf.clear();
        textFieldContato.clear();
    }
    public boolean isValido() {
        return valido;
    }
    private boolean validarCampos() {
        return !textFieldNome.getText().isEmpty() &&
               !textFieldCpf.getText().isEmpty() &&
               !textFieldContato.getText().isEmpty();
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        // Inicializando campos de radio button
        tpQualquerRadio.setUserData(TipoSanguineo.DESCONHECIDO);
        tpARadio.setUserData(TipoSanguineo.A);
        tpABRadio.setUserData(TipoSanguineo.AB);
        tpBRadio.setUserData(TipoSanguineo.B);
        tpORadio.setUserData(TipoSanguineo.O);
        rhQualquerRadio.setUserData(RH.DESCONHECIDO);
        rhPositivoRadio.setUserData(RH.POSITIVO);
        rhNegativoRadio.setUserData(RH.NEGATIVO);

        TextFormatter<String> formatterApenasDigitos = new TextFormatter<>(change -> {
            if (!change.isContentChange()) {
                return change;
            }
            String text = change.getControlNewText();
            System.out.println(text);
            if (text.length() == 0) { // permite campo vazio
                return change;
            } else { // verifica se o texto, com a mudança, é um long válido
                try {
                    Long.parseLong(text);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            return change;
        });
        textFieldCpf.setTextFormatter(formatterApenasDigitos);
    }

}
