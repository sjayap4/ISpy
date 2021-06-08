//import java.io.Serializable;
//import java.util.ArrayList;
//
//public class Image implements Serializable {
//    File imageFile;
//    String message;
//    // Recipients holds clients that we're sending to
//    ArrayList<String> recipients;
//    Image(String name) {
//        imageFile = name;
//        recipients = new ArrayList<String>();
//    }
//
//    public String getimageFile() {
//        return imageFile;
//    }
//    public void setimageFile(String img){
//        imageFile = img;
//    }
//    public String getMessage(){
//        return message;
//    }
//    public void setMessage(String msg){
//        message = msg;
//    }
//    // GETRECIPIENTS: returns the recipient ArrayList
//    public ArrayList<String> getRecipients() {
//        return recipients;
//    }
//
//    // SETRECIPIENTS: sets the new recipients ArrayList
//    public void setRecipients(ArrayList<String> newRecipients) {
//        recipients.clear();
//        for ( int i = 0; i < newRecipients.size(); i++) {
//            recipients.add(newRecipients.get(i));
//        }
//    }
//
//}
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

// Image: a class to be sent between the server and the client
public class Image implements Serializable {

    // Recipients holds clients that we're sending to
    ArrayList<String> recipients;

    // List of clients in the room
    ArrayList<Integer> clientsInRoom;

    // id holds the client's id number
    int id = 0;

    // id holds the disconnected id number
    int disconnected = -1;

    // boolean that holds the connection status
    boolean connStatus = true;

    // String that holds message being sent to clients
    String messageToClients;

    // String that holds message being sent to server
    String messageToServer;

    // String that holds the username of the client
    String userName;

    // File that sends in a jpeg
    File imageFile;

    // String that holds the item name of the item we're looking for
    String itemName;

    // boolean that holds whether the image holds the item
    boolean pass = false;

    // constructor: initialize the ArrayList
    Image() {
        recipients = new ArrayList<String>();
        clientsInRoom = new ArrayList<Integer>();
    }

    // Setters and getters for the username
    public String getUserName() {
        return userName;
    }
    public void setUserName(String name) {
        userName = name;
    }

    // Setters and getters if the user sent in the correct image
    public boolean getPass() {
        return pass;
    }
    public void setPass(boolean result) {
        pass = result;
    }

    // Setters and getters for the image files
    public File getimageFile() {
        return imageFile;
    }
    public void setimageFile(File img) {
        imageFile = img;
    }

    // Setters and getters for the item names
    public void setItemName(String img){
        itemName=img;
    }
    public String getItemName(){
        return itemName;
    }

    // Setters and getters for the recipients
    public ArrayList<String> getRecipients() {
        return recipients;
    }
    public void setRecipients(ArrayList<String> newRecipients) {
        recipients.clear();
        for ( int i = 0; i < newRecipients.size(); i++) {
            recipients.add(newRecipients.get(i));
        }
    }

    // Clears the recipients arraylist
    public void clearRecipients() {
        recipients.clear();
    }

    // Adds a value to recipients
    public void addRecipients(String clientNum) {
        recipients.add(clientNum);
    }

    // Set the disconnected client number of the
    public void setDisconnected(int clientNum) {
        disconnected = clientNum;
    }

    // Set the connection status
    public void setConnStatus(boolean status) {
        connStatus = status;
    }

    // Setters and getters for the client messages
    public String getClientMessage() {
        return messageToClients;
    }
    public void setClientMessage(String newMessage) {
        messageToClients = newMessage;
    }

    // Setters and getters for the server messages
    public String getServerMessage() {
        return messageToServer;
    }
    public void setServerMessage(String newMessage) {
        messageToServer = newMessage;
    }

    // Sets clientsInRoom
    public void setClientsInRoom(ArrayList<Integer> list) {
        clientsInRoom.clear();
        for ( int i = 0; i < list.size(); i++) {
            clientsInRoom.add(list.get(i));
        }
    }


}