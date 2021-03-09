package RepositoryPack;

import Collection.List.MyListInterface;
import Model.Exception.MyException.MyException;
import Model.ProgramState;

import java.util.ArrayList;
import java.util.List;

public interface RepositoryInterface {

    void logProgramStateExecute(ProgramState program) throws MyException;

    void logStartMessage() throws MyException;

    void logErrorMessage(String errorMessage);

    //a
    List<ProgramState> getListProgram();

    void setProgramList(List<ProgramState> newList);
}

