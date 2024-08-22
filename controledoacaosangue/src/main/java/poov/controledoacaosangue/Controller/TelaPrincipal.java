package poov.controledoacaosangue.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import poov.controledoacaosangue.App;
import poov.controledoacaosangue.DAO.ConexaoFactoryPostgreSQL;
import poov.controledoacaosangue.DAO.DoacaoDAO;
import poov.controledoacaosangue.DAO.DoadorDAO;
import poov.controledoacaosangue.DAO.core.DAOFactory;
import poov.controledoacaosangue.Model.Doacao;
import poov.controledoacaosangue.Model.Doador;
import poov.controledoacaosangue.Model.RH;
import poov.controledoacaosangue.Model.Situacao;
import poov.controledoacaosangue.Model.TipoSanguineo;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TelaPrincipal implements Initializable{

    // Instanciando objetos DAO para manipulações no banco
    private DAOFactory factory;

    
    @FXML
    private Button alterarDoadorButton;

    @FXML
    private Button buscarButton;

    @FXML
    private Button cadastrarDoacaoButton;

    @FXML
    private Button cadastrarDoadorButton;

    @FXML
    private TableColumn<Doador, String> codigoColumn;

    @FXML
    private TableColumn<Doador, String> contatoColumn;

    @FXML
    private TableColumn<Doador, String> cpfColumn;

    @FXML
    private TableColumn<Doador, String> tipoSanguineoColumn;

    @FXML
    private TableColumn<Doador, String> rhColumn;

    @FXML
    private TableColumn<Doador, String> nomeColumn;

    @FXML
    private RadioButton dcABRadio;

    @FXML
    private RadioButton dcARadio;

    @FXML
    private RadioButton dcBRadio;

    @FXML
    private Button dcBuscarButton;

    @FXML
    private TableColumn<Doacao, String> dcCodigoColumn;

    @FXML
    private TextField dcCodigoDoacao;

    @FXML
    private TextField dcCodigoDoador;

    @FXML
    private TextField dcContatoDoador;

    @FXML
    private TableColumn<Doacao, String> dcContatoDoadorColumn;

    @FXML
    private TextField dcCpfDoador;

    @FXML
    private TableColumn<Doacao, String> dcCpfDoadorColumn;

    @FXML
    private TableColumn<Doacao, String> dcDataColumn;

    @FXML
    private DatePicker dcDataFim;

    @FXML
    private DatePicker dcDataIni;

    @FXML
    private TableColumn<Doacao, String> dcHoraColumn;

    @FXML
    private TextField dcHoraFim;

    @FXML
    private TextField dcHoraIni;

    @FXML
    private Button dcLimparButton;

    @FXML
    private TextField dcNomeDoador;

    @FXML
    private TableColumn<Doacao, String> dcNomeDoadorColumn;

    @FXML
    private RadioButton dcORadio;

    @FXML
    private RadioButton dcQualquerRadioRh;

    @FXML
    private RadioButton dcQualquerRadioTipo;

    @FXML
    private RadioButton dcRhNegativo;

    @FXML
    private RadioButton dcRhPositivo;

    @FXML
    private TableColumn<Doacao, String> dcVolumeColumn;

    @FXML
    private TextField dcVolumeFim;

    @FXML
    private TextField dcVolumeInicio;

    @FXML
    private TableView<Doacao> doacoesTableView;

    @FXML
    private TableView<Doador> doadoresTableView;

    @FXML
    private TextField dtCodigoDoador;

    @FXML
    private TextField dtContatoDoador;

    @FXML
    private TextField dtCpfDoador;

    @FXML
    private Tab dtDoacao;

    @FXML
    private Tab dtDoador;

    @FXML
    private TextField dtNomeDoador;

    @FXML
    private Button fecharButton;

    @FXML
    private Button limparButton;

    @FXML
    private Button removerDoadorButton;

    @FXML
    private RadioButton rhNegativoRadio;

    @FXML
    private RadioButton rhPositivoRadio;

    @FXML
    private RadioButton rhQualquerRadio;

    @FXML
    private ToggleGroup rhToggleGroup;

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

    @FXML
    private Button verDoacoesButton;

    @FXML
    private TextField dtHora;
    
    @FXML
    private DatePicker dtDatePick;

    @FXML
    private TextField dtRhCorreto;

    @FXML
    private TextField dtSituacao;

    @FXML
    private TextField dtVolume;

    public int acao = 0;

    private Stage stageTelaCadastroVacina;
    private TelaCadastroVacinaController controllerTelaCadastroVacina;

    private Stage stageTelaAlterarVacina;
    private TelaAlterarVacinaController controllerTelaAlterarVacina;

    private Stage stageTelaCadastrarDoacao;
    private TelaCadastroDoacao controllerCadastroDoacao;

    private Stage stageTelaVerDoacao;
    private TelaVerDoacoes controllerVerDoacao;

    @FXML
    void alterarDoadorPress(ActionEvent event) {
        
            // Capturando doador Clicado
            Doador selecionado = doadoresTableView.getSelectionModel().getSelectedItem();
            if(selecionado != null){
                

                if (stageTelaAlterarVacina.getOwner() == null) {
                    stageTelaAlterarVacina.initOwner(((Button)event.getSource()).getScene().getWindow());
                }
                controllerTelaAlterarVacina.setDoador(selecionado);
                stageTelaAlterarVacina.showAndWait();
                if (controllerTelaAlterarVacina.isValido()) {
                    Doador alterado = controllerTelaAlterarVacina.getDoador();
                    try {
                        // Abrindo conexao
                        factory.abrirConexao();
                        // Instanciando classe de doador
                        DoadorDAO dao = factory.getDAO(DoadorDAO.class);
                        String msg = dao.alterarDoador(alterado);
                        List<Doador> doadores = dao.buscarTodos();
                        // Mostrando alerta na tela
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Info");
                        alert.setHeaderText("Info");
                        alert.setContentText(msg);
                        alert.showAndWait();
                        // Refazendo pesquisa e atualizando table view
                        doadoresTableView.getItems().clear();
                        doadoresTableView.getItems().addAll(doadores);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        // Mostrando alerta na tela
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Info");
                        alert.setHeaderText("Info");
                        alert.setContentText("Preencha tudo corretamente!");
                        alert.showAndWait();
                    }finally{
                        // fechando conexao
                        factory.fecharConexao();
                    }
                }           
        
                
            }else{
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Problema");
                alert.setHeaderText("Problema");
                alert.setContentText("Voce precisa selecionar algum doador da tabela!");
                alert.showAndWait();
            }
        
    }

    @FXML
    void cadastrarDoacaoPress(ActionEvent event) {
        // Capturando doador Clicado
        Doador selecionado = doadoresTableView.getSelectionModel().getSelectedItem();
        if(selecionado != null){
            if (stageTelaCadastrarDoacao.getOwner() == null) {
                stageTelaCadastrarDoacao.initOwner(((Button)event.getSource()).getScene().getWindow());
            }
            controllerCadastroDoacao.setDoador(selecionado);
            stageTelaCadastrarDoacao.showAndWait();
            // Criando doacao
            Doacao nova = controllerCadastroDoacao.getDoacao();
            nova.setSituacao(Situacao.ATIVO);

            try{
                // Abrindo conexao
                factory.abrirConexao();
                // Instanciando classe de doador
                DoacaoDAO dao = factory.getDAO(DoacaoDAO.class);
                String msg = dao.novaDoacao(nova);
                // Mostrando alerta na tela
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Info");
                alert.setHeaderText("Info");
                alert.setContentText(msg);
                alert.showAndWait(); 
                
            }catch(SQLException e){
                e.printStackTrace();
            }finally{
                // fechando conexao
                factory.fecharConexao();
            }
        }else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Problema");
            alert.setHeaderText("Problema");
            alert.setContentText("Voce precisa selecionar algum doador da tabela!");
            alert.showAndWait();
        }
    }

    @FXML
    void cadastrarDoador(ActionEvent event) {
        if (stageTelaCadastroVacina.getOwner() == null) {
            stageTelaCadastroVacina.initOwner(((Button)event.getSource()).getScene().getWindow());
        }
        controllerTelaCadastroVacina.limpar();
        stageTelaCadastroVacina.showAndWait();
        if (controllerTelaCadastroVacina.isValido()) {
            Doador novo = controllerTelaCadastroVacina.getDoador();
            try {
                factory.abrirConexao();
                DoadorDAO dao = factory.getDAO(DoadorDAO.class);
                String msg = dao.cadastrarDoador(novo);
                factory.fecharConexao();
                // Mostrando alerta na tela
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Info");
                alert.setHeaderText("Info");
                alert.setContentText(msg);
                alert.showAndWait(); 
                // Refazendo pesquisa e atualizando table view
                List<Doador> doadores = dao.buscarTodos();
                doadoresTableView.getItems().clear();
                doadoresTableView.getItems().addAll(doadores);  
            } catch (SQLException e) {
                e.printStackTrace();
                // Mostrando alerta na tela
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Info");
                alert.setHeaderText("Info");
                alert.setContentText("Erro ao cadastrar Doador!");
                alert.showAndWait(); 
            }
        }
    }

    @FXML
    void dcBuscarButtonPress(ActionEvent event) {
        try {
            // Abrindo conexão com o banco
            factory.abrirConexao();
            // Instanciando a conexão com a tabela de doações
            DoacaoDAO dao = factory.getDAO(DoacaoDAO.class);
            DoadorDAO daoDoador = factory.getDAO(DoadorDAO.class);
            // Buscando todas as doações ativas
            List<Doacao> doacoes = dao.buscarDoacoesAtivas();
            // Identidicando doadores
            Doador novo = dcColetarTextFields();
            List<Doador> ajuda = daoDoador.buscarPorFiltro(novo);
            // Lista selecionada
            List<Doacao> selecionada = new ArrayList<>(); 
            for(Doacao aux : doacoes){
                
                for(Doador esse : ajuda){
                    if (aux.getDoador().getCodigo() == esse.getCodigo()){
                        aux.setDoador(esse);
                        selecionada.add(aux);
                    }
                }
            }

            // Atualizando a tabela de doações
            doacoesTableView.getItems().clear();
            doacoesTableView.getItems().addAll(selecionada);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // fechando conexão
            factory.fecharConexao();
        }
    }

    @FXML
    void dcLimparButtonPress(ActionEvent event) {

    }

    @FXML
    void dtBuscarPress(ActionEvent event) {
        
        // Criando objeto e coletando os text fields
        Doador filtro = dtColetarTextFields();
        // Estabelecendo conexão com o banco de dados
        try {
            // Abrindo conexão com o banco
            factory.abrirConexao();
            // Instanciando a conexão com a tabela de doadores
            DoadorDAO dao = factory.getDAO(DoadorDAO.class);
            // Armazenando valores num ArrayList
            List<Doador> doadores = dao.buscarPorFiltro(filtro);
            // Mostrando na tabela
            doadoresTableView.getItems().clear();
            doadoresTableView.getItems().addAll(doadores);

        }catch(SQLException e){
            e.printStackTrace();

        } finally {
            factory.fecharConexao();
        }
        

    }

    @FXML
    void dtLimparPress(ActionEvent event) {
        dtCodigoDoador.setText("");
        dtNomeDoador.setText("");
        dtCpfDoador.setText("");
        dtContatoDoador.setText("");
        tpQualquerRadio.setSelected(true);
        rhQualquerRadio.setSelected(true);
        dtSituacao.setText("");
        dtRhCorreto.setText("");
    }

    public void preencherCampos(Doador doador){
        dtCodigoDoador.setText(doador.getCodigo().toString());
        dtNomeDoador.setText(doador.getNome());
        dtCpfDoador.setText(doador.getCpf());
        dtContatoDoador.setText(doador.getContato());

        switch (doador.getTipoSanguineo().getDescricao()) {
            case "A" :
                tpARadio.setSelected(true);
                break;
            case "B" :
                tpBRadio.setSelected(true);
                break;
            case "AB" :
                tpABRadio.setSelected(true);
                break;
        
            case "O" :
                tpORadio.setSelected(true);
                break;

            default:
                tpQualquerRadio.setSelected(true);
                break;
        }

        switch (doador.getRh().getDescricao()) {
            case "POSITIVO":
                rhPositivoRadio.setSelected(true);
                break;
            case "NEGATIVO":
                rhNegativoRadio.setSelected(true);
                break;
            default:
                rhQualquerRadio.setSelected(true);
                break;
        }

        dtSituacao.setText(doador.getSituacao().getDescricao());
        dtRhCorreto.setText("false");
    }

    @FXML
    void fecharPress(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    void removerDoadorPress(ActionEvent event) {
        // Capturando doador Clicado
        Doador selecionado = doadoresTableView.getSelectionModel().getSelectedItem();
        if(selecionado != null){
            try{
                // Abrindo conexao
                factory.abrirConexao();
                // Instanciando classe de doador
                DoadorDAO dao = factory.getDAO(DoadorDAO.class);
                String msg = dao.desativarDoador(selecionado);
                // Mostrando alerta na tela
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Info");
                alert.setHeaderText("Info");
                alert.setContentText(msg);
                alert.showAndWait();
                // Refazendo pesquisa e atualizando table view
                List<Doador> doadores = dao.buscarTodos();
                doadoresTableView.getItems().clear();
                doadoresTableView.getItems().addAll(doadores);  
            }catch(SQLException e){
                e.printStackTrace();
            }finally{
                // fechando conexao
                factory.fecharConexao();
            }
        }else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Problema");
            alert.setHeaderText("Problema");
            alert.setContentText("Voce precisa selecionar algum doador da tabela!");
            alert.showAndWait();
        }
    }

    @FXML
    void verDoacoesPress(ActionEvent event) {
        // Capturando doador Clicado
        Doador selecionado = doadoresTableView.getSelectionModel().getSelectedItem();
        if(selecionado != null){
            if (stageTelaVerDoacao.getOwner() == null) {
                stageTelaVerDoacao.initOwner(((Button)event.getSource()).getScene().getWindow());
            }
            
            try{
                // Abrindo conexao
                factory.abrirConexao();
                // Instanciando classe de doador
                DoacaoDAO dao = factory.getDAO(DoacaoDAO.class);
                // Buscando todas as doações ativas
                List<Doacao> doacoes = dao.buscarDoacoesDeDoador(selecionado);
                // Mostrando alerta na tela
                controllerVerDoacao.setDoador(selecionado);
                controllerVerDoacao.atualizarTabela(doacoes);
                stageTelaVerDoacao.showAndWait();
                
            }catch(SQLException e){
                e.printStackTrace();
            }finally{
                // fechando conexao
                factory.fecharConexao();
            }
        }else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Problema");
            alert.setHeaderText("Problema");
            alert.setContentText("Voce precisa selecionar algum doador da tabela!");
            alert.showAndWait();
        }
    }

    private Doador dcColetarTextFields(){
        // Criando objeto doador para receber dados
        Doador novoDoador = new Doador();
        // Capturando nome digitado
        if (!dcNomeDoador.getText().isEmpty()) 
        novoDoador.setNome(dcNomeDoador.getText());
        // Capturando codigo digitado
        if (!dcCodigoDoador.getText().isEmpty()) 
        novoDoador.setCodigo(Long.parseLong(dcCodigoDoador.getText()));
        // Capturando contato digitado
        if (!dcContatoDoador.getText().isEmpty()) 
        novoDoador.setContato(dcContatoDoador.getText());
        // Capturando cpf digitado
        if (!dcCpfDoador.getText().isEmpty()) 
        novoDoador.setCpf(dcCpfDoador.getText());
        // Retornando doador com campos preenchidos de acordo com o usuario
        return novoDoador;
    }
    private Doador dtColetarTextFields(){

        // Criando objeto doador para receber dados
        Doador novoDoador = new Doador();
        // Capturando nome digitado
        if (!dtNomeDoador.getText().isEmpty()) 
        novoDoador.setNome(dtNomeDoador.getText());
        // Capturando codigo digitado
        if (!dtCodigoDoador.getText().isEmpty()) 
        novoDoador.setCodigo(Long.parseLong(dtCodigoDoador.getText()));
        // Capturando contato digitado
        if (!dtContatoDoador.getText().isEmpty()) 
        novoDoador.setContato(dtContatoDoador.getText());
        // Capturando cpf digitado
        if (!dtCpfDoador.getText().isEmpty()) 
        novoDoador.setCpf(dtCpfDoador.getText());
        // // Capturando Tipo sanguineo
        RadioButton dcTipoSanguineo = (RadioButton) tipoSanguineoToggleGroup.getSelectedToggle();
        if (dcTipoSanguineo != null) {
            novoDoador.setTipoSanguineo((TipoSanguineo) dcTipoSanguineo.getUserData());
        }
        // // Capturando tipo de rh
        RadioButton dtTipoRh = (RadioButton) rhToggleGroup.getSelectedToggle();
        if (dtTipoRh != null) {
            novoDoador.setRh((RH) dtTipoRh.getUserData());
        }
        // Retornando doador com campos preenchidos de acordo com o usuario
        return novoDoador;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'initialize'");
        // ==================================================================================
        // Estabelecendo conexão com o banco de dados
        factory = new DAOFactory(new ConexaoFactoryPostgreSQL("isabelle.db.elephantsql.com:5432/cuuyxamp", "cuuyxamp", "7KnUKy1JobzJ21zzJrnDFNWRT8obkMqs"));//Banco remoto que utilizei
        // factory = new DAOFactory(new ConexaoFactoryPostgreSQL("localhost:5432/poov", "postgres", "12345")); //Banco local do PC do IF
        
        // Inicialização dos campos de texto
        dtCodigoDoador.setText("");
        dtNomeDoador.setText("");
        dtCpfDoador.setText("");
        dtContatoDoador.setText("");
        tpQualquerRadio.setSelected(true);
        rhQualquerRadio.setSelected(true);


        // Inicializando campos de radio button
        tpQualquerRadio.setUserData(null);
        tpARadio.setUserData(TipoSanguineo.A);
        tpABRadio.setUserData(TipoSanguineo.AB);
        tpBRadio.setUserData(TipoSanguineo.B);
        tpORadio.setUserData(TipoSanguineo.O);
        rhQualquerRadio.setUserData(null);
        rhPositivoRadio.setUserData(RH.POSITIVO);
        rhNegativoRadio.setUserData(RH.NEGATIVO);

        
        // Inicializando células da tabela TAB DOADORES para introduzir elementos buscados do banco
        // Tabela da aba DT (Doadores Tab)
        codigoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo().toString()));
        nomeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        cpfColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCpf()));
        contatoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContato()));
        tipoSanguineoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoSanguineo().getDescricao()));
        rhColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRh().getDescricao()));
        
        // Inicializando células da tabela TAB DOACAO para introduzir elementos buscados do banco
        dcCodigoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo().toString()));
        dcVolumeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getVolume() )));
        dcDataColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getData())));
        dcHoraColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getHora())));
        dcNomeDoadorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDoador().getNome()));
        dcCpfDoadorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDoador().getCpf()));
        dcContatoDoadorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDoador().getContato()));



        // TELAS DE CADASTRO E ALTERAR
        FXMLLoader loaderCadastro = new FXMLLoader(App.class.getResource("/poov/controledoacaosangue/telacadastrovacina.fxml"));
        FXMLLoader loaderAlterar = new FXMLLoader(App.class.getResource("/poov/controledoacaosangue/telaalterarvacina.fxml"));
        FXMLLoader loaderDoacao = new FXMLLoader(App.class.getResource("/poov/controledoacaosangue/secondary.fxml"));
        FXMLLoader loaderVerDoacao = new FXMLLoader(App.class.getResource("/poov/controledoacaosangue/verDoacoes.fxml"));
        Parent root;
        try {
            root = loaderCadastro.load();
            Scene scene = new Scene(root);
            stageTelaCadastroVacina = new Stage();
            stageTelaCadastroVacina.setScene(scene);
            stageTelaCadastroVacina.setTitle("Cadastro de Doador");
            stageTelaCadastroVacina.initModality(Modality.WINDOW_MODAL);
            controllerTelaCadastroVacina = loaderCadastro.getController();

            root = loaderAlterar.load();
            scene = new Scene(root);
            stageTelaAlterarVacina = new Stage();
            stageTelaAlterarVacina.setScene(scene);
            stageTelaAlterarVacina.setTitle("Alteração de Doador");
            stageTelaAlterarVacina.initModality(Modality.WINDOW_MODAL);
            controllerTelaAlterarVacina = loaderAlterar.getController();

            root = loaderDoacao.load();
            scene = new Scene(root);
            stageTelaCadastrarDoacao = new Stage();
            stageTelaCadastrarDoacao.setScene(scene);
            stageTelaCadastrarDoacao.setTitle("Cadastro de doação");
            stageTelaCadastrarDoacao.initModality(Modality.WINDOW_MODAL);
            controllerCadastroDoacao = loaderDoacao.getController();

            root = loaderVerDoacao.load();
            scene = new Scene(root);
            stageTelaVerDoacao = new Stage();
            stageTelaVerDoacao.setScene(scene);
            stageTelaVerDoacao.setTitle("Cadastro de doação");
            stageTelaVerDoacao.initModality(Modality.WINDOW_MODAL);
            controllerVerDoacao = loaderVerDoacao.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
