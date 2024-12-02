package org.example.eajfx;

public class Command
{
    String task;
    String argument1;
    String argument2;

    public Command(String task) {
        this.task = task;
    }

    public Command(String task, String argument1, String argument2) {
        this.task = task;
        this.argument1 = argument1;
        this.argument2 = argument2;
    }

    public String getTask() {
        return task;
    }

    public String getArgument1() {
        return argument1;
    }

    public String getArgument2() {
        return argument2;
    }

    @Override
    public String toString() {
        if (argument1 != null && argument2 != null) {
            return task + " " + argument1 + " " + argument2;
        } else if (argument1 != null) {
            return task + " " + argument1;
        } else {
            return task;
        }
    }
}
