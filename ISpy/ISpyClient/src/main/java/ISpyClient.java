import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.bind.SchemaOutputResolver;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;


// ISpyClient: the client GUI class
public class ISpyClient extends Application{
    // Connect networking stuff
    Socket socketClient;
    ObjectOutputStream out;
    ObjectInputStream in;

    //client networking
    Client network;
    Image info;
    String itemName;

    //first page
    Pane neighborhood, zoo, park;
    Label findNeighborhood, findZoo,findPark;

    Button parkBtn, neighborhoodBtn, zooBtn;
    Button neighborhoodBack, parkBack, zooBack;

    HashMap<String, Scene> sceneMap;
    String holdLogInStmt, holdCreateAccStmt;

    //Everything for the client starting page
    BorderPane first;
    Label gameName;
    //center stuff for firstPage
    GridPane gridFirst,centerFirst;
    Button start,exit;

    //Everything for the login page
    BorderPane second;
    Label loginLabel,nameLabel,passwordLabel;
    Text logInErrorStmt;
    // center stuff for secondPage(login)
    GridPane gridSecond,centerSecond;
    Button login,createAccountBtn,exitLoginBtn;
    TextField username;
    PasswordField password;

    //Everything for the addGridPane  for createAccountPage
    BorderPane createaccPage;
    GridPane accountDetails, grid;
    Label gettingStarted,user_ID,user_Password,user_Email;
    TextField userinfo_ID,userinfo_Email;
    PasswordField userinfo_Password;
    Button submitinfo_Btn,backinfo_Btn,exitinfo_Btn;

    //Everything for the third page
    BorderPane third,thirdTop;
    Label modeLabel;
    MenuItem exitThird,logoutThird,instructionThird;
    MenuButton Menu;
    HBox thirdOptions;
    Text pointsClient;
    //center stuff for third page
    GridPane gridThird,centerThird;
    Label choicesLabel;

    //Everything for submit page
    Pane submit;
    Label hintLabel,fileChoosen;
    Text hintTxt;
    VBox submitVBox;
    HBox submitHBox;
    Button submitBtn, uploadBtn, backItemsPage, chooseBtn;


    // Stuff for item buttons
    HashMap<String, String> itemsMap;
    List<String> nHoodPassButtonList;
    List<String> parkPassButtonList;
    List<String>  zooPassButtonList;
    ArrayList<String> nHoodPassLocalList;
    ArrayList<String> parkPassLocalList;
    ArrayList<String>  zooPassLocalList;

    //Everything for Completed Game Page
    BorderPane gameOver;
    Label gamePOver;
    Button backDone;
    String lvlName;

    VBox parkItems, zooItems, neighborhoodItems;

    // Keep track of level
    int levelPark, levelNeighborhood, levelZoo;
    Label parkLvlTxt, nHLvlTxt, zooLvlTxt;
    String lvlParkStr, lvlNHStr, lvlZooStr;
    VBox levelBox, nhBox, pBox, zBox;

    String curLocation;

    Text createAccErrorStmt;

    String userInput, passInput;

    MyConnection connection = new MyConnection();

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        //image to be sent
        info = new Image();

        // make vboxes that will hold the items that the user must search for
        parkItems = new VBox();
        neighborhoodItems = new VBox();
        zooItems = new VBox();
        nhBox = new VBox();
        pBox = new VBox();
        zBox = new VBox();

        // create HashMap that will store the database results
        itemsMap = new HashMap<>();

        // create list to hold item buttons being passed in from csv
        nHoodPassButtonList = new ArrayList<>();
        parkPassButtonList = new ArrayList<>();
        zooPassButtonList = new ArrayList<>();
        nHoodPassLocalList = new ArrayList<>();
        parkPassLocalList = new ArrayList<>();
        zooPassLocalList = new ArrayList<>();

        // create labels
        parkLvlTxt = new Label(lvlParkStr);
        zooLvlTxt = new Label(lvlZooStr);
        nHLvlTxt = new Label(lvlNHStr);
        modeLabel = new Label("DIFFERENT MODES");

        //create buttons to change scene
        start = new Button("Start");
        exit = new Button("Exit");
        createAccountBtn = new Button("Create Account");
        login = new Button ("Login");

        //create buttons for different locations and change scene depending on which button selected
        parkBtn = new Button("Park");
        neighborhoodBtn = new Button("Neighborhood");
        zooBtn = new Button ("Zoo");

        // create buttons that will bring you back to the third page
        neighborhoodBack = new Button("Back");
        parkBack = new Button("Back");
        zooBack = new Button("Back");


        //create choose file button
        chooseBtn = new Button ("Choose File");

        //create hashmap to store scenes
        sceneMap = new HashMap<>();

        curLocation = "";

        //scenes returned from methods; put in hashmap
        sceneMap.put("first", firstPage(centerGridFirst()));
        sceneMap.put("second", secondPage(centerGridSecond()));
        sceneMap.put("account", createAccount(CenterGrid()));
        sceneMap.put("third", thirdPage(centerGridThird()));
        sceneMap.put("neighborhood", neighborhoodPage());
        sceneMap.put("zoo", zooPage());
        sceneMap.put("park",parkPage());
        sceneMap.put("submit", submitPage());

