package application;
	
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;




public class Main extends Application {
	//@Override
	
	private Scene mainScene; //used so I can redirect back to "main page"
	
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			
			primaryStage.setTitle("Heart Health Imaging and Recording System Implementation");
			
			//Main Page
            Label title = new Label("Welcome to Heart Health Imaging and Recording System");
            BorderPane.setAlignment(title, Pos.CENTER); // align title in the center
            root.setTop(title); // set title in the top section of the BorderPane
			Button PatientIntake = new Button("Patient Intake");
			Button CTscan = new Button("CT Scan Tech View");
			Button PatientView = new Button ("Patient View");
			GridPane.setHalignment(title, javafx.geometry.HPos.CENTER); 
			
			//The actions for each button
            PatientIntake.setOnAction(e -> { //redirects to patient info page
                primaryStage.setScene(createPatientIntakeScene(primaryStage));
            });
            CTscan.setOnAction(e -> { //redirects to Tech page
                primaryStage.setScene(TechScene(primaryStage));
            });
            PatientView.setOnAction(e -> { //redirects to patient overview page
                primaryStage.setScene(PatientScene(primaryStage));
            });
			

            //create a VBox to hold the buttons
            VBox vbox = new VBox(10); // spacing between buttons
            vbox.setAlignment(Pos.CENTER);
            vbox.getChildren().addAll(PatientIntake, CTscan, PatientView);
            // set the VBox containing buttons in the center of the BorderPane
            root.setCenter(vbox);
            
            //used so that the new page can redirect back to this scene
            mainScene = scene;
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
    private Scene createPatientIntakeScene(Stage primaryStage) { //patient info function
    	//creates new scene 
        BorderPane root = new BorderPane();
        Scene scene2 = new Scene(root, 400, 400);
        
        //create label
        Label label = new Label("Patient Intake Form");
        BorderPane.setAlignment(label, Pos.CENTER); //align title in the center
        root.setTop(label);

        // gridPane to hold labels and text fields and center them
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        //labels
        String[] labelTexts = {"First Name:", "Last Name:", "Email:", "Phone Number:", "Health History:", "Insurance ID:"};
        TextField[] textFields = new TextField[labelTexts.length];
        for (int i = 0; i < labelTexts.length; i++) { //iterates through length of labelTexts
            Label nameLabel = new Label(labelTexts[i]);
            gridPane.add(nameLabel, 0, i); 	

            //text fields
            TextField textField = new TextField();
            textFields[i] = textField;
            gridPane.add(textField, 1, i); //add text field to the second column, i-th row
        }
        root.setCenter(gridPane);
        
        // Create a button to go back to the main page
        Button backButton = new Button("Save");
        backButton.setOnAction(e -> {        	
        	savePatientInformation(primaryStage, textFields); //will save info through a txt
            primaryStage.setScene(mainScene); // Switch back to the main scene
        });
        BorderPane.setAlignment(backButton, Pos.BOTTOM_RIGHT);
        root.setBottom(backButton);

        return scene2;
    }
    
    private void savePatientInformation(Stage primaryStage, TextField[] textFields) {
    	//creates the patient ID
    	Random random = new Random();
        int patientID = random.nextInt(90000) + 10000;
        //creates the file
        String directoryPath = "C:/txtfiles"; // specific directory path
        File directory = new File(directoryPath);
        if (!directory.exists()) { //checking if it doesn't exists
            directory.mkdirs(); //creates the directory if it doesn't exist
        }

        String fileName = directoryPath + "/" + patientID +"_PatientInfo.txt"; //applying the generated ID and patientinfo title
        File file = new File(fileName);

        try (FileWriter writer = new FileWriter(file)) { //creates the file
            for (TextField textField : textFields) {    //looking through entire array of listed items from the patient info function
                String label = textField.getPromptText();
                String value = textField.getText();
                writer.write(label + ": " + value + "\n");
            }
            writer.flush(); //clears
        } catch (IOException e) {  //creates error handling
            e.printStackTrace();
        }
    }
    
