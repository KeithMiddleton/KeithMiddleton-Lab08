import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import javafx.stage.FileChooser;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;


public class lab08 extends Application {

    @Override

    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Lab 08 Solution");

        GridPane myGrid = new GridPane();
        TableView tableView = new TableView();

    	TableColumn<StudentRecord, String> column1 = new TableColumn<>("SID");
    	column1.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<StudentRecord, String> column2 = new TableColumn<>("Assignments");
    	column2.setCellValueFactory(new PropertyValueFactory<>("assignments"));

    	TableColumn<StudentRecord, String> column3 = new TableColumn<>("Midterm");
    	column3.setCellValueFactory(new PropertyValueFactory<>("midterm"));

    	TableColumn<StudentRecord, String> column4 = new TableColumn<>("Final Exam");
    	column4.setCellValueFactory(new PropertyValueFactory<>("finalExam"));

    	TableColumn<StudentRecord, String> column5 = new TableColumn<>("Final Mark");
    	column5.setCellValueFactory(new PropertyValueFactory<>("finalMark"));

    	TableColumn<StudentRecord, String> column6 = new TableColumn<>("Letter Grade");
    	column6.setCellValueFactory(new PropertyValueFactory<>("letterGrade"));
        
        tableView.getColumns().add(column1);
		tableView.getColumns().add(column2);
		tableView.getColumns().add(column3);
		tableView.getColumns().add(column4);
		tableView.getColumns().add(column5);
		tableView.getColumns().add(column6);



		//Menu
		Menu fileMenu = new Menu("File");

		//Names
		MenuItem menuNew = new MenuItem("New");
		MenuItem menuOpen = new MenuItem("Open");
		MenuItem menuSave = new MenuItem("Save");
		MenuItem menuSaveAs = new MenuItem("Save As");
		MenuItem menuExit = new MenuItem("Exit");

		//Adding
		fileMenu.getItems().add(menuNew);
		fileMenu.getItems().add(menuOpen);
		fileMenu.getItems().add(menuSave);
		fileMenu.getItems().add(menuSaveAs);
		fileMenu.getItems().add(menuExit);
		
		//Event adding
		menuNew.setOnAction(e -> {
    		currentFilename = "";
    		tableView.getItems().clear();
		});

		menuOpen.setOnAction(e -> {
    		FileChooser fileChooser = new FileChooser();
    		File selectedFile = fileChooser.showOpenDialog(primaryStage);
    		currentFilename = selectedFile.getName();
    		ObservableList<StudentRecord> temp = load(selectedFile);
    		tableView.getItems().clear();
    		for (StudentRecord s : temp){
			tableView.getItems().add(s);
		}
		});

		menuSave.setOnAction(e -> {
    		save(tableView.getItems());
		});

		menuSaveAs.setOnAction(e -> {
    		FileChooser fileChooser = new FileChooser();
    		File selectedFile = fileChooser.showOpenDialog(primaryStage);
    		currentFilename = selectedFile.getName();
    		save(tableView.getItems());
		});

		menuExit.setOnAction(e -> {
    		primaryStage.close();
		});

		MenuBar menuBar = new MenuBar();

		menuBar.getMenus().add(fileMenu);

        VBox vbox = new VBox(0);
        vbox.getChildren().add(menuBar);
        vbox.getChildren().add(tableView);
		Scene scene = new Scene(vbox);
		primaryStage.setScene(scene);
		primaryStage.show();
    }

    String currentFilename = "";

    public void save(ObservableList<StudentRecord> temp){
    	try{
    		if(currentFilename == ""){
    			currentFilename = "NewFile.txt";
    		}
    		File file = new File(currentFilename);
    		FileWriter writer = new FileWriter(currentFilename);
    		writer.write("SID,Assignments,Midterm,Exam\n");

    		for (StudentRecord s : temp){
				String text = s.getId() + "," + s.getAssignments() + "," + s.getMidterm() + "," + s.getFinalExam() + "\n";
				writer.write(text);
			}

    		writer.close();
    		}
    		catch (IOException r){
    			System.out.println("error");
    		}
    }

    public ObservableList<StudentRecord> load(File file){
        ObservableList<StudentRecord> marks = FXCollections.observableArrayList();
        Scanner sc = null;
        try
        {
            sc = new Scanner(file);
            sc.nextLine(); //skip title line
        }
        catch (Exception e)
        {
            System.out.println("The file does not exist!");
        }
        while (sc.hasNextLine())
        {
            List<String> data = new ArrayList<String>();
            try (Scanner rowScanner = new Scanner(sc.nextLine()))
            {
                rowScanner.useDelimiter(",");
                while (rowScanner.hasNext())
                {
                    data.add(rowScanner.next());
                }
            }
            marks.add(new StudentRecord(data.get(0), Float.parseFloat(data.get(1)), Float.parseFloat(data.get(2)), Float.parseFloat(data.get(3))));
        }
        return marks;
    }
    

    public static void main(String[] args) {
        launch(args);
    }

    public class StudentRecord{
	String id;
	float midterm;
	float assignments;
	float finalExam;
	float finalMark;
	String letterGrade;

	public StudentRecord(String sid, float assignment, float mid, float exam){
		this.id = sid;
		this.midterm = mid;
		this.assignments = assignment;
		this.finalExam = exam;

		this.finalMark = (assignments * 0.2f) + (mid * 0.3f) + (finalExam * 0.5f);

		if (this.finalMark <= 100 && this.finalMark >= 80){
			this.letterGrade = "A";
		}
		else if (this.finalMark < 80 && this.finalMark >= 70){
			this.letterGrade = "B";
		}
		else if (this.finalMark < 70 && this.finalMark >= 60){
			this.letterGrade = "C";
		}
		else if (this.finalMark < 60 && this.finalMark >= 50){
			this.letterGrade = "D";
		}
		else{
			this.letterGrade = "F";
		}
	}

	public String getId() {
		return id;
	}
		
	public float getAssignments() {
		return assignments;
	}
		
	public float getMidterm() {
		return midterm;
	}
		
	public float getFinalExam() {
		return finalExam;
	}
		
	public float getFinalMark() {
		return finalMark;
	}
		
	public String getLetterGrade() {
		return letterGrade;
	}
}

public class DataSource {

	public ObservableList<StudentRecord> getAllMarks(){

		ObservableList<StudentRecord> marks = FXCollections.observableArrayList();

		// Student ID, Assignments, Midterm, Final exam
		marks.add(new StudentRecord("100100100", 75.0f, 68.0f,54.25f));  
		marks.add(new StudentRecord("100100101",70.0f,69.25f, 51.5f));  
		marks.add(new StudentRecord("100100102",100.0f, 97.0f, 92.5f));  
		marks.add(new StudentRecord("100100103", 90.0f, 88.5f, 68.75f));
		marks.add(new StudentRecord("100100104", 72.25f, 74.75f,58.25f));
		marks.add(new StudentRecord("100100105", 85.0f, 56.0f,62.5f));
		marks.add(new StudentRecord("100100106",70.0f,66.5f, 61.75f));  
		marks.add(new StudentRecord("100100107",55.0f, 47.0f, 50.5f));  
		marks.add(new StudentRecord("100100108", 40.0f, 32.5f, 27.75f));
		marks.add(new StudentRecord("100100109", 82.5f, 77.0f, 74.25f));

		return marks;
	}
}


}