        //start button event handler
        start.setOnAction(e -> {
            // move to the second page
            primaryStage.setScene(sceneMap.get("second"));

            // Try to connect to SQL
            try {
                connection.connect();
            }catch(FileNotFoundException fileNotFoundException){
                fileNotFoundException.printStackTrace();
            }
        });

        //exit button exits from the game
        exit.setOnAction(e -> Platform.exit());

        //create button to change from second scene to third scene
        login.setOnAction(e -> {

            // Get the username and password input from the user
            userInput = username.getText();
            passInput = password.getText();

            // Create booleans to see if the username and password are in the database
            Boolean foundUser = connection.lookForUser(userInput);
            Boolean correctPass = connection.checkPass(userInput, passInput);

            // If one of the inputs are empty then print a empty statement
            if(passInput.equals("") || userInput.equals("")){
                holdLogInStmt = "missing an input";
                logInErrorStmt.setText(holdLogInStmt);
            }

            // If there is none found then add
            else if(!foundUser){
                holdLogInStmt = "Click on create account";
                logInErrorStmt.setText("Click on Create Account");
            }

            // Else if password doesn't, match print error message
            else if(foundUser && !correctPass){
                holdLogInStmt = "password is wrong";
                logInErrorStmt.setText(holdLogInStmt);
            }

            // Else if user and pass is same go to next scene
            else if(foundUser && correctPass){
                // If the level for park is not 0, get the saved level
                if (connection.getParkLvl() != 0) {
                    levelPark = connection.getParkLvl();
                }
                // Otherwise, set the level for park to 1
                else {
                    levelPark = 1;
                }

                // If the level for zoo is not 0, get the saved level
                if (connection.getZooLvl() != 0) {
                    levelZoo = connection.getZooLvl();
                }
                // Otherwise, set the level for zoo to 1
                else {
                    levelZoo = 1;
                }

                // If the level for neighborhood is not 0, get the saved level
                if (connection.getNhLvl() != 0) {
                    levelNeighborhood = connection.getNhLvl();
                }
                // Otherwise, set the level for neighborhood to 1
                else {
                    levelNeighborhood = 1;
                }

                // Update the third scene and get it
                updateThird(primaryStage);
                primaryStage.setScene(sceneMap.get("third"));

                // Listener for anything sent from the server
                network = new Client(data-> {Platform.runLater(() -> {
                    updateButtons((Image) data);
                    });
                });
                network.start();
            }
        });

        //button to change to create account scene
        createAccountBtn.setOnAction(e -> {
            primaryStage.setScene(sceneMap.get("account"));
        });

        //exit from login page
        exitLoginBtn.setOnAction(e -> Platform.exit());

        //button to change to neighborhood scene
        neighborhoodBtn.setOnAction(e -> {
            // If the neighborhood level is or greater than 6
            if(connection.getNhLvl()>=6){
                // Grab the game done screen
                lvlName = "Neighborhood";
                primaryStage.setScene(GameDone(lvlName, primaryStage));
                primaryStage.show();
            }
            // The neighborhood level is less than 6
            else {
                // Get the saved neighborhood completed items
                String savedNHPass = connection.getSavedNeighborhood();

                // If there is something returned from that function
                if (savedNHPass != null) {
                    // Split the string and save as an arraylist
                    String savedNHPassArr[] = savedNHPass.split("\n");
                    nHoodPassButtonList = Arrays.asList(savedNHPassArr);

                    // If the local list in this class is not the same size as the list in the database, add it
                    if (nHoodPassButtonList.size() != nHoodPassLocalList.size()) {
                        nHoodPassLocalList.addAll(nHoodPassButtonList);
                    }
                }

                // Call the clear function and get the area and items
                connection.clear();
                connection.getAreaAndLevel(levelNeighborhood, "Neighborhood");
                connection.getItems();
                itemsMap = connection.getResults();

                // Update the database
                connection.updateAccountLevel("Neighborhood", levelNeighborhood);

                // Update the neighborhood scene
                updateNeighborhood(itemsMap, primaryStage);

                // Set the scene to neighborhood
                primaryStage.setScene(sceneMap.get("neighborhood"));
            }
        });

        //button to change to zoo scene
        zooBtn.setOnAction(e -> {
            // If the zoo level is or greater than 6
            if(connection.getZooLvl()>=6){
                // Grab the game done screen
                lvlName = "Zoo";
                primaryStage.setScene(GameDone(lvlName, primaryStage));
                primaryStage.show();
            }
            // The zoo level is less than 6
            else {
                // Get the saved zoo completed items
                String savedZooPass = connection.getSavedZoo();

                // If there is something returned from that function
                if (savedZooPass != null) {
                    // Split the string and save as an arraylist
                    String savedZooPassArr[] = savedZooPass.split("\n");
                    zooPassButtonList = Arrays.asList(savedZooPassArr);

                    // If the local list in this class is not the same size as the list in the database, add it
                    if (zooPassButtonList.size() != zooPassLocalList.size()) {
                        zooPassLocalList.addAll(zooPassButtonList);
                    }
                }

                // Call the clear function and get the area and items
                connection.clear();
                connection.getAreaAndLevel(levelZoo, "Zoo");
                connection.getItems();
                itemsMap = connection.getResults();

                // Update the database
                connection.updateAccountLevel("Zoo", levelZoo);

                // Update the zoo scene
                updateZoo(itemsMap, primaryStage);

                // Set the scene to zoo
                primaryStage.setScene(sceneMap.get("zoo"));
            }

        });

