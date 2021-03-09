package ControllerPack;
import Collection.Heap.HeapInterface;
import Collection.List.MyListInterface;
import Model.Exception.MyException.MyException;
import Model.ProgramState;
import Model.Values.ReferenceValue;
import Model.Values.Value;
import RepositoryPack.RepositoryInterface;
import View.Interpreter;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;


public class Controller {
    private RepositoryInterface repo;
    private ExecutorService executor = null;
    public Controller(RepositoryInterface r){
        this.repo=r;
    }


    public void allStep() throws MyException, InterruptedException {
        this.executor= Executors.newFixedThreadPool(2);
        List<ProgramState> programs=removeCompleted(repo.getListProgram());
        //repo.logStartMessage();
        try {
            if (programs.size() > 0) {
                executeGarbageCollector();
                oneStepForAllPrograms(programs);
                programs = removeCompleted(repo.getListProgram());
            }
        }
        catch (MyException exc){
            repo.logErrorMessage(exc.getMessage());
            throw new MyException(exc.getMessage());
        }
        executor.shutdownNow();
        repo.setProgramList(programs);

    }

    private void executeGarbageCollector(){
        //firstly we work with the sym table
        List<ProgramState> programs = repo.getListProgram();
        List<List<Integer>> addrSymTableList = programs.stream().map(ProgramState::getSymTable).map(p->getAddrFromSymTable(p.values())).collect(Collectors.toList());
        List<Integer> addresses= new ArrayList<Integer>();
        addrSymTableList.forEach(addresses::addAll);
        List<Integer> add2 = getAddrFromHeap(programs.get(0).getHeap().values());
        addresses.addAll(add2);
        Map<Integer,Value> garbCollector = unsafeGarbageCollector(addresses,programs.get(0).getHeap().getContent());
        programs.forEach(p->p.getHeap().setContent(garbCollector));
    }
    private Map<Integer, Value> unsafeGarbageCollector(List<Integer> addresses, Map<Integer,Value> heap){
        return heap.entrySet().stream().filter(e->addresses.contains(e.getKey())).
                collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }

    private List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream().
                filter(v->v instanceof ReferenceValue).
                map(v->((ReferenceValue)v).getAddress()).
                collect(Collectors.toList());
    }

    private List<Integer> getAddrFromHeap(Collection<Value> heapValues){
        return heapValues.stream().
                filter(v->v instanceof ReferenceValue)
                .map(v->((ReferenceValue)v).getAddress())
                .collect(Collectors.toList());
    }

    //a5
    List<ProgramState> removeCompleted(List<ProgramState> inProgramList){
        return inProgramList.stream().filter(ProgramState::isNotCompleted).collect(Collectors.toList());
    }

    public void oneStepForAllPrograms(List<ProgramState> programs) throws InterruptedException {

        programs.forEach(prg->{repo.logProgramStateExecute(prg);
            System.out.println(prg);});

        List<Callable<ProgramState>> callList = programs.stream().filter(p->!p.getExeStack().isEmpty()).
                map((ProgramState p)->(Callable<ProgramState>)(p::oneStep)).collect(Collectors.toList());

        List<ProgramState> newProgramList = executor.invokeAll(callList).stream().map(future->{
            try{
                return future.get();
            }
            catch (MyException | InterruptedException | ExecutionException exc){
                throw new MyException(exc.getMessage());
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
        programs.addAll(newProgramList);
        programs.forEach(prg->{
            System.out.println(prg);repo.logProgramStateExecute(prg);});
        repo.setProgramList(programs);
    }

    public Integer getNumberOfProgramStates(){
        Integer nr = 0;
        for(ProgramState p: repo.getListProgram())
            if(!p.getExeStack().isEmpty()){
                nr++;
            }
        return nr;
    }

    //NEW FUNCTIONS
    public List<Integer> getListOfThreadIDs(){
        return repo.getListProgram().stream().map(ProgramState::getThreadID).collect(Collectors.toList());
    }

    public HeapInterface<Value> getHeapTable(){
        return repo.getListProgram().get(0).getHeap();
    }

    public MyListInterface<Value> getOutTable(){
        return repo.getListProgram().get(0).getOut();
    }

    public ProgramState getProgramWithID(Integer id){
        if(id==null)
            return null;
        for(ProgramState pro: repo.getListProgram())
            if(pro.getThreadID() == id.intValue())
                return pro;
        return null;
    }


    public void oneStepGUI() throws InterruptedException {
        executor = Executors.newFixedThreadPool(5);
        removeCompleted(repo.getListProgram());
        List<ProgramState> programs = repo.getListProgram();

        if(programs.size()>0){
            executeGarbageCollector();
            oneStepForAllPrograms(programs);
            removeCompleted(repo.getListProgram());
            executor.shutdownNow();
        }
    }
}

