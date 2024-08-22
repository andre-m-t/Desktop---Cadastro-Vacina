module JavaFXApplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires java.sql;

    opens poov.controledoacaosangue to javafx.fxml;
    opens poov.controledoacaosangue.Controller to javafx.fxml, javafx.graphics;
    opens poov.controledoacaosangue.DAO to javafx.fxml, javafx.graphics;
    opens poov.controledoacaosangue.DAO.core to javafx.fxml, javafx.graphics;
    opens poov.controledoacaosangue.Model to javafx.base, javafx.fxml, javafx.graphics;
    exports poov.controledoacaosangue;
}