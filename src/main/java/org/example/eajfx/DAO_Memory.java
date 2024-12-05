package org.example.eajfx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class DAO_Memory implements Iterable<Command>{
    protected
    List<Command> program_commands = new ArrayList<>();

    public void add_command(Command c)
    {
        program_commands.add(c);
    }

    public void delete_command(Command c)
    {
        program_commands.remove(c);
    }

    public void swap_up_command(Command c) {
        int index = program_commands.indexOf(c);
        if (index > 0) { // Проверяем, что команда не первая
            Collections.swap(program_commands, index, index - 1);
        }
    }

    public void swap_down_command(Command c) {
        int index = program_commands.indexOf(c);
        if (index >= 0 && index < program_commands.size() - 1) { // Проверяем, что команда не последняя
            Collections.swap(program_commands, index, index + 1);
        }
    }

    @Override
    public Iterator<Command> iterator() {
        return program_commands.iterator();
    }
}
