
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Consumer;

// GameServer: a server class for the server application

public class GameServer{

    // Set count to 1
    int count = 1;

    // Initialize ArrayList and HashMaps
    ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
    ArrayList<Integer> clientsInRoom = new ArrayList<Integer>();
    HashMap<String, ClientThread> numberToThread = new HashMap<String, ClientThread>();

    // Create an instance of TheServer class
    TheServer server;

    // Define a Consumer object
    private Consumer<Serializable> callback;

    // Initialize an Image object
    Image info = new Image();

    // GameServer constructor
    GameServer(Consumer<Serializable> call){
        callback = call;
        server = new TheServer();
        server.start();
    }

    // Nested TheServer class
    public class TheServer extends Thread{

        // Run Function
        public void run() {
            try(ServerSocket mysocket = new ServerSocket(5555);){
                // While the server is running
                while(true) {
                    // Update the server message when a new thread comes in
                    ClientThread c = new ClientThread(mysocket.accept(), count);
                    info.setServerMessage("client has connected to server.");
                    clientsInRoom.add(count);
                    callback.accept(info);
                    numberToThread.put("Client #" + Integer.toString(count), c);
                    clients.add(c);
                    c.start();
                    count++;
                }
            }//end of try
            catch(Exception e) {
                callback.accept("Server socket did not launch");
            }
        }//end of while
    }

    // ClientThread class which keeps track of all the clients
    class ClientThread extends Thread{
        // Create a Socket instance
        Socket connection;

        // count variable
        int count;

        // Image object
        Image clientInfo;

        // Input and Output streams
        ObjectInputStream in;
        ObjectOutputStream out;

        // ClientThread constructor
        ClientThread(Socket s, int count){
            this.connection = s;
            this.count = count;
            clientInfo = new Image();
        }

        // Returns the count
        public Integer getCount() {
            return count;
        }

        // updateSpecificClients function
        public synchronized void updateSpecificClients(Image info) {
            // Update every recipient in the arraylist
            ArrayList<String> recipients = info.getRecipients();
            for(String s : recipients) {
                // Assign a number to the client thread
                ClientThread t = numberToThread.get(s);
                try {
                    // Write out to that client object
                    t.out.writeObject(info);
                    out.flush();
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // updateClients function
        public synchronized void updateClients(Image information) {
            // Update every client
            for(int i = 0; i < clients.size(); i++) {
                // Get the client thread
                ClientThread t = clients.get(i);
                // Write out to that client
                try {
                    t.out.writeObject(information);
                    out.flush();
                }
                catch(Exception e) {}
            }
        }

        // Run
        public void run(){
            // Define the in and output streams
            try {
                in = new ObjectInputStream(connection.getInputStream());
                out = new ObjectOutputStream(connection.getOutputStream());
                connection.setTcpNoDelay(true);
            }
            catch(Exception e) {
                System.out.println("Streams not open");
            }

            // New client joined, update client's message to notify the clients for info
            clientInfo.setClientMessage("new client on server: client");

            // Reset the new connected clients in the room and update info
            clientInfo.setClientsInRoom(clientsInRoom);
            info.addRecipients("Client #" + Integer.toString(count));


            // Send info to update the clients
            updateClients(clientInfo);
            updateSpecificClients(info);

            while(true) {
                try {
                    // Read in an object
                    info = (Image) in.readObject();

                    // Update server message to reflect the username and the image file that was sent
                    info.setServerMessage("client "+info.getUserName()+" sent image file: "+ info.getimageFile().getName() + " to get " + info.getItemName());

                    // accept the info and update
                    callback.accept(info);
                    updateSpecificClients(info);

                    // set imageName to equal the file's name
                    String imageName = info.getimageFile().getName();

                    // split string to get format
                    String delims = "[.]";
                    String[] tokens = imageName.split(delims);
                    String getFormat = tokens[tokens.length - 1];

                    // create instance of AwsConnection
                    AwsConnection checkConnection = new AwsConnection(info, getFormat);
                    boolean correctImage = checkConnection.checkConnection();

                    // if image is correct
                    if(correctImage == true){
                        // set the pass to true and update server message
                        info.setPass(true);
                        info.setServerMessage(imageName + " is correct");

                        // update the clients
                        updateClients(info);
                    }

                    // else if image isn't correct
                    else if (correctImage == false){
                        //picture is not accepted and set pass to false and update server message
                        info.setPass(false);
                        info.setServerMessage(imageName + " is wrong");

                        // update the clients
                        updateClients(info);
                    }

                    // Reset everything
                    callback.accept(info);
                    info.clearRecipients();
                    info.setPass(false);
                    info.setClientMessage(null);
                }
                catch(Exception e) {
                    System.out.println(e);

                    // Client disconnected, update server message
                    info.setServerMessage("Client #" + count + " disconnected");
                    Image disconnectClient = new Image();
                    disconnectClient.setClientMessage("Client #" + count + " has left the server!");

                    // Set info's connection status to false
                    info.setConnStatus(false);

                    callback.accept(info);

                    // Create a new arraylist of recipients
                    ArrayList<String> recipients = new ArrayList<String>();

                    // Set the disconnected index to the disconnected client number
                    int disconnectedIndex = 0;
                    for ( int i = 0; i < clientsInRoom.size(); i++) {
                        if (clientsInRoom.get(i) == count) {
                            disconnectedIndex = i;
                        }
                        else {
                            recipients.add("Client #" + Integer.toString(clientsInRoom.get(i)));
                        }
                    }

                    // Remove the disconnected index
                    clientsInRoom.remove(disconnectedIndex);
                    numberToThread.remove("Client #" + Integer.toString(count));
                    disconnectClient.setClientsInRoom(clientsInRoom);
                    disconnectClient.setRecipients(recipients);
                    disconnectClient.setDisconnected(count);
                    updateSpecificClients(disconnectClient);

                    // If there are no longer any clients in the room
                    if (clientsInRoom.size() == 0) {
                        disconnectClient.clearRecipients();
                        info.clearRecipients();
                    }
                    clients.remove(this);

                    break;
                }
            }
        }//end of run


    }//end of client thread
}







