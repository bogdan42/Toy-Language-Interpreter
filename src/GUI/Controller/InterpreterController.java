package GUI.Controller;
import Collection.Dictionary.MyDictionaryInterface;
import Collection.Heap.HeapInterface;
import Collection.Stack.MyStackInterface;
import ControllerPack.Controller;
import Model.Exception.MyException.MyException;
import Model.ProgramState;
import Model.Statement.StatementInterface;
import Model.Values.Value;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class InterpreterController {
    private Integer currentThreadID = -1;
    private Controller controller;
    private Stage self=null;

    @FXML
    private TextField numberOfProgramStatesTxtField;
    @FXML
    private Label textLabelPrgStates;

    @FXML
    private Button oneStepButton;

    //DATA RELATED TO HEAP TABLE
    @FXML
    private TableView<Map.Entry<Integer, String>> HeapTable;

    @FXML
    private TableColumn<Map.Entry<Integer,String>,Integer> addressHeapTable;

    @FXML
    private TableColumn<Map.Entry<Integer,String>,String> valueHeapTable;
    //DATA RELATED TO OUT TABLE
    @FXML
    private ListView<String> outListView;

    //DATA RELATED TO FILE TABLE
    @FXML
    private ListView<?> fileTableListView;

    //DATA RELATED TO THREAD ID

    @FXML
    private ListView<Integer> threadIDListView;

    //DATA RELATED TO SYMBOL TABLE

    @FXML
    private TableView<Map.Entry<String,String>> symbolTableView;

    @FXML
    private TableColumn<Map.Entry<String,String>,String> variableColumn;

    @FXML
    private TableColumn<Map.Entry<String,String>,String> valueSymbolCollumn;

    //DATA RELATED TO  EXECUTION STACK LIST
    @FXML
    private ListView<String> exeStackListView;
    @FXML
    void oneStepButtonClicked(MouseEvent event) {
        try {
            if(currentThreadID==-1)
                currentThreadID=controller.getListOfThreadIDs().get(0);
            if(!controller.getListOfThreadIDs().contains(currentThreadID))
                currentThreadID=controller.getListOfThreadIDs().get(0);

            controller.oneStepGUI();
            numberOfProgramStatesTxtField.setText(controller.getNumberOfProgramStates().toString());
            populate();
            if(controller.getNumberOfProgramStates()==0){
                Alert  endOfExecution = new Alert(Alert.AlertType.INFORMATION);
                endOfExecution.setResizable(true);
                endOfExecution.setHeaderText("End of Program Execution!");
                endOfExecution.setContentText("Window Will Close Now!");
                endOfExecution.showAndWait();
                self.close();
            }
            if(!controller.getListOfThreadIDs().contains(currentThreadID))
                currentThreadID=controller.getListOfThreadIDs().get(0);
        }
        catch (MyException | InterruptedException e){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setResizable(true);
            errorAlert.setHeaderText("Execution Error");
            this.currentThreadID=-1;
            String exc = "Type:\n";
            exc = exc.concat(e.getMessage().replace(":","\nMessage:\n"));
            errorAlert.setContentText(exc+"Window will close!");
            errorAlert.showAndWait();
            self.close();
        }

    }

    public void setProgramController(Controller controller){
        this.controller=controller;
        this.numberOfProgramStatesTxtField.setText(Integer.toString(controller.getNumberOfProgramStates()));
    }

    @FXML
    public void initialize(){
        numberOfProgramStatesTxtField.setEditable(false);
        addressHeapTable.setCellValueFactory(p->new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        valueHeapTable.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue() + ""));

        variableColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey() + ""));
        valueSymbolCollumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue() + ""));
    }

    @FXML
    private void selectedThreadID(MouseEvent event){
        this.currentThreadID = threadIDListView.getSelectionModel().getSelectedItem();
        if(controller.getListOfThreadIDs().contains(currentThreadID))
            populateExecutionStack(controller.getProgramWithID(currentThreadID));
        else
            currentThreadID=controller.getListOfThreadIDs().get(0);

    }

    //FUNCTION THAT UPDATES ALL DATA
    private void populate(){
        this.numberOfProgramStatesTxtField.setText(controller.getNumberOfProgramStates().toString());
        populateThreadIDList();
        populateHeapTable();
        populateOutList();
        populateExecutionStack(controller.getProgramWithID(currentThreadID));
        populateSymbolTable(controller.getProgramWithID(currentThreadID));
    }
    //function that populates the threadID_ListView
    private void populateThreadIDList(){
        ObservableList<Integer>  data = FXCollections.observableArrayList();
        data.addAll(controller.getListOfThreadIDs());
        threadIDListView.setItems(data);
    }

    private void populateHeapTable(){
        HeapInterface<Value> originalHeap = controller.getHeapTable();
        Map<Integer,String> heap = new HashMap<>();

        for(Integer k:originalHeap.getContent().keySet())
            heap.put(k,originalHeap.getContent().get(k).toString());

        Map<Integer,String> heapMap = new HashMap<>();
        for(Map.Entry<Integer,String> entry : heap.entrySet())
            heapMap.put(entry.getKey(),entry.getValue());

        List<Map.Entry<Integer,String>> heapTableList = new ArrayList<>(heapMap.entrySet());
        this.HeapTable.setItems(FXCollections.observableList(heapTableList));
        this.HeapTable.refresh();

    }

    private void populateOutList(){
        List<String> outStr = controller.getOutTable().getAll().stream().map(Object::toString).collect(Collectors.toList());
        ObservableList<String> outList = FXCollections.observableArrayList(outStr);
        this.outListView.setItems(outList);
    }

    private void populateExecutionStack(ProgramState program){
        MyStackInterface<StatementInterface> currentStack = program.getExeStack();
        List<String> statements = new ArrayList<>();
        for(StatementInterface statement : currentStack.getElems())
            statements.add(statement.toString());
        this.exeStackListView.setItems(FXCollections.observableList(statements));

    }

    private void populateSymbolTable(ProgramState program){
        MyDictionaryInterface<String,Value> symbol_table = program.getSymTable();
        Map<String,String> symbolTable= new HashMap<>();

        for(String key:symbol_table.keySet())
            symbolTable.put(key,symbol_table.get(key).toString());

        Map<String,String> symbolMap = new HashMap<>();
        for(Map.Entry<String,String> entry: symbolTable.entrySet())
            symbolMap.put(entry.getKey(),entry.getValue());


        List<Map.Entry<String,String>> symbolList = new ArrayList<>(symbolMap.entrySet());
        this.symbolTableView.setItems(FXCollections.observableList(symbolList));
        this.symbolTableView.refresh();

    }


    public void setPage(Stage stage){
        this.self=stage;
    }
}
