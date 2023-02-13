module com.example.project3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens oitkmg.project3 to javafx.fxml;
    exports oitkmg.project3;
}