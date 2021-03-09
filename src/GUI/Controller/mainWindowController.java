package GUI.Controller;

import Collection.Dictionary.MyDictionary;
import Collection.List.MyList;
import Collection.Stack.MyStack;
import ControllerPack.Controller;
import Model.Exception.MyException.MyException;
import Model.ProgramState;
import Model.Statement.StatementInterface;
import RepositoryPack.Repository;
import RepositoryPack.RepositoryInterface;
import View.Interpreter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class mainWindowController{
    private ArrayList<StatementInterface> options=null;
    private Integer index = null;
    private Stage startWindow = null;
    private Stage mainPage=null;
    @FXML
    private ListView<String> programList;
    @FXML
    private Button executeButton;

    @FXML
    private Label textLabel;


    @FXML
    private TextArea textArea;

    @FXML
    public void initialize(){
        textArea.setEditable(false);
        textLabel.setFont(new Font(15));
        textLabel.setText("ToyLanguage Interpreter \nAlege un program\n Si apasa: \n EXECUTE!");
        textLabel.setTextAlignment(TextAlignment.CENTER);
        textLabel.autosize();
        this.options= Interpreter.getAll();
        this.populate();
    }


    public void itemClicked(MouseEvent mouseEvent) {
        this.index = programList.getSelectionModel().getSelectedIndex();
        textArea.setFont(new Font(15));
        textArea.setText("          Selected Program:\n");
        textArea.appendText("\n\n");
        textArea.appendText(niceShow(options.get(index).toString()));
    }

    @FXML
    public void executeClicked(MouseEvent mouseEvent) throws IOException {
        System.out.println(index);
        if(index==null){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("No selected item");
            errorAlert.setContentText("Please select a program from the list of programs!");
            errorAlert.showAndWait();
            return;
        }

        setUpControllerProgramState(getSelectedProgram(index),index);
        textArea.setText("");
        //delete selected item
        index=null;
    }

    private void populate(){
        ObservableList<String> lst = FXCollections.observableArrayList();
        lst.addAll(options.stream().map(Object::toString).collect(Collectors.toList()));
        programList.setItems(lst);
    }

    public void setStartWindow(Stage startPage){
        this.startWindow=startPage;
    }

    private StatementInterface getSelectedProgram(int index){
        return this.options.get(index);
    }

    public void setUpControllerProgramState(StatementInterface selectedProgram,Integer index) throws IOException {
        try {
            selectedProgram.typeCheck(new MyDictionary<>());
            ProgramState programState = new ProgramState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),selectedProgram);
            RepositoryInterface repo = new Repository(programState, "log" + index + ".txt");
            Controller contr = new Controller(repo);
            //SETUP NEW PAGE
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("mainWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1150, 800);
            InterpreterController interpreterController = fxmlLoader.getController();
            interpreterController.setProgramController(contr);
            Stage stage = new Stage();
            scene.getStylesheets().add(getClass().getResource("mainWindow.css").toExternalForm());
            stage.setTitle("Interpreter");
            stage.setScene(scene);
            stage.initOwner(startWindow);
            stage.initModality(Modality.WINDOW_MODAL);
            interpreterController.setPage(stage);
            stage.show();
        }
        catch (MyException exc){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("TypeCheck Error");
            error.setContentText(exc.getMessage());
            error.showAndWait();

        }

    }

    private String niceShow(String txt){
        return txt.replace(";",";\n");
    }


}