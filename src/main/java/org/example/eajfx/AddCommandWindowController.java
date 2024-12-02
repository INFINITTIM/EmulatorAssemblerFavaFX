package org.example.eajfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddCommandWindowController
{
    ProgramModel program_model = ProgramBuilder.build();

    @FXML
    TextField task;

    @FXML
    TextField arg1;

    @FXML
    TextField arg2;

    @FXML
    void add_new_command()
    {
        if (!task.getText().equals("print") &&
                !task.getText().equals("ld") &&
                !task.getText().equals("st") &&
                !task.getText().equals("mv") &&
                !task.getText().equals("div") &&
                !task.getText().equals("add") &&
                !task.getText().equals("sub") &&
                !task.getText().equals("init") &&
                !task.getText().equals("mult"))
        {
            try {
                FXMLLoader loader = new FXMLLoader(AppMain.class.getResource("ExceptionNewTask.fxml"));
                Scene scene = new Scene(loader.load());
                Stage error1window = new Stage();
                error1window.initModality(Modality.APPLICATION_MODAL);
                error1window.setTitle("Error");
                error1window.setScene(scene);
                error1window.showAndWait();
            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
        else if (((task.getText().equals("ld") || task.getText().equals("st")) &&
                (Integer.parseInt(arg2.getText()) < 1 || Integer.parseInt(arg2.getText()) > 1024)) ||
                (task.getText().equals("init") && (Integer.parseInt(arg1.getText()) < 1 || Integer.parseInt(arg1.getText()) > 1024)))
        {
            try {
                FXMLLoader loader = new FXMLLoader(AppMain.class.getResource("ExceptionNewArgMemory.fxml"));
                Scene scene = new Scene(loader.load());
                Stage error2window = new Stage();
                error2window.initModality(Modality.APPLICATION_MODAL);
                error2window.setTitle("Error");
                error2window.setScene(scene);
                error2window.showAndWait();
            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
        else
        {
            program_model.add_command(new Command(task.getText(), arg1.getText(), arg2.getText()));

            Stage stage = (Stage)task.getScene().getWindow(); // Получаем текущее окно
            stage.close();
        }
    }

    @FXML
    void initialize()
    {
        arg1.setDisable(true);
        arg2.setDisable(true);

        task.setOnAction(event -> {
            if(task.getText().equals("ld") ||
                    task.getText().equals("st") ||
                    task.getText().equals("mv") ||
                    task.getText().equals("div") ||
                    task.getText().equals("add") ||
                    task.getText().equals("sub") ||
                    task.getText().equals("init") ||
                    task.getText().equals("mult"))
            {
                arg1.setDisable(false);
                arg2.setDisable(false);
            }
            else
            {
                arg1.setDisable(true);
                arg2.setDisable(true);
            }
        });
    }
}