        //button to change to park scene
        parkBtn.setOnAction(e -> {
            // If the park level is or greater than 6
            if(connection.getParkLvl()>=6){
                // Grab the game done screen
                lvlName = "Park";
                primaryStage.setScene(GameDone(lvlName, primaryStage));
                primaryStage.show();
            }
            // The park level is less than 6
            else {
                // Get the saved park completed items
                String savedParkPass = connection.getSavedPark();

                // If there is something returned from that function
                if (savedParkPass != null) {
                    // Split the string and save as an arraylist
                    String savedParkPassArr[] = savedParkPass.split("\n");
                    parkPassButtonList = Arrays.asList(savedParkPassArr);

                    // If the local list in this class is not the same size as the list in the database, add it
                    if (parkPassButtonList.size() != parkPassLocalList.size()) {
                        parkPassLocalList.addAll(parkPassButtonList);
                    }
                }

                // Call the clear function and get the area and items
                connection.clear();
                connection.getAreaAndLevel(levelPark, "Park");
                connection.getItems();
                itemsMap = connection.getResults();

                // Update the database
                connection.updateAccountLevel("Park", levelPark);

                // Update the park scene
                updatePark(itemsMap, primaryStage);

                // Get the park page
                primaryStage.setScene(sceneMap.get("park"));
            }
        });

        // go back to the third page from neighborhood
        neighborhoodBack.setOnAction(e -> {
            // update the third page and get it
            updateThird(primaryStage);
            primaryStage.setScene(sceneMap.get("third"));
        });

        // go back to the third page from neighborhood
        parkBack.setOnAction(e -> {
            // update the third page and get it
            updateThird(primaryStage);
            primaryStage.setScene(sceneMap.get("third"));
        });

        // go back to the third page from zoo
        zooBack.setOnAction(e -> {
            // update the third page and get it
            updateThird(primaryStage);
            primaryStage.setScene(sceneMap.get("third"));
        });

        // button to go back to the items page
        backItemsPage.setOnAction(e-> {
            // If the location is neighborhood
            if (curLocation.equals("neighborhood")) {
                // if the size of the passed items in neighborhood matches the number of items in the map
                if (nHoodPassButtonList.size() == itemsMap.size()) {
                    // Clear the passed items for neighborhood
                    connection.clearPassed(curLocation);

                    // Clear the local list
                    nHoodPassLocalList.clear();

                    // Increment the level
                    levelNeighborhood++;

                    // Update the level in database
                    connection.updateAccountLevel("Neighborhood", levelNeighborhood);
                }
            }

            // If the location is park
            else if (curLocation.equals("park")) {
                // if the size of the passed items in park matches the number of items in the map
                if (parkPassButtonList.size() == itemsMap.size()) {
                    // Clear the passed items for park
                    connection.clearPassed(curLocation);

                    // Clear the local list
                    parkPassLocalList.clear();

                    // Increment the level
                    levelPark++;

                    // Update the level in database
                    connection.updateAccountLevel("Park", levelPark);
                }
            }

            // If the location is zoo
            else {
                // if the size of the passed items in zoo matches the number of items in the map
                if (zooPassButtonList.size() == itemsMap.size()) {
                    // Clear the passed items for zoo
                    connection.clearPassed(curLocation);

                    // Clear the local list
                    zooPassLocalList.clear();

                    // Increment the level
                    levelZoo++;

                    // Update the level in database
                    connection.updateAccountLevel("Zoo", levelZoo);
                }
            }

            // Update the third page and get it
            updateThird(primaryStage);
            primaryStage.setScene(sceneMap.get("third"));

            // Set the text that the file chose was wrong or the picture was not submitted for the next time
            fileChoosen.setText("No file has been submitted or\n"+"wrong image was submitted\n"+"Please submit the correct image");
        });

        //button to submit when creating new account
        submitinfo_Btn.setOnAction(e -> {
            //gets the text entered into text field
            String currUser = userinfo_ID.getText();
            String currPass = userinfo_Password.getText();
            String currEmail = userinfo_Email.getText();

            Boolean hasUser = connection.lookForUser(currUser);

            //checks whether or not there is a missing field not entered
            if(currUser.equals("") || currEmail.equals("") || currPass.equals("")){
                holdCreateAccStmt = "Missing input";
                createAccErrorStmt.setText(holdCreateAccStmt);
            }
            //if username already exists tell user to return to login page
            else if(hasUser){
                holdCreateAccStmt = "Please return to login page.\n User already exists.";
                createAccErrorStmt.setText(holdCreateAccStmt);
            }
            //else creates an account
            else {
                connection.addLogIn(currUser, currPass, currEmail);
                primaryStage.setScene(sceneMap.get("second"));
            }
        });

        //button to go back to login page
        backinfo_Btn.setOnAction(e -> primaryStage.setScene(sceneMap.get("second")));

        //exit button exits from the game
        exitinfo_Btn.setOnAction(e -> Platform.exit());

        //button to go back to login page from third page
        logoutThird.setOnAction(e -> primaryStage.setScene(sceneMap.get("second")));

        //button to exit game from modes
        exitThird.setOnAction(e -> {
            Platform.exit();
        });

