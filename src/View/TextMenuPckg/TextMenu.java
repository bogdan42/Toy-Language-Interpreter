package View.TextMenuPckg;

import View.CommandPckg.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private Map<String, Command> commands;

    public TextMenu(){
        this.commands=new HashMap<>();
    }

    public void addCommand(Command command){
        this.commands.put(command.getKey(),command);
    }

    public void printMenu(){
        for(Command com: commands.values()){
            String line = String.format("%4s: %s",com.getKey(),com.getDescription());
            System.out.println(line);
        }
    }

    public void show(){
        Scanner scan = new Scanner(System.in);
        while(true) {
            printMenu();
            System.out.println("Input command: ");
            String commandKey = scan.nextLine();
            Command userCommand = commands.get(commandKey);

            if(userCommand == null)
                System.out.println("Invalid command!");
            else
                userCommand.execute();
        }
    }
}
