package se.kth.id2212.hangman.androidClient.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import se.kth.id2212.common.Request;
import se.kth.id2212.common.Response;


/**
 *
 * @author Lucas
 */
public class ConnectionHandler {

    Socket server;
    ObjectOutputStream oos;
    ObjectInputStream ois;

    public ConnectionHandler() {
        try {
            this.server = new Socket("localhost", 5555);
            this.oos = new ObjectOutputStream(server.getOutputStream());
            this.ois = new ObjectInputStream(server.getInputStream());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public ConnectionHandler(String address, int port) {
        try {
            this.server = new Socket(address, port);
            this.oos = new ObjectOutputStream(server.getOutputStream());
            this.ois = new ObjectInputStream(server.getInputStream());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public boolean getStatus() {
        try {
            return this.server.isConnected();
        } catch(Exception e) {
            return false;
        }
            
    }

    public Response sendRequest(Request req) {

        try {
            this.oos.writeObject(req);
            this.oos.flush();
            this.oos.reset();

            return (Response) ois.readObject();
        } catch (IOException  ex) {
            System.err.println(ex.getMessage());
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return null;
    }
}
