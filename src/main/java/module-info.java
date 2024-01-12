module com.chicha.minesweeper {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.chicha.minesweeper to javafx.fxml;
    exports com.chicha.minesweeper;
}