package RepositoryPack;

import Collection.List.MyList;
import Collection.List.MyListInterface;
import Model.Exception.MyException.MyException;
import Model.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements RepositoryInterface {

    private MyListInterface<ProgramState> programs;
    private String logFilePath;
    public Repository(ProgramState progr){
        this.programs = new MyList<>();
        this.logFilePath="";
        this.programs.add(progr);
    }

    public Repository(ProgramState progr,String logFilePath){
        this.programs=new MyList<>();
        this.programs.add(progr);
        this.logFilePath=logFilePath;
    }

    @Override
    public void logProgramStateExecute(ProgramState program) throws MyException {
        try{
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath,true)));
            //for now we have only one prgr. state
            logFile.write(program.toString());
            logFile.close();

        }catch (IOException er){
            throw new  MyException("Failed to write to log file!");
        }
    }

    @Override
    public void logStartMessage() throws MyException {
        try{
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath,true)));
            //for now we have only one prgr. state
            logFile.write("                  STARTED EXECUTION\n");
            logFile.close();

        }catch (IOException er){
            throw new  MyException("Failed to write to log file!");
        }
    }

    @Override
    public void logErrorMessage(String errorMessage) {
        try{
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath,true)));
            //for now we have only one prgr. state
            logFile.write("                  ERROR MESSAGE:\n");
            logFile.write(errorMessage);
            logFile.write("\n            PROGRAM EXECUTION HAS STOPED\n");
            logFile.close();

        }catch (IOException er){
            throw new  MyException("Failed to write to log file!");
        }
    }

    @Override
    public List<ProgramState> getListProgram() {
        return programs.getAll();
    }

    @Override
    public void setProgramList(List<ProgramState> newList) {
        this.programs.setAll(newList);
    }
}