        //chooseFile button
        FileChooser fileChooser = new FileChooser();
        chooseBtn.setOnAction(e -> {
            // Get the filechooser and allow user to select a file
            File selectedFile = fileChooser.showOpenDialog(primaryStage);

            // Update the text to notify what file the user chose
            fileChoosen.setText("File Choosen: "+selectedFile.getName());

            // Don't disable the submit button so the user can not click it
            submitBtn.setDisable(false);

            // Submit button event handler
            submitBtn.setOnAction(a->{
                // Disable the choose and submit buttons again
                chooseBtn.setDisable(true);
                submitBtn.setDisable(true);

                // Update Image object info
                info.setimageFile(selectedFile);
                info.setItemName(itemName);
                info.setUserName(userInput);

                // Send it to the server
                network.send(info);
                fileChoosen.setText("");
            });
        });

        //instruction separate window
        instructionThird.setOnAction(e->{
            // Create a new pop up window for the instructions
            TextArea textInstructions = new TextArea();
            textInstructions.setText("INSTRUCTIONS TO PLAY THE GAME\n"+"" +
                    "iSpy is an interactive game for kids to get outside and learn more about the world.\n" +
                    "The game provides 3 different options including a park, neighborhood, and zoo. \n" +
                    "Among those three locations,the game will task the children with finding different items per location. \n" +
                    "After finding the items,the kids must submit a photo in order to score points and move to different levels.");
            textInstructions.setFont(Font.font("Helvetica",FontWeight.SEMI_BOLD,25));
            textInstructions.setEditable(false);
            Scene instructionsScene = new Scene(textInstructions,800,500);
            Stage instructionsWindow = new Stage();
            instructionsWindow.setTitle("Instruction Page");
            instructionsWindow.setScene(instructionsScene);
            instructionsWindow.show();
        });

        // Set the title of the window
        primaryStage.setTitle("(Client) ISpy Game!!!");


