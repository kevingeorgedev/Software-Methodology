module oitkmg.project4.project4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;


    opens oitkmg.project4 to javafx.fxml;
    exports oitkmg.project4;
}