package se.kth.id2212.hangman.androidClient;

import se.kth.id2212.common.ResponseStatus;


public interface IView {

	public void notifyScore(int score);
	public void notifyLeftTries(int leftTries);
	public void notifyCurrentWord(String currentWord);
	public void notifyConnected(boolean status);
	public void notifyMessage(ResponseStatus s);
}