        //method call to get the first scene from hashmap and set it
        primaryStage.setScene(sceneMap.get("first"));
        primaryStage.show();
    }

    //center for Welcome firstPage
    public GridPane centerGridFirst(){
        gridFirst = new GridPane();
        gridFirst.setAlignment(Pos.CENTER);
        gridFirst.setHgap(5);
        gridFirst.setVgap(18);

        HBox hbButtons = new HBox();
        hbButtons.setAlignment(Pos.CENTER);
        hbButtons.setSpacing(10.0);

        start = new Button("START");
        start.setFont(Font.font("Helvetica",FontWeight.SEMI_BOLD,30));

        exit= new Button("EXIT");
        exit.setFont(Font.font("Helvetica",FontWeight.SEMI_BOLD,30));

        start.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        exit.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        hbButtons.getChildren().addAll(start,exit);
        gridFirst.add(hbButtons, 0, 3, 2, 1);
        return gridFirst;
    }

    //First Page
    public Scene firstPage(GridPane center){
        first = new BorderPane();
        first.setStyle("-fx-background-image:url(background_start.gif) ; -fx-background-size: 100% 100% ");

        gameName = new Label("Welcome to iSpy");

        gameName.setFont(Font.font("Helvetica", FontWeight.BOLD,45));
        gameName.setTextFill(Color.ALICEBLUE);
        first.setTop(gameName);
        BorderPane.setAlignment(gameName, Pos.TOP_CENTER);
        centerFirst = center;
        first.setCenter(centerFirst);
        return new Scene(first,700, 750);
    }

    // Center gridpane for the second page
    public GridPane centerGridSecond(){
        gridSecond = new GridPane();
        gridSecond.setAlignment(Pos.CENTER);
        gridSecond.setHgap(5);
        gridSecond.setVgap(18);

        HBox hbButtons = new HBox();
        hbButtons.setAlignment(Pos.CENTER);
        hbButtons.setSpacing(10.0);

        login = new Button("LOGIN");
        login.setFont(Font.font("Helvetica",FontWeight.SEMI_BOLD,30));

        exitLoginBtn= new Button("EXIT");
        exitLoginBtn.setFont(Font.font("Helvetica",FontWeight.SEMI_BOLD,30));

        createAccountBtn = new Button("CREATE ACCOUNT");
        createAccountBtn.setFont(Font.font("Helvetica",FontWeight.SEMI_BOLD,30));

        nameLabel = new Label("USERNAME:");
        nameLabel.setFont(Font.font("Helvetica",FontWeight.SEMI_BOLD,25));
        nameLabel.setTextFill(Color.WHITE);
        username = new TextField();
        username.setPromptText("Enter username here");

        passwordLabel = new Label("PASSWORD:");
        passwordLabel.setFont(Font.font("Helvetica",FontWeight.SEMI_BOLD,25));
        passwordLabel.setTextFill(Color.WHITE);
        password = new PasswordField();
        password.setPromptText("Enter password here");

        login.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        exitLoginBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        createAccountBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        hbButtons.getChildren().addAll(login,exitLoginBtn,createAccountBtn);
        gridSecond.add(nameLabel, 0, 0);
        gridSecond.add(username, 1, 0);
        gridSecond.add(passwordLabel, 0, 1);
        gridSecond.add(password, 1, 1);
        gridSecond.add(hbButtons, 0, 3, 2, 1);
        return gridSecond;
    }

    //Second Page
    public Scene secondPage(GridPane centerG) {
        second = new BorderPane();
        second.setStyle("-fx-background-image:url(background_login.gif) ; -fx-background-size: 100% 100% ");
        loginLabel = new Label("LOGIN");
        loginLabel.setFont(Font.font("Helvetica", FontWeight.BOLD,50));
        loginLabel.setTextFill(Color.WHITE);
        second.setTop(loginLabel);
        BorderPane.setAlignment(loginLabel, Pos.TOP_CENTER);
        centerSecond= centerG;
        second.setCenter(centerSecond);
        logInErrorStmt = new Text();
        logInErrorStmt.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        logInErrorStmt.setFill(Color.WHITE);
        BorderPane.setAlignment(logInErrorStmt, Pos.BOTTOM_CENTER);
        second.setBottom(logInErrorStmt);
        return new Scene(second, 700, 750);

    }

    //GridPane for BorderPane Center
    public GridPane CenterGrid(){
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(18);

        HBox hbButtons = new HBox();
        hbButtons.setAlignment(Pos.CENTER);
        hbButtons.setSpacing(10.0);

        submitinfo_Btn = new Button("SUBMIT");
        submitinfo_Btn.setFont(Font.font("Helvetica",FontWeight.SEMI_BOLD,20));

        backinfo_Btn = new Button("BACK");
        backinfo_Btn.setFont(Font.font("Helvetica",FontWeight.SEMI_BOLD,20));

        exitinfo_Btn = new Button("EXIT");
        exitinfo_Btn.setFont(Font.font("Helvetica",FontWeight.SEMI_BOLD,20));

        submitinfo_Btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        backinfo_Btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        exitinfo_Btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        user_ID = new Label("USERNAME:");
        user_ID.setFont(Font.font("Helvetica",FontWeight.SEMI_BOLD,25));
        user_ID.setTextFill(Color.WHITE);
        userinfo_ID = new TextField();
        userinfo_ID.setPromptText("Enter username here");

        user_Password = new Label("PASSWORD:");
        user_Password.setFont(Font.font("Helvetica",FontWeight.SEMI_BOLD,25));
        user_Password.setTextFill(Color.WHITE);
        userinfo_Password = new PasswordField();
        userinfo_Password.setPromptText("Enter password here");

        user_Email = new Label ("EMAIL ADDRESS:");
        user_Email.setFont(Font.font("Helvetica",FontWeight.SEMI_BOLD,25));
        user_Email.setTextFill(Color.WHITE);
        userinfo_Email = new TextField();
        userinfo_Email.setPromptText("Enter email address here");

        hbButtons.getChildren().addAll(submitinfo_Btn,backinfo_Btn,exitinfo_Btn);
        grid.add(user_ID, 0, 0);
        grid.add(userinfo_ID, 1, 0);
        grid.add(user_Password, 0, 1);
        grid.add(userinfo_Password, 1, 1);
        grid.add(user_Email, 0, 2);
        grid.add(userinfo_Email, 1, 2);
        grid.add(hbButtons, 0, 3, 2, 1);

        return grid;
    }

    //createAccount Page
    public Scene createAccount(GridPane centerG){
        gettingStarted = new Label("GETTING STARTED");
        gettingStarted.setFont(Font.font("Helvetica", FontWeight.BOLD,50));
        gettingStarted.setTextFill(Color.WHITE);

        accountDetails= centerG;

        createaccPage = new BorderPane();
        createaccPage.setStyle("-fx-background-image:url(background_createAccount.gif) ; -fx-background-size: 100% 100% ");
        createaccPage.setTop(gettingStarted);
        createAccErrorStmt = new Text();
        createAccErrorStmt.setFont(Font.font("Helvetica", FontWeight.BOLD,20));
        createAccErrorStmt.setFill(Color.WHITE);
        createaccPage.setBottom(createAccErrorStmt);
        BorderPane.setAlignment(createAccErrorStmt,Pos.BOTTOM_CENTER);


        BorderPane.setAlignment(gettingStarted,Pos.TOP_CENTER);
        createaccPage.setCenter(accountDetails);

        return new Scene(createaccPage,700,750);
    }

    // gridpane for the third page
    public GridPane centerGridThird(){
        gridThird = new GridPane();
        gridThird.setAlignment(Pos.CENTER);
        gridThird.setHgap(5);
        gridThird.setVgap(18);

        HBox hbLabel = new HBox();
        hbLabel.setAlignment(Pos.CENTER);

        choicesLabel = new Label("SELECT A LOCATION");
        choicesLabel.setFont(Font.font("Helvetica", FontWeight.BOLD,45));
        choicesLabel.setTextFill(Color.WHITE);
        hbLabel.getChildren().addAll(choicesLabel);
        parkBtn= new Button("PARK");
        parkBtn.setFont(Font.font("Helvetica",FontWeight.SEMI_BOLD,20));

        neighborhoodBtn = new Button("NEIGHBORHOOD");
        neighborhoodBtn.setFont(Font.font("Helvetica",FontWeight.SEMI_BOLD,20));

        zooBtn = new Button("ZOO");
        zooBtn.setFont(Font.font("Helvetica",FontWeight.SEMI_BOLD,20));

        parkBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        neighborhoodBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        zooBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);


        gridThird.add(hbLabel, 0,0);
        gridThird.add(parkBtn,0,1);
        gridThird.add(neighborhoodBtn,0,2);
        gridThird.add(zooBtn,0,3);

        return gridThird;
    }

    // updateThird updates the third page
    public void updateThird(Stage primaryStage) {

        // If the level park is less than 6, print the level
        if (levelPark < 6) {
            lvlParkStr = "PARK LEVEL: " + levelPark;
        }
        // If the level park is greater than or equal to 6, print that it is complete
        else {
            lvlParkStr = "PARK LEVEL: COMPLETE";
        }

        // If the level neighborhood is less than 6, print the level
        if (levelNeighborhood < 6) {
            lvlNHStr = "NEIGHBORHOOD LEVEL: " + levelNeighborhood;
        }
        // If the level neighborhood is greater than or equal to 6, print that it is complete
        else {
            lvlNHStr = "NEIGHBORHOOD LEVEL: COMPLETE";
        }

        // If the level zoo is less than 6, print the level
        if (levelZoo < 6) {
            lvlZooStr = "ZOO LEVEL: " + levelZoo;
        }
        // If the level zoo is greater than or equal to 6, print that it is complete
        else {
            lvlZooStr = "ZOO LEVEL: COMPLETE";
        }

        // Set the text design and the text itself
        modeLabel.setFont(Font.font("Helvetica", FontWeight.BOLD,50));
        modeLabel.setTextFill(Color.WHITE);
        parkLvlTxt.setFont(Font.font("Helvetica", FontWeight.BOLD,25));
        parkLvlTxt.setTextFill(Color.WHITE);
        zooLvlTxt.setFont(Font.font("Helvetica", FontWeight.BOLD,25));
        zooLvlTxt.setTextFill(Color.WHITE);
        nHLvlTxt.setFont(Font.font("Helvetica", FontWeight.BOLD,25));
        nHLvlTxt.setTextFill(Color.WHITE);

        parkLvlTxt.setText(lvlParkStr);
        nHLvlTxt.setText(lvlNHStr);
        zooLvlTxt.setText(lvlZooStr);

        // Clear the box and add the texts
        levelBox.getChildren().clear();
        levelBox.getChildren().add(modeLabel);
        levelBox.getChildren().add(parkLvlTxt);
        levelBox.getChildren().add(nHLvlTxt);
        levelBox.getChildren().add(zooLvlTxt);
    }

    //Third Page
    public Scene thirdPage(GridPane centerG) {

        third = new BorderPane();
        third.setStyle("-fx-background-image:url(background_selection.gif) ; -fx-background-size: 100% 100% ");

        thirdTop = new BorderPane();

        levelBox = new VBox();
        thirdTop.setBottom(levelBox);
        levelBox.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(thirdTop, Pos.BOTTOM_CENTER);
        centerThird= centerG;
        third.setCenter(centerThird);

        exitThird = new MenuItem("EXIT");
        logoutThird = new MenuItem("LOGOUT");
        instructionThird = new MenuItem("INSTRUCTIONS");

        Menu = new MenuButton("MENU", null, logoutThird,exitThird,instructionThird);
        thirdOptions = new HBox(Menu);
        thirdTop.setLeft(thirdOptions);
        BorderPane.setAlignment(thirdOptions,Pos.CENTER_LEFT);

        thirdTop.setRight(pointsClient);
        BorderPane.setAlignment(thirdOptions,Pos.CENTER_RIGHT);

        third.setTop(thirdTop);


        return new Scene(third, 750, 750);
    }

    //neighborhood page
    public Scene neighborhoodPage() {
        neighborhood = new Pane();
        lvlNHStr = "NEIGHBORHOOD LEVEL: " + levelNeighborhood;
        neighborhood.setStyle("-fx-background-image:url(background_Neighborhood.jpg) ; -fx-background-size: 100% 100% ");
        nHLvlTxt = new Label(lvlNHStr);
        findNeighborhood = new Label("FIND THESE ITEMS IN THE \nNEIGHBORHOOD");
        findNeighborhood.setFont(Font.font("Helvetica", FontWeight.BOLD,45));
        findNeighborhood.setTextFill(Color.BLACK);
        findNeighborhood.relocate(60,25);
        findNeighborhood.setTextAlignment(TextAlignment.CENTER);
        neighborhood.getChildren().addAll(findNeighborhood, nhBox);
        return new Scene(neighborhood, 700, 750);
    }

    //zoo page
    public Scene zooPage() {
        zoo = new Pane();
        lvlZooStr = "ZOO LEVEL: " + levelZoo;
        zoo.setStyle("-fx-background-image:url(background_zoo.png) ; -fx-background-size: 100% 100% ");
        zooLvlTxt = new Label(lvlZooStr);
        findZoo = new Label("FIND THESE ITEMS IN THE \nZOO");
        findZoo.setFont(Font.font("Helvetica", FontWeight.BOLD,45));
        findZoo.setTextFill(Color.BLACK);
        findZoo.relocate(60,25);
        findZoo.setTextAlignment(TextAlignment.CENTER);
        zoo.getChildren().addAll(findZoo, zooLvlTxt, zBox);
        return new Scene(zoo, 700, 750);
    }

    //park Page
    public Scene parkPage() {
        park = new Pane();
        lvlParkStr = "PARK LEVEL: " + levelPark;
        park.setStyle("-fx-background-image:url(background_Park.jpg) ; -fx-background-size: 100% 100% ");
        parkLvlTxt = new Label(lvlParkStr);
        findPark = new Label("FIND THESE ITEMS IN THE \nPARK");
        findPark.setFont(Font.font("Helvetica", FontWeight.BOLD,45));
        findPark.setTextFill(Color.BLACK);
        findPark.setAlignment(Pos.CENTER);
        findPark.setTextAlignment(TextAlignment.CENTER);
        findPark.relocate(60,25);
        park.getChildren().addAll(findPark, parkLvlTxt, pBox);
        return new Scene(park, 700, 750);
    }


    //submit page
    public Scene submitPage(){
        submit = new Pane();
        submit.setStyle("-fx-background-image:url(background_Submit.gif) ; -fx-background-size: 100% 100% ");

        hintLabel = new Label();
        hintLabel.setFont(Font.font("Helvetica", FontWeight.BOLD,50));
        hintLabel.setTextFill(Color.WHITE);

        hintTxt = new Text();
        hintTxt.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
        hintTxt.setFill(Color.PALEGOLDENROD);

        fileChoosen = new Label();
        fileChoosen.setFont(Font.font("Helvetica", FontWeight.BOLD,20));
        fileChoosen.setTextFill(Color.rgb(224, 189, 72));
        fileChoosen.relocate(100,350);

        submitBtn = new Button("Submit");
        submitBtn.setDisable(true);

        uploadBtn = new Button("Upload");
        backItemsPage = new Button("Back");

        submitHBox = new HBox(10, submitBtn,chooseBtn, backItemsPage);

        submitVBox = new VBox(10, hintLabel, hintTxt, submitHBox);
        submitVBox.relocate(100, 200);


        submit.getChildren().addAll(submitVBox,fileChoosen);

        return new Scene(submit, 700, 750);
    }


    // updateNeighborhood : method that adds buttons to the neighborhood scene
    public void updateNeighborhood(HashMap<String,String> items, Stage primaryStage) {
        // Set style and sizes for back button and spacing for vbox
        neighborhoodBack.setStyle("-fx-background-color: SNOW;");
        neighborhoodBack.setMaxSize(125,125);

        nhBox.getChildren().clear();
        nhBox.setAlignment(Pos.CENTER);
        nhBox.relocate(190,140);

        neighborhoodItems.getChildren().clear();
        neighborhoodItems.setSpacing(15.0);
        neighborhoodItems.relocate(300,150);
        neighborhoodItems.setAlignment(Pos.BASELINE_CENTER);

        nHLvlTxt.setFont(Font.font("Helvetica", FontWeight.BOLD,25));
        nHLvlTxt.setTextFill(Color.BLACK);
        nHLvlTxt.setAlignment(Pos.CENTER);

        lvlNHStr = "NEIGHBORHOOD LEVEL: " + levelNeighborhood;
        nHLvlTxt.setText(lvlNHStr);
        nhBox.getChildren().addAll(nHLvlTxt);
        curLocation = "neighborhood";

        // We got new items so we must traverse the itemsMap and create buttons
        // for each item
        itemsMap.entrySet().forEach(entry->{
            // Create a new button for that item
            Button b = new Button(entry.getKey());

            // If the item has been passed, disable the button
            if (nHoodPassButtonList.contains(entry.getKey())){
                b.setDisable(true);
            }

            // Style the button b
            b.setStyle("-fx-background-color: SNOW;");
            b.setMaxSize(125,125);

            // b event handler
            b.setOnAction(e-> {
                // Don't disable the choose and submit buttons
                chooseBtn.setDisable(false);
                submitBtn.setDisable(false);

                // Set the text to the hint
                hintLabel.setText(entry.getKey());
                hintTxt.setText(entry.getValue());

                // update the itemName
                itemName = entry.getKey();

                // Set submit button to disable
                submitBtn.setDisable(true);

                // Get the submit page
                primaryStage.setScene(sceneMap.get("submit"));
            });
            neighborhoodItems.getChildren().addAll(b);
        });
        // Add the back button
        neighborhoodItems.getChildren().addAll(neighborhoodBack);
        nhBox.getChildren().addAll(neighborhoodItems);
    }

    // updatePark : method that adds buttons to the park scene
    public void updatePark(HashMap<String,String> items, Stage primaryStage) {
        // Set style and sizes for back button and spacing for vbox
        parkBack.setStyle("-fx-background-color: SNOW;");
        parkBack.setMaxSize(125,125);

        pBox.getChildren().clear();
        pBox.setAlignment(Pos.CENTER);
        pBox.setSpacing(15.0);
        pBox.relocate(260,140);

        parkItems.getChildren().clear();
        parkItems.setSpacing(15.0);
        parkItems.relocate(300,150);
        parkItems.setAlignment(Pos.BASELINE_CENTER);

        parkLvlTxt.setFont(Font.font("Helvetica", FontWeight.BOLD,25));
        parkLvlTxt.setTextFill(Color.BLACK);
        parkLvlTxt.setAlignment(Pos.CENTER);

        lvlParkStr = "PARK LEVEL: " + levelPark;
        parkLvlTxt.setText(lvlParkStr);
        pBox.getChildren().addAll(parkLvlTxt);
        curLocation = "park";

        // We got new items so we must traverse the itemsMap and create buttons
        // for each item
        itemsMap.entrySet().forEach(entry->{
            // Create a button for each item
            Button b = new Button(entry.getKey());

            // If the item has been passed, disable the button
            if (parkPassButtonList.contains(entry.getKey())){
                b.setDisable(true);
            }

            // Style the button
            b.setStyle("-fx-background-color: SNOW;");
            b.setMaxSize(125,125);

            // Button event handler
            b.setOnAction(e-> {
                chooseBtn.setDisable(false);
                submitBtn.setDisable(false);
                hintLabel.setText(entry.getKey());
                hintTxt.setText(entry.getValue());
                itemName = entry.getKey();
                submitBtn.setDisable(true);
                primaryStage.setScene(sceneMap.get("submit"));
            });

            parkItems.getChildren().addAll(b);
        });

        // Add the back button
        parkItems.getChildren().addAll(parkBack);
        pBox.getChildren().addAll(parkItems);
    }

    // updateZoo : method that adds buttons to the zoo scene
    public void updateZoo(HashMap<String,String> items, Stage primaryStage) {
        // Set style and sizes for back button and spacing for vbox
        zooBack.setStyle("-fx-background-color: SNOW;");
        zooBack.setMaxSize(125,125);

        zBox.getChildren().clear();
        zBox.setAlignment(Pos.CENTER);
        zBox.setSpacing(15.0);
        zBox.relocate(270,140);

        zooItems.getChildren().clear();
        zooItems.setSpacing(15.0);
        zooItems.relocate(270,125);
        zooItems.setAlignment(Pos.BASELINE_CENTER);

        curLocation = "zoo";
        zooLvlTxt.setFont(Font.font("Helvetica", FontWeight.BOLD,25));
        zooLvlTxt.setTextFill(Color.BLACK);
        zooLvlTxt.setAlignment(Pos.CENTER);
        zooLvlTxt.relocate(270,125);

        lvlZooStr = "ZOO LEVEL: " + levelZoo;
        zooLvlTxt.setText(lvlZooStr);
        zBox.getChildren().addAll(zooLvlTxt);

        // We got new items so we must traverse the itemsMap and create buttons
        // for each item
        itemsMap.entrySet().forEach(entry->{
            // Create buttons for each item
            Button b = new Button(entry.getKey());

            // If an item has already passed, disable the button
            if (zooPassButtonList.contains(entry.getKey())){
                b.setDisable(true);
            }
            b.setStyle("-fx-background-color: SNOW;");
            b.setMaxSize(125,125);

            // button event handler
            b.setOnAction(e-> {
                chooseBtn.setDisable(false);
                submitBtn.setDisable(false);
                hintLabel.setText(entry.getKey());
                hintTxt.setText(entry.getValue());
                itemName = entry.getKey();
                submitBtn.setDisable(true);
                primaryStage.setScene(sceneMap.get("submit"));
            });
            zooItems.getChildren().addAll(b);
        });

        // Add the back button
        zooItems.getChildren().addAll(zooBack);
        zBox.getChildren().addAll(zooItems);
    }

    // updateButtons : function that updates the lists so we know if we need to disable an item button or not
    public void updateButtons(Image d) {
        // If the Image sent from server contains pass that is true
        if (d.getPass() == true) {
            // Update the fileChoosen text to correct
            fileChoosen.setText("Correct!");

            // If the location is park
            if (curLocation.equals("park")) {
                // Add the item name to the local list
                parkPassLocalList.add(itemName);

                // Set the list from the database to equal the local list
                parkPassButtonList = new ArrayList<>(parkPassLocalList);

                // Update the database
                connection.updatePassed("Park", parkPassButtonList);
            }

            // If the location is zoo
            else if (curLocation.equals("zoo")) {
                // Add the item name to the local list
                zooPassLocalList.add(itemName);

                // Set the list from the database to equal the local list
                zooPassButtonList = new ArrayList<>(zooPassLocalList);

                // Update the database
                connection.updatePassed("Zoo", zooPassButtonList);
            }

            // If the location is neighborhood
            else if (curLocation.equals("neighborhood")) {
                // Add the item name to the local list
                nHoodPassLocalList.add(itemName);

                // Set the list from the database to equal the local list
                nHoodPassButtonList = new ArrayList<>(nHoodPassLocalList);

                // Update the database
                connection.updatePassed("Neighborhood", nHoodPassButtonList);
            }
        }
        // Image from the server's pass is false
        else {
            // Set the choose and submit button's as not disabled
            chooseBtn.setDisable(false);
            submitBtn.setDisable(false);

            // Prompt the user that the image sent in was incorrect
            fileChoosen.setText("No file has been submitted or\n"+"wrong image was submitted\n"+"Please submit the correct image");
        }
    }

    // GameDone page to notify when user completed a location
    public Scene GameDone(String info, Stage primaryStage){
        gameOver= new BorderPane();
        gameOver.setStyle("-fx-background-image:url(background_CompletedGame.gif) ; -fx-background-size: 100% 100% ");

        gamePOver = new Label("You have successfully completed the "+info+" level!");
        gamePOver.setFont(Font.font("Helvetica", FontWeight.BOLD,20));
        gamePOver.setTextFill(Color.WHITE);
        gameOver.setCenter(gamePOver);
        gamePOver.setAlignment(Pos.CENTER);

        backDone = new Button("Back");
        backDone.setStyle("-fx-background-color: SNOW;");
        backDone.setOnAction(e-> {
            updateThird(primaryStage);
            primaryStage.setScene(sceneMap.get("third"));
        });


        BorderPane.setAlignment(backDone, Pos.BOTTOM_CENTER);
        gameOver.setBottom(backDone);


        return new Scene(gameOver, 700, 750);

    }

}