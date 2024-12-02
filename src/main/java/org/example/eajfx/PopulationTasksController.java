package org.example.eajfx;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.Map;

public class PopulationTasksController implements IObserver
{
    ProgramModel program_model = ProgramBuilder.build();

    @FXML
    GridPane gridpanefortasks;

    @FXML
    void initialize()
    {
        program_model.addObserver(this);
    }

    @Override
    public void event(ProgramModel p) {
        gridpanefortasks.getChildren().clear();
        int counter_row = 0;
        for (String s : program_model.ListWithTasksAndCounters())
        {
            Label taskLabel = new Label(s);
            gridpanefortasks.add(taskLabel, 0, counter_row);
            counter_row++;
        }
    }

    @Override
    public void event(CPUModel cpu) {

    }

    @Override
    public void event(ExecuterModel ex) {

    }
}
