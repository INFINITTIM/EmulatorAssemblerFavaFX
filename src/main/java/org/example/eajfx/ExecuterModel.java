package org.example.eajfx;

import java.util.ArrayList;

public class ExecuterModel
{
    CPUModel cpu = CPUBuilder.build();

    private int index_command_exec_now = -1;

    ArrayList<IObserver> allObserver = new ArrayList<>();

    void eventCall(){
        allObserver.forEach(action->action.event(this));
    }

    public void addObserver(IObserver e){
        allObserver.add(e);
        eventCall();
    }

    public int get_index_command_exec_now()
    {
        return index_command_exec_now;
    }

    public void run_program(ProgramModel prog) {
        for (Command command : prog) {
            try {
                index_command_exec_now++;
                cpu.exec(command);
            } catch (Exception_div_zero e) {
                System.err.println("Ошибка: " + e.getMessage());
            }
        }
        eventCall();
    }

    public void run_one_iteration_program(ProgramModel prog)
    {
        if(index_command_exec_now != prog.dao.program_commands.size())
            index_command_exec_now++;
        for (Command command : prog)
        {
            try {
                if(prog.dao.program_commands.indexOf(command) == index_command_exec_now)
                    cpu.exec(command);
            } catch (Exception_div_zero e) {
                System.err.println("Ошибка: " + e.getMessage());
            }
        }
        eventCall();
    }

    public void stop_running_program(ProgramModel prog)
    {
        index_command_exec_now = -1;
        cpu.all_clear();
        eventCall();
    }
}
