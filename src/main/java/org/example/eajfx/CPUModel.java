package org.example.eajfx;

import java.util.ArrayList;
import java.util.Arrays;

public class CPUModel implements ICPU
{
    private int[] registers = new int[4];
    private int[] memory = new int[1024];

    ArrayList<IObserver> allObserver = new ArrayList<>();

    void eventCall(){
        allObserver.forEach(action->action.event(this));
    }

    public void addObserver(IObserver e){
        allObserver.add(e);
        eventCall();
    }

    @Override
    public void exec(Command command) throws Exception_div_zero {
        switch (command.getTask())
        {
            case "ld" :
                //загрузка данных из памяти в регистр
                try {
                    int this_register = get_register_index(command.getArgument1());
                    int memory_address = Integer.parseInt(command.getArgument2());
                    registers[this_register] = memory[memory_address-1];
                } catch (Exception_register e) {
                    System.err.println("Ошибка: " + e.getMessage());
                }
                break;
            case "st" :
                //загрузка данных из регистра в память
                try {
                    int this_register = get_register_index(command.getArgument1());
                    int memory_address = Integer.parseInt(command.getArgument2());
                    memory[memory_address-1] = registers[this_register];
                } catch (Exception_register e) {
                    System.err.println("Ошибка: " + e.getMessage());
                }
                break;
            case "mv" :
                //копирование значения из регистра два в регистр один
                try {
                    int new_register = get_register_index(command.getArgument1());
                    int old_register = get_register_index(command.getArgument2());
                    registers[new_register] = registers[old_register];
                } catch (Exception_register e) {
                    System.err.println("Ошибка: " + e.getMessage());
                }
                break;
            case "init" :
                //инициализация адреса в памяти значением
                int memory_address = Integer.parseInt(command.getArgument1());
                int value = Integer.parseInt(command.getArgument2());
                memory[memory_address-1] = value;
                break;
            case "print" :
                for(int i = 0; i < registers.length; i++)
                    System.out.print(registers[i] + " ");
                System.out.println();
                break;
            case "add" :
                registers[3] = registers[0] + registers[1];
                break;
            case "sub" :
                registers[3] = registers[0] - registers[1];
                break;
            case "mult" :
                registers[3] = registers[0] * registers[1];
                break;
            case "div" :
                if (registers[1] == 0)
                    throw new Exception_div_zero("Деление на ноль");
                registers[3] = registers[0] / registers[1];
                break;
            default:
                System.out.println("Нет данной команды");
        }
        eventCall();
    }

    public int getRegister_1() {
        return registers[0];
    }

    public int getRegister_2() {
        return registers[1];
    }

    public int getRegister_3() {
        return registers[2];
    }

    public int getRegister_4() {
        return registers[3];
    }

    public int getMemory(int address) {
        return memory[address];
    }

    private int get_register_index(String register_name) throws Exception_register {
        switch (register_name) {
            case "a":
                return 0;
            case "b":
                return 1;
            case "c":
                return 2;
            case "d":
                return 3;
            default:
                throw new Exception_register("Некорректный аргумент(регистр): " + register_name);
        }
    }

    public void all_clear() {
        Arrays.fill(registers, 0);
        Arrays.fill(memory, 0);
        eventCall();
    }
}
