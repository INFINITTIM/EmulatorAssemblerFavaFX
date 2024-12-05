package org.example.eajfx;

import java.sql.*;
import java.util.Collections;

public class DAO_DB extends DAO_Memory {

    Connection con;

    void connect()
    {
        try{
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(
                    "jdbc:sqlite:command.db");
            System.out.println("Opened database successfully");
        } catch(ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void add_command(Command c)
    {
        try {
            PreparedStatement pst = con.prepareStatement(
                    "INSERT INTO AllCommands(TASK, ARG1, ARG2) VALUES (?,?,?)");
            pst.setString(1, c.getTask());
            pst.setString(2, c.getArgument1());
            pst.setString(3, c.getArgument2());
            pst.executeUpdate();
            //здесь мы берем и обновляем все id чтобы они шли по порядку
            //обновляем все записи в нашей таблице AllCommands, а именно
            //будем обновлять ID который будет равен значению подзапроса
            //записанного после знака "=" и ещё + 1 так как нумерация идёт
            //с единицы, а в подзапросе мы достаем количество всех строчек
            //в нашей таблице у которых ID (temp.ID) (временная переборная переменная)
            //меньше чем наше текущее значение ID и + 1 ещё конечно
            //делается это для того чтобы исключить каких-либо пропастей в
            //нумерации ID у команд в таблице которые неизбежно появляются
            //при добавлении и удалении элемента
            PreparedStatement pst2 = con.prepareStatement(
                    "UPDATE AllCommands SET ID = (SELECT COUNT(*) FROM AllCommands AS temp WHERE temp.ID < AllCommands.ID) + 1;");
            pst2.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        updateList();
    }

    public void delete_command(Command c)
    {
        try {
            PreparedStatement pst = con.prepareStatement(
                    "DELETE FROM AllCommands WHERE ID = ?");
            pst.setInt(1, c.getID());
            pst.executeUpdate();
            PreparedStatement pst2 = con.prepareStatement(
                    "UPDATE AllCommands SET ID = (SELECT COUNT(*) FROM AllCommands AS temp WHERE temp.ID < AllCommands.ID) + 1;");
            pst2.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        updateList();
    }

    public void swap_up_command(Command c) {
        try {
            int current_id = c.getID();
            int past_id = current_id - 1;
            String task = "";
            String arg1 = "";
            String arg2 = "";

            if(current_id > 1)
            {
                PreparedStatement pst = con.prepareStatement(
                        "SELECT TASK, ARG1, ARG2 FROM AllCommands WHERE ID=?");
                pst.setInt(1, past_id);
                ResultSet r = pst.executeQuery();
                while(r.next())
                {
                    task = r.getString("TASK");
                    arg1 = r.getString("ARG1");
                    arg2 = r.getString("ARG2");
                }
                PreparedStatement pst2 = con.prepareStatement(
                        "UPDATE AllCommands SET TASK=?, ARG1=?, ARG2=? WHERE ID=?");
                pst2.setString(1, c.getTask());
                pst2.setString(2, c.getArgument1());
                pst2.setString(3, c.getArgument2());
                pst2.setInt(4, past_id);
                pst2.executeUpdate();
                PreparedStatement pst3 = con.prepareStatement(
                        "UPDATE AllCommands SET TASK=?, ARG1=?, ARG2=? WHERE ID=?");
                pst3.setString(1, task);
                pst3.setString(2, arg1);
                pst3.setString(3, arg2);
                pst3.setInt(4, c.getID());
                pst3.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        updateList();
    }

    public void swap_down_command(Command c) {
        try {
            int current_id = c.getID();
            int past_id = current_id + 1;
            String task = "";
            String arg1 = "";
            String arg2 = "";

            if(current_id < program_commands.size())
            {
                PreparedStatement pst = con.prepareStatement(
                        "SELECT TASK, ARG1, ARG2 FROM AllCommands WHERE ID=?");
                pst.setInt(1, past_id);
                ResultSet r = pst.executeQuery();
                while(r.next())
                {
                    task = r.getString("TASK");
                    arg1 = r.getString("ARG1");
                    arg2 = r.getString("ARG2");
                }
                PreparedStatement pst2 = con.prepareStatement(
                        "UPDATE AllCommands SET TASK=?, ARG1=?, ARG2=? WHERE ID=?");
                pst2.setString(1, c.getTask());
                pst2.setString(2, c.getArgument1());
                pst2.setString(3, c.getArgument2());
                pst2.setInt(4, past_id);
                pst2.executeUpdate();
                PreparedStatement pst3 = con.prepareStatement(
                        "UPDATE AllCommands SET TASK=?, ARG1=?, ARG2=? WHERE ID=?");
                pst3.setString(1, task);
                pst3.setString(2, arg1);
                pst3.setString(3, arg2);
                pst3.setInt(4, c.getID());
                pst3.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        updateList();
    }


    DAO_DB() {
        connect();
        updateList();
    }

    protected void updateList() {
        try {
            program_commands.clear();
            Statement st = con.createStatement();
            ResultSet r = st.executeQuery("select * from AllCommands");
        while(r.next()) {
            program_commands.add(new Command(
                    r.getString("TASK"),
                    r.getString("ARG1"),
                    r.getString("ARG2"),
                    r.getInt("ID")
            ));
        }
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