    private Scene TechScene(Stage primaryStage) {
        //creates new scene
        BorderPane root = new BorderPane();
        Scene scene2 = new Scene(root, 400, 400);

        //create label
        Label cactitle = new Label("Vessel level Agaston CAC score:");
        Label label = new Label("Technician");
        BorderPane.setAlignment(label, Pos.CENTER); 
        root.setTop(label);
        
        // gridpane to hold labels and text fields
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        //labels
        String[] labelTexts = {"Patient ID:", "The total Agatston CAC score ", "LM:", "LAD:", "LCX:", "RCA:", "PDA:"};
        TextField[] textFields = new TextField[labelTexts.length];
        for (int i = 0; i < labelTexts.length; i++) {
            Label nameLabel = new Label(labelTexts[i]);
            TextField textField = new TextField();
            textFields[i] = textField;
            
            //add the first two labels and text fields
            if (i < 2) {
                gridPane.add(nameLabel, 0, i); //add label to the first column, i-th row
                gridPane.add(textField, 1, i); //add text field to the second column, i-th row
            }
            else if (i == 2)  //created so it has that dividing title
            {
                gridPane.addRow(2, cactitle);
                gridPane.add(nameLabel, 0, i+1); //used to fix the missing i == 2 value which is replaced by "cactitle"
                gridPane.add(textField, 1, i+1);
            }
            else {
                gridPane.add(nameLabel, 0, i + 1); //add label to the first column, i+1-th row
                gridPane.add(textField, 1, i + 1); //add text field to the second column, i+1-th row
            }
        }   

        root.setCenter(gridPane); //centers everything

        //create a VBox to hold the "Save" button and the error label
        VBox bottomContainer = new VBox(10); //spacing between nodes
        bottomContainer.setAlignment(Pos.CENTER_RIGHT);
        
        //create a button to go back to the main page
        Button backButton = new Button("Save");
        
        //prevents the error to display multiple times
        AtomicBoolean errorDisplayed = new AtomicBoolean(false);
        
        backButton.setOnAction(e -> {
            if (checkFieldsNotEmpty(textFields)) { //checkFieldsNotEmpty function called to check if text fields are empty
                primaryStage.setScene(mainScene); //switch back to the main scene
                saveCT(primaryStage, textFields);  //saves to a txt file
            } else {
                if (!errorDisplayed.get()) {
                    Label errorlabel = new Label("Error: Please fill in all fields.");
                    BorderPane.setAlignment(errorlabel, Pos.CENTER); 
                    bottomContainer.getChildren().add(errorlabel); //add error label to the bottom container
                    errorDisplayed.set(true); //set errorDisplayed flag to true
                }
            }
        });
        
        BorderPane.setAlignment(backButton, Pos.BOTTOM_RIGHT); //put the "back button" or save button to the bottom right to save
        bottomContainer.getChildren().add(backButton); //add "Save" button to the bottom container
        
        root.setBottom(bottomContainer);

        return scene2;
    }
    //function to check if if the text field is empty or not
    private boolean checkFieldsNotEmpty(TextField[] textFields) {
        for (TextField textField : textFields) {
            if (textField.getText().isEmpty()) {
                return false; //return false if any text field is empty
            }
        }
        return true; //return true if all text fields are filled
    }
    private void saveCT(Stage primaryStage, TextField[] textFields) {

    	//creates the patient ID
        String patientID = textFields[0].getText(); //grabbing the patient ID
        //creates the file
        String directoryPath = "C:/txtfiles"; //specific directory path
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs(); //creates the directory if it doesn't exist
        }

        String fileName = directoryPath + "/" + patientID +"CTResults.txt"; //saving file
        File file = new File(fileName);

        try (FileWriter writer = new FileWriter(file)) {
            for (TextField textField : textFields) {
                String label = textField.getPromptText();
                String value = textField.getText();
                writer.write(label + ": " + value + "\n");
            }
            writer.flush(); //clears any characters that have been saved 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //looks through the directory
    private String findPatientInfoFile(String directoryPath, String searchText) {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            return null; //directory doesn't exist or is not a directory
        }

        File[] files = directory.listFiles((dir, name) -> name.contains(searchText));
        if (files != null && files.length > 0) {
            //return the first matching file found
            return files[0].getPath();
        }
        return null; // No matching file found
    }
    //display patient info
    private Scene PatientScene(Stage primaryStage) {
        //creates the new scene
        BorderPane root = new BorderPane();
        Scene scene2 = new Scene(root, 400, 400);

        //read patient's name from PatientInfo.txt
        String patientName = "";
        String patientInfoFilePath = findPatientInfoFile("C:/txtfiles", "PatientInfo"); //gets through txt
        if (patientInfoFilePath != null) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(patientInfoFilePath));
                patientName = reader.readLine(); // Assuming the first line contains the patient's name
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //create label with patient's name
        Label label = new Label("Hello " + patientName);
        BorderPane.setAlignment(label, Pos.CENTER);
        root.setTop(label);

        //gridPane to hold labels and text fields
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        //labels
        String[] labelTexts = {"The total Agatston CAC score ", "LM:", "LAD:", "LCX:", "RCA:", "PDA:"};
        TextField[] textFields = new TextField[labelTexts.length];

        //read data from CTResults.txt and enter text fields
        String ctResultsFilePath = findPatientInfoFile("C:/txtfiles", "CTResults");
        if (ctResultsFilePath != null) {  //checking if the file path doesn't exist or if it does
            try {
                BufferedReader reader = new BufferedReader(new FileReader(ctResultsFilePath)); //reads the file
                String line;
                int i = 0;
                while ((line = reader.readLine()) != null && i < labelTexts.length) {
                    //split each line into label and value
                    String[] parts = line.split(": "); //gets rid of the ": " to check through an array
                    if (parts.length == 2) { 
                        labelTexts[i] += parts[0]; //update label text
                        TextField textField = new TextField(parts[1]); //create text field with stored data
                        textField.setEditable(false); //make text field non-editable
                        textFields[i] = textField;
                        //add label and text field to the grid pane
                        gridPane.add(new Label(labelTexts[i]), 0, i); //add label to the first column, i-th row
                        gridPane.add(textField, 1, i); //add text field to the second column, i-th row
                        i++;
                    }
                }
                reader.close();
            } catch (IOException e) { //error handling 
                e.printStackTrace();
            }
        }

        root.setCenter(gridPane);

        //create a button to go back to the main page
        Button backButton = new Button("Back to Main Page");
        backButton.setOnAction(e -> {
            primaryStage.setScene(mainScene); // Switch back to the main scene
        });

        root.setBottom(backButton);

        return scene2;
    }

	
	public static void main(String[] args) {
		launch(args);
		
	}
}
