package org.example.eajfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class CommandController implements IObserver
{
    ProgramModel program_model = ProgramBuilder.build();
    ExecuterModel executer_model = ExecuterBuilder.build();

    @FXML
    Label Command;

    @FXML
    Label Argument1;

    @FXML
    Label Argument2;

    Command c;

    public void set_command(Command c)
    {
        this.c = c;
        Command.setText(c.getTask());
        Argument1.setText(c.getArgument1());
        Argument2.setText(c.getArgument2());
    }

    @FXML
    void delete_command()
    {
        program_model.delete_command(c);
    }

    @FXML
    void up_command()
    {
        program_model.swap_up_command(c);
    }

    @FXML
    void down_command()
    {
        program_model.swap_down_command(c);
    }

    @FXML
    void initialize()
    {
        executer_model.addObserver(this);
    }

    @Override
    public void event(ProgramModel p) {

    }

    @Override
    public void event(CPUModel cpu) {
    }

    @Override
    public void event(ExecuterModel ex) {
        if(ex.get_index_command_exec_now() == program_model.get_command_index(c) && program_model.get_command_index(c) != -1)
        {
            Command.setTextFill(Color.RED);
            Argument1.setTextFill(Color.RED);
            Argument2.setTextFill(Color.RED);
        }
        else
        {
            Command.setTextFill(Color.BLACK);
            Argument1.setTextFill(Color.BLACK);
            Argument2.setTextFill(Color.BLACK);
        }
        System.out.println(ex.get_index_command_exec_now());
        System.out.println(program_model.get_command_index(c));
    }
}
