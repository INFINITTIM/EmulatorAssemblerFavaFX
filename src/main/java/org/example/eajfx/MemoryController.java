package org.example.eajfx;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public class MemoryController implements IObserver
{
    CPUModel cpu_model = CPUBuilder.build();

    @FXML
    GridPane memory_container;

    @FXML
    void initialize()
    {
        cpu_model.addObserver(this);
    }

    @Override
    public void event(ProgramModel p) {

    }

    @Override
    public void event(CPUModel cpu) {
        int ii = 0;
        int jj = 0;
        memory_container.getChildren().clear();
        for(int i = 1; i <=1024; i++)
        {
            Label address_memory = new Label(i + ":" + cpu.getMemory(i-1));
            if(i%4 == 1 && i > 4)
            {
                ii = 0;
                jj++;
            }
            if(cpu.getMemory(i-1) > 0)
            {
                address_memory.setTextFill(Color.RED);
            }
            else
            {
                address_memory.setTextFill(Color.BLACK);
            }
            memory_container.add(address_memory, ii, jj);
            ii++;
        }
    }

    @Override
    public void event(ExecuterModel ex) {

    }
}
