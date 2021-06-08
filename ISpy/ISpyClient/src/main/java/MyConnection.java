import javafx.scene.control.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;
import java.sql.*;
import java.sql.DriverManager;

// MyConnection: a class that allows client to connect to the database
public class MyConnection {
    Connection con;
    String userName, password;
    String savedPark, savedNeighborhood, savedZoo;
    int parkLevel, nhLevel, zooLevel;

    // Query to access the items table
    String query = "SELECT * FROM iSpy.items WHERE ";

    // Strings to store the results of the query
    HashMap<String, String> results = new HashMap<>();

    //connect to sql
    public void connect() throws FileNotFoundException {
        String MySQLURL = "jdbc:mysql://localhost:3306/iSpy?useSSL=false";

        try {
            // open connection
            con = DriverManager.getConnection(MySQLURL, "root", "tbtrs1964");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // getArea: concats query so that it searches where the areaName is the place entered in
    public void getAreaAndLevel(int level, String areaName) {
        query += "itemLevel = ";
        query += level;
        query += " AND (areaName = '";
        query += areaName;
        query += "' OR areaName = 'All');";
    }

    // getItems: get the items that stores the results into the strings itemName and hint
    public int getItems() {
        ResultSet resultSet = null;

        try {
            // If the connection is connected
            if (con != null) {
                // Execute the query
                Statement stmt = con.createStatement();
                resultSet = stmt.executeQuery(query);

                //If the result is null
                if (resultSet == null) {
                    System.out.println("ERROR: there are no items here. :(");
                }

                // If the result returns something
                else {
                    // Loop through storing the resultSet into its respective string
                    while (resultSet.next()) {
                        // Add the results (item name and hint) and store into results
                        results.put(resultSet.getString("itemName"), resultSet.getString("hint"));
                    }
                }
            }
        } catch (Exception e) {
            return 0; // Failed, return 0
        }
        return 1; // Success, return 1
    }

    // updateAccountLevel : updates the account's account level
    public void updateAccountLevel(String area, int lvl) {
        try {
            // Check for connection
            if (con != null) {
                // Update table with query
                Statement stmt = con.createStatement();

                // update iSpy.logininfo
                if (lookForUser(userName) == true) {
                    String addItems;
                    if (area.equals("Park")) {
                        addItems = "UPDATE iSpy.logininfo SET parkLevel=\"" + lvl + "\" WHERE UserName='" + userName + "' AND Password='" + password +"'";
                    }
                    else if (area.equals("Neighborhood")) {
                        addItems = "UPDATE iSpy.logininfo SET nborhoodLevel=\"" + lvl + "\" WHERE UserName='" + userName + "' AND Password='" + password +"'";
                    }
                    else {
                        addItems = "UPDATE iSpy.logininfo SET zooLevel=\"" + lvl + "\" WHERE UserName='" + userName + "' AND Password='" + password +"'";
                    }

                    stmt.executeUpdate(addItems);
                }
            }
        }
        // Failed
        catch(Exception e){
            e.printStackTrace();
        }
    }

    // updateAccountLevel : updates the account's passedPark column
    public void updatePassed(String area, List<String> list) {
        String items = "";
        try {
            // Check for connection
            if (con != null) {
                // Update table with query
                Statement stmt = con.createStatement();
                for (String name : list) {
                    items += name;
                    items += "\n";
                }
                // update iSpy.logininfo
                if (lookForUser(userName) == true) {
                    String addItems;
                    if (area.equals("Park")) {
                        addItems = "UPDATE iSpy.logininfo SET passedPark=\"" + items + "\" WHERE UserName='" + userName + "' AND Password='" + password +"'";
                    }
                    else if (area.equals("Neighborhood")) {
                        addItems = "UPDATE iSpy.logininfo SET passedNH=\"" + items + "\" WHERE UserName='" + userName + "' AND Password='" + password +"'";
                    }
                    else {
                        addItems = "UPDATE iSpy.logininfo SET passedZoo=\"" + items + "\" WHERE UserName='" + userName + "' AND Password='" + password +"'";
                    }

                    stmt.executeUpdate(addItems);
                }
            }
        }
        // Failed
        catch(Exception e){
            e.printStackTrace();
        }
    }

    // printResult: prints out the hash map holding all the values
    public void printResult() {
        results.entrySet().forEach(entry->{
            System.out.println(entry.getKey() + " " + entry.getValue());
        });
    }

    // getResults: Returns the HashMap containing the results
    public HashMap<String, String> getResults() {
        return results;
    }

    // clear: resets the query and clears the hash map containing the results
    // of the returned values from the query
    public void clear() {
        query = "SELECT * FROM iSpy.items WHERE ";
        results.clear();
    }

    //add log in
    public void addLogIn(String user, String pass, String email){
        StringBuilder login = new StringBuilder();
        try{
            // Check for connection
            if (con != null) {
                // Update table with query
                Statement stmt = con.createStatement();
                userName = user;
                password = pass;
                login.append("INSERT INTO iSpy.LoginInfo (userName, password, email) VALUES ('");
                login.append(user);
                login.append("', '");
                login.append(pass);
                login.append("', '");
                login.append(email);
                login.append("');");

                stmt.executeUpdate(login.toString());
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    //look for user in database
    public Boolean lookForUser(String user){
        String currentQ = "";
        String currentUser = "";
        try{

            // Check to see if there is connection
            if (con != null) {
                Statement stmt = con.createStatement();
                currentQ += "SELECT Username FROM iSpy.LoginInfo WHERE Username= '" + user + "'";
                ResultSet result = stmt.executeQuery(currentQ);

                // If there is a user then return true if user is found
                while(result.next()){
                    currentUser = result.getString("Username");
                    if(currentUser == null){
                        return false;
                    }
                    else{
                        userName = user;
                        return true;
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    // Checks to see if password and username match
    public Boolean checkPass(String user, String pass){
        String currentQ = "";

        try{
            // Makes sure there's a connection
            if (con != null) {
                Statement stmt = con.createStatement();
                currentQ += "SELECT Username FROM iSpy.LoginInfo WHERE Username= '" + user + "' AND Password= '" + pass + "'";
                ResultSet result = stmt.executeQuery(currentQ);

                //goes through the result
                while(result.next()){
                    userName = user;
                    password = pass;
                    return true;
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    // getSavedZoo : returns the saved zoo items for account
    public String getSavedZoo() {
        String currentQ = "";

        try{
            // Makes sure there's a connection
            if (con != null) {
                Statement stmt = con.createStatement();
                currentQ += "SELECT * FROM iSpy.LoginInfo WHERE Username= '" + userName + "' AND Password= '" + password + "'";
                ResultSet result = stmt.executeQuery(currentQ);

                //goes through the result
                while(result.next()){
                    savedZoo = result.getString("passedZoo");
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return savedZoo;
    }

    // getSavedNeighborhood : returns the saved neighborhood items for account
    public String getSavedNeighborhood() {
        String currentQ = "";

        try{
            // Makes sure there's a connection
            if (con != null) {
                Statement stmt = con.createStatement();
                currentQ += "SELECT * FROM iSpy.LoginInfo WHERE Username= '" + userName + "' AND Password= '" + password + "'";
                ResultSet result = stmt.executeQuery(currentQ);

                //goes through the result
                while(result.next()){
                    savedNeighborhood = result.getString("passedNH");
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return savedNeighborhood;
    }

    // getSavedPark : returns the saved park items for account
    public String getSavedPark() {
        String currentQ = "";

        try{
            // Makes sure there's a connection
            if (con != null) {
                Statement stmt = con.createStatement();
                currentQ += "SELECT * FROM iSpy.LoginInfo WHERE Username= '" + userName + "' AND Password= '" + password + "'";
                ResultSet result = stmt.executeQuery(currentQ);

                //goes through the result
                while(result.next()){
                    savedPark = result.getString("passedPark");
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return savedPark;
    }

    // getParkLvl : returns the saved park level for account
    public int getParkLvl() {
        String currentQ = "";

        try{
            // Makes sure there's a connection
            if (con != null) {
                Statement stmt = con.createStatement();
                currentQ += "SELECT * FROM iSpy.LoginInfo WHERE Username= '" + userName + "' AND Password= '" + password + "'";
                ResultSet result = stmt.executeQuery(currentQ);

                //goes through the result
                while(result.next()){
                    parkLevel = result.getInt("parkLevel");
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return parkLevel;
    }

    // getZooLvl : returns the saved zoo level for account
    public int getZooLvl() {
        String currentQ = "";

        try{
            // Makes sure there's a connection
            if (con != null) {
                Statement stmt = con.createStatement();
                currentQ += "SELECT * FROM iSpy.LoginInfo WHERE Username= '" + userName + "' AND Password= '" + password + "'";
                ResultSet result = stmt.executeQuery(currentQ);

                //goes through the result
                while(result.next()){
                    zooLevel = result.getInt("zooLevel");
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return zooLevel;
    }

    // getNhLvl : returns the saved neighborhood level for account
    public int getNhLvl() {
        String currentQ = "";

        try{
            // Makes sure there's a connection
            if (con != null) {
                Statement stmt = con.createStatement();
                currentQ += "SELECT * FROM iSpy.LoginInfo WHERE Username= '" + userName + "' AND Password= '" + password + "'";
                ResultSet result = stmt.executeQuery(currentQ);

                //goes through the result
                while(result.next()){
                    nhLevel = result.getInt("nborhoodLevel");
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return nhLevel;
    }

    // setSQL: set safe SQL setting
    public void setSQL() {
        String currentQ = "SET SQL_SAFE_UPDATES = 0;";
        try {
            // Check for connection
            if (con != null) {
                // Update table with query
                Statement stmt = con.createStatement();

                stmt.executeUpdate(currentQ);
            }
        }
        // Failed
        catch(Exception e){
            e.printStackTrace();
        }
    }

    // clearPassed: sets a saved location's list of passed items to null
    public void clearPassed(String location) {
        setSQL();
        String currentQ = "UPDATE iSpy.logininfo SET";
        try {
            // Check for connection
            if (con != null) {
                // Update table with query
                Statement stmt = con.createStatement();

                // update iSpy.logininfo
                if (lookForUser(userName) == true) {
                    if (location.equals("park")) {
                        currentQ += " passedPark = NULL WHERE UserName='" + userName + "' AND Password='" + password +"'";
                    }
                    else if (location.equals("neighborhood")) {
                        currentQ += " passedNH = NULL WHERE UserName='" + userName + "' AND Password='" + password +"'";
                    }
                    else {
                        currentQ += " passedZoo = NULL WHERE UserName='" + userName + "' AND Password='" + password +"'";
                    }
                    stmt.executeUpdate(currentQ);
                }
            }
        }
        // Failed
        catch(Exception e){
            e.printStackTrace();
        }
    }
}