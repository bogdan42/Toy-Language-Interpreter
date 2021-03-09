package View.CommandPckg;

import ControllerPack.Controller;
import Model.Exception.MyException.MyException;

public class RunExampleCommand extends Command {
    private String key;
    private String description;
    private Controller controller;

    public RunExampleCommand(String key,String description,Controller contr){
        super(key,description);
        this.controller=contr;
    }
    @Override
    public void execute() {
        try{
            controller.allStep();
        }
        catch (MyException | InterruptedException exc){
            System.out.println("--------------------------------------------------------------------------------------------------------------------------");
            System.out.println(exc.getMessage());
            System.out.println("---------------------------------------------------------------------------------------------------------------------------");
        }

    }
}
