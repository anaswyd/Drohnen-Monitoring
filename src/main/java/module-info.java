module de.uas.fra.project.group25.javaproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens de.uas.fra.project.group25.javaproject to javafx.fxml;
    exports de.uas.fra.project.group25.javaproject;
    exports de.uas.fra.project.group25.javaproject.Controllers;
    opens de.uas.fra.project.group25.javaproject.Controllers to javafx.fxml;
}