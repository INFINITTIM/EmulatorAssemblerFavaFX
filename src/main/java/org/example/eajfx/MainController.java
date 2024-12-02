package org.example.eajfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController implements IObserver
{
    ProgramModel program_model = ProgramBuilder.build();
    ExecuterModel executer_model = ExecuterBuilder.build();

    @FXML
    GridPane allviews;

    @FXML
    VBox vbox2;

    @FXML
    void initialize() {
        program_model.addObserver(this);
        try {
        RegistersController rc = new RegistersController();
        FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("RegistersView.fxml"));
        Pane pane1 = fxmlLoader1.load();

        MemoryController mc = new MemoryController();
        FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("MemoryView.fxml"));
        Pane pane2 = fxmlLoader2.load();

        PopulationTasksController pc = new PopulationTasksController();
        FXMLLoader fxmlLoader3 = new FXMLLoader(getClass().getResource("PoputationTasksView.fxml"));
        Pane pane3 = fxmlLoader3.load();

        vbox2.getChildren().addAll(pane1, pane2, pane3);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @FXML
    void AddCommand() throws IOException {
        FXMLLoader loader = new FXMLLoader(AppMain.class.getResource("AddCommandWindowView.fxml"));
        Scene scene = new Scene(loader.load());
        Stage addcommandwindow = new Stage();
        addcommandwindow.initModality(Modality.APPLICATION_MODAL);
        addcommandwindow.setTitle("AddNewCommand");
        addcommandwindow.setScene(scene);
        addcommandwindow.showAndWait();
    }

    @FXML
    void ExecuteProgram() {
        executer_model.run_program(program_model);
    }

    @FXML
    void ExecuteOneCommandInProgram() {
        executer_model.run_one_iteration_program(program_model);
    }

    @FXML
    void StopExecuteProgram()
    {
        executer_model.stop_running_program(program_model);
    }

    @Override
    public void event(ProgramModel p) {
        allviews.getChildren().clear();
        for(Command c : p)
        {
            CommandController cc = new CommandController();
            FXMLLoader fxmlLoader = new FXMLLoader(AppMain.class.getResource("CommandView.fxml"));
            fxmlLoader.setController(cc);
            try {
                Pane pane = fxmlLoader.load();
                cc.set_command(c);
                allviews.addColumn(0, pane);
            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void event(CPUModel cpu) {

    }

    @Override
    public void event(ExecuterModel ex) {

    }
}
