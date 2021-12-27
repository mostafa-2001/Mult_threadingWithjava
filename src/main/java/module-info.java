module com.example.project_os {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.project_os to javafx.fxml;
    exports com.example.project_os;
}