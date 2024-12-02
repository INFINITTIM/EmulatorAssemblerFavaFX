package org.example.eajfx;

import java.util.*;
import java.util.stream.Collectors;

public class ProgramModel implements Iterable<Command>
{
    List<Command> program_commands = new ArrayList<>();
    int max_command_index = 0;
    ArrayList<IObserver> allObserver = new ArrayList<>();

    void eventCall(){
        allObserver.forEach(action->action.event(this));
    }

    public void addObserver(IObserver e){
        allObserver.add(e);
        eventCall();
    }

    public void add_command(Command c)
    {
        program_commands.add(c);
        max_command_index++;
        eventCall();
    }

    public void delete_command(Command c)
    {
        program_commands.remove(c);
        max_command_index--;
        eventCall();
    }

    public int get_command_index(Command c)
    {
        return (program_commands.indexOf(c));
    }

    public void swap_up_command(Command c) {
        int index = program_commands.indexOf(c);
        if (index > 0) { // Проверяем, что команда не первая
            Collections.swap(program_commands, index, index - 1);
        }
        eventCall();
    }

    public void swap_down_command(Command c) {
        int index = program_commands.indexOf(c);
        if (index >= 0 && index < program_commands.size() - 1) { // Проверяем, что команда не последняя
            Collections.swap(program_commands, index, index + 1);
        }
        eventCall();
    }

    @Override
    public Iterator<Command> iterator() {
        return program_commands.iterator();
    }

    private Map<String, Long> MapWithPopulationTasks()
    {
        //мап инскрукция и кол-во вхождений данной инструкции
        Map<String, Long> map_with_population_tasks = program_commands
                .stream()
                .collect(Collectors.groupingBy(Command::getTask, Collectors.counting()));
        return map_with_population_tasks;
    }

    public Map<List<String>, Long> PopulationTasks()
    {
        //максимальное значение вхождений какой-либо инструкции
        Long max_count = Collections.max(MapWithPopulationTasks().values());

        //мап с самыми часто встречающимися инструкциями и их кол-вом вхождений
        Map<List<String>, Long> map_with_most_population_tasks = new HashMap<>();
        map_with_most_population_tasks
                .put(MapWithPopulationTasks().entrySet()
                        .stream()
                        .filter(element -> element.getValue().equals(max_count))
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList()), max_count);
        return map_with_most_population_tasks;
    }

    public List<String> ListWithTasksAndCounters()
    {
        List<Map.Entry<String, Long>> list_with_tasks_and_counters = MapWithPopulationTasks()
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toList());
        List<String> l = new ArrayList<>();
        for (Map.Entry<String, Long> e : list_with_tasks_and_counters)
        {
            String s = e.getKey() + " " + String.valueOf(e.getValue());
            l.add(s);
        }
        return l;
    }

    public String ListDiap() {
        List<Integer> list_diap = new ArrayList<>();
        list_diap = program_commands
                .stream()
                .filter(command -> (command.getTask()=="init")
                        ||  (command.getTask()=="ld")
                        || (command.getTask()=="st"))
                .map(command -> command.getTask() == "init" ?
                        Integer.parseInt(command.getArgument1()) :
                        Integer.parseInt(command.getArgument2()))
                .collect(Collectors.toList());
        int min = Collections.min(list_diap);
        int max = Collections.max(list_diap);
        return "Диапазон памяти: " + min + "..." + max;
    }
}