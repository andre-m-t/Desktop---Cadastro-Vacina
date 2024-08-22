package poov.controledoacaosangue.Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import poov.controledoacaosangue.DAO.DoacaoDAO;
import poov.controledoacaosangue.Model.Doacao;
import poov.controledoacaosangue.Model.Doador;

public class TelaVerDoacoes implements Initializable{

    @FXML
    private Button buttonCancelar;

    @FXML
    private TableColumn<Doacao, String> dcCodigoColumn;

    @FXML
    private TableColumn<Doacao, String> dcDataColumn;

    @FXML
    private TableColumn<Doacao, String> dcHoraColumn;

    @FXML
    private TableColumn<Doacao, String> dcVolumeColumn;

    @FXML
    private TableView<Doacao> doacoesTableView;

    @FXML
    private Label labelNome;

    List<Doacao> todasDoacoes = new ArrayList<>();
    Doador doador = new Doador();

    @FXML
    void buttonCancelarClicado(ActionEvent event) {
        ((Button)event.getSource()).getScene().getWindow().hide();
    }

    public void setDoador(Doador doador){
        this.doador = doador;
        labelNome.setText(doador.getNome());
    }

    public void atualizarTabela(List<Doacao> todasDoacoes){
        // Atualizando a tabela de doações
        doacoesTableView.getItems().clear();
        doacoesTableView.getItems().addAll(todasDoacoes);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Inicializando células da tabela TAB DOACAO para introduzir elementos buscados do banco
        dcCodigoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo().toString()));
        dcVolumeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getVolume() )));
        dcDataColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getData())));
        dcHoraColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getHora())));

    }

}
