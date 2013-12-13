/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.id2212.hangman.androidClient.client;

import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import se.kth.id2212.common.Request;
import se.kth.id2212.common.RequestStatus;
import se.kth.id2212.common.Response;
import se.kth.id2212.common.ResponseStatus;
import se.kth.id2212.hangman.androidClient.IView;
import se.kth.id2212.hangman.androidClient.NetworkTask;


/**
 *
 * @author Lucas
 */
public class Controller {

    private ConnectionHandler connectionHandler;
    private IView view;
    private static Controller singleton = null;

    public Controller() {

    }

    public static Controller getInstance(){
    	if(singleton == null){
    		singleton = new Controller();
    	}
    	return singleton;
    }
    public void setView(IView view) {
        this.view = view;
    }

    public boolean connect(String address, int port) {
        this.connectionHandler = new ConnectionHandler(address, port);
        //this.view.notifyConnected(connectionHandler.getStatus());
        return connectionHandler.getStatus();
    }

    

    public void guessLetter(char letter) {
        Request req = new Request(RequestStatus.GUESS_LETTER, letter);
        handleNetworkTask(req);
    }

    private void handleNetworkTask(Request req) {
    	NetworkTask task = new NetworkTask();
        task.execute(req);
        Response resp;
		try {
			resp = task.get();
	        handleResponse(resp);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void startNewGame() {
        Request req = new Request(RequestStatus.START_NEW_GAME);
        handleNetworkTask(req);
    }

    public void guessWord(String word) {
        Request req = new Request(RequestStatus.GUESS_WORD, word);
        handleNetworkTask(req);
    }

    public void handleResponse(Response resp) {
        ResponseStatus s = resp.getStatus();
        int score = resp.getScore();
        int leftTries = resp.getLeftTries();
        String currentWord = resp.getHiddenWord();
        
        view.notifyScore(score);
        view.notifyMessage(s);
        view.notifyLeftTries(leftTries);
        view.notifyCurrentWord(currentWord);

        System.out.println(resp);
    }

	public ConnectionHandler getConnectionHandler() {
		return connectionHandler;
	}
    
    
}
