package org.example.eajfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RegistersController implements IObserver
{
    CPUModel cpu_model = CPUBuilder.build();

    @FXML
    Label l1;

    @FXML
    Label l2;

    @FXML
    Label l3;

    @FXML
    Label l4;

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
        l1.setText(Integer.toString(cpu_model.getRegister_1()));
        l2.setText(Integer.toString(cpu_model.getRegister_2()));
        l3.setText(Integer.toString(cpu_model.getRegister_3()));
        l4.setText(Integer.toString(cpu_model.getRegister_4()));
    }

    @Override
    public void event(ExecuterModel ex) {

    }
}
