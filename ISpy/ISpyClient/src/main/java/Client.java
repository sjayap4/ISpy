//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.io.Serializable;
//import java.net.Socket;
//import java.util.Scanner;
//import java.util.function.Consumer;
//
////Client class
//public class ClientNetworkingThread extends Thread {
//    Socket socketClient;//socket
//    ObjectOutputStream out;
//    ObjectInputStream in;
//    private String IPAddress;//the ip address that the client gives
//    private int portNumber;//the port Number the client gives
//    private Consumer<Serializable> callback;//used to receive info from the server
//
//    //Client class constructor
//    ClientNetworkingThread(Consumer<Serializable> call,String IP, int port){
//        this.callback = call;
//        this.IPAddress=IP;
//        this.portNumber = port;
//    }
//
//    //returns port Number
//    int getPortNumber(){
//        return this.portNumber;
//    }
//
//    //returns IPAddress
//    String getIPAddress(){
//        return this.IPAddress;
//    }
//
//
//    //runs the Client with the Socket
//    public void run() {
//        try {
//            socketClient = new Socket(IPAddress, portNumber);
//            out = new ObjectOutputStream(socketClient.getOutputStream());
//            in = new ObjectInputStream(socketClient.getInputStream());
//            socketClient.setTcpNoDelay(true);
//        } catch (Exception e) {
//
//        }
//
//        //reads and accepts info from the Server
//        while (true) {
//            try {
//                String info = in.readObject().toString();
//                callback.accept(info);
//            }
//            catch (Exception e) {
//
//            }
//        }
//    }
//    //function to send data
//    public void send(String info){
//        try{
//            out.writeObject(info);
//        }
//        catch(IOException e){
//            e.printStackTrace();
//        }
//    }
//}
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;


// Client: a client object to send information to the server
public class Client extends Thread{


    Socket socketClient;
    ObjectOutputStream out;
    ObjectInputStream in;

    private Consumer<Serializable> callback;

    // client constructor
    Client(Consumer<Serializable> call){
        callback = call;
    }

    // run reads in objects if there is incoming objects coming in the stream and accepts it
    public void run() {
        try {
            socketClient= new Socket("127.0.0.1",5555);
            out = new ObjectOutputStream(socketClient.getOutputStream());
            in = new ObjectInputStream(socketClient.getInputStream());
            socketClient.setTcpNoDelay(true);
        }
        catch(Exception e) {}
        while(true) {
            try {
                Image info = (Image) in.readObject();
                callback.accept(info);
            }
            catch(Exception e) {}
        }
    }

    // send writes out to the output stream
    public void send(Image info) {
        try {
            out.writeObject(info);
            out.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
