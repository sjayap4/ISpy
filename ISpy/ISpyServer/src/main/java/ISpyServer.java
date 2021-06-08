import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.HashMap;

// ISpyServer: The GUI for the server window

public class ISpyServer extends Application {
    //first page
    BorderPane first;
    Label gameName;
    Label portName;
    TextField portNum;

    //center stuff
    GridPane grid,centerPort;
    Button connectServerBtn, disconnectServerBtn;

    //second page(info page)
    BorderPane mainPage;
    Label serverConnected;
    ListView<String>chatLog;

    //server info
    GameServer serverConnection;

    //to change Scene
    HashMap<String, Scene> sceneMap;
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }

    //feel free to remove the starter code from this method
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Define the scene hashmap and place the first and second pages in it
        sceneMap = new HashMap<>();
        sceneMap.put("first",firstPage(CenterGrid()));
        sceneMap.put("main",secondPage());

        // Set title of the window
        primaryStage.setTitle("(Server) ISpy Game!!!");

        // Set the first screen
        primaryStage.setScene(sceneMap.get("first"));

        // Show the primary stage
        primaryStage.show();

        // Connect button event handler
        connectServerBtn.setOnAction(e->{
            // If the correct port number was entered
            if(portNum.getText().equals("5555")){
                // Take it to the next page and update the title
                primaryStage.setScene(sceneMap.get("main"));
                primaryStage.setTitle("Server Active");
                primaryStage.show();

                // Create a new GameServer object
                serverConnection = new GameServer(data -> {
                    Platform.runLater(()->{
                        updateGUI((Image)data);
                    });
                });
            }

        });

        // Exit button event handler
        disconnectServerBtn.setOnAction(e-> Platform.exit());
    }

    //GridPane for BorderPane Center
    public GridPane CenterGrid(){
        // Create a new grid pane
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(18);

        // Hbox for the two buttongs
        HBox hbButtons = new HBox();
        hbButtons.setAlignment(Pos.CENTER);
        hbButtons.setSpacing(10.0);

        // Define a submit button
        connectServerBtn = new Button("SUBMIT");
        connectServerBtn.setFont(Font.font("Helvetica",FontWeight.SEMI_BOLD,20));

        // Define an exit button
        disconnectServerBtn = new Button("EXIT");
        disconnectServerBtn.setFont(Font.font("Helvetica",FontWeight.SEMI_BOLD,20));

        // Set sizes of the button
        connectServerBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        disconnectServerBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // Create labels to prompt user for a port number
        portName = new Label("Port Number:");
        portName.setFont(Font.font("Helvetica",FontWeight.SEMI_BOLD,25));
        portName.setTextFill(Color.WHITE);

        // Create a text field for user to enter in a port number
        portNum = new TextField();
        portNum.setPromptText("Enter port number here");

        // Put the buttons in the hbox
        hbButtons.getChildren().addAll(connectServerBtn,disconnectServerBtn);
        grid.add(portName, 0, 0);
        grid.add(portNum, 1, 0);
        grid.add(hbButtons, 0, 3, 2, 1);

        // Return grid
        return grid;
    }


    //first Page
    public Scene firstPage(GridPane centerG){
        // Declare a new borderpane and set the background picture
        first = new BorderPane();
        first.setStyle("-fx-background-image:url(serverPic.gif) ; -fx-background-size: 100% 100% ");

        // Create a prompt with the title name and place it
        gameName = new Label("Welcome to iSpy");
        gameName.setFont(Font.font("Helvetica", FontWeight.BOLD,45));
        gameName.setTextFill(Color.WHITE);
        first.setTop(gameName);
        BorderPane.setAlignment(gameName, Pos.TOP_CENTER);

        // Get the gridpane
        centerPort= centerG;
        first.setCenter(centerPort);
        return new Scene(first,700, 750);
    }

    //second page
    public Scene secondPage(){
        // Declare a new borderpane and set the background picture
        mainPage = new BorderPane();
        mainPage.setStyle("-fx-background-image:url(mainPagebg.gif) ; -fx-background-size: 100% 100% ");

        // Create a prompt with the title name and place it
        serverConnected = new Label("SERVER LOG");
        serverConnected.setFont(Font.font("Helvetica", FontWeight.BOLD,45));
        serverConnected.setTextFill(Color.WHITE);
        mainPage.setTop(serverConnected);
        BorderPane.setAlignment(serverConnected, Pos.TOP_CENTER);

        // Create a chatLog for the server log and place it
        chatLog = new ListView<>();
        chatLog.setMaxSize(400,400);
        mainPage.setCenter(chatLog);
        return new Scene(mainPage,700,750);
    }

    // updates the GUI when a new message comes in
    public void updateGUI(Image info){
        // Put a new message in the server log
        chatLog.getItems().add(info.getServerMessage());
    }
}
