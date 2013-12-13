package se.kth.id2212.hangman.androidClient;

import se.kth.id2212.common.ResponseStatus;
import se.kth.id2212.hangman.androidClient.client.Controller;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GameActivity extends Activity implements IView {
	TextView score;
	TextView message;
	TextView triesLeft;
	TextView word;
	EditText guessField;
	private Controller controller;
	private Button guessButton;
	private Button newGameButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		score = (TextView) findViewById(R.id.score);
		message = (TextView) findViewById(R.id.message);
		triesLeft = (TextView) findViewById(R.id.triesLeft);
		guessField = (EditText) findViewById(R.id.guessField);
		word = (TextView) findViewById(R.id.word);
		this.controller = Controller.getInstance();
		controller.setView(this);
		Controller.getInstance().startNewGame();
		guessButton = (Button) findViewById(R.id.guess);
		guessButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String guess = guessField.getText().toString();
				Log.i("Guess", guess);
				if(guess.length()==1){
					controller.guessLetter(guess.charAt(0));
				}else{
					controller.guessWord(guess);
				}
				guessField.setText("");
			}
		});
		
		newGameButton = (Button) findViewById(R.id.newGameButton);
		newGameButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.startNewGame();
				setGuessEnabled(true);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	@Override
	public void notifyScore(int score) {
		this.score.setText("Score: "+ score);
		
	}

	@Override
	public void notifyLeftTries(int leftTries) {
		this.triesLeft.setText("Tries left: "+ leftTries);
		
	}

	@Override
	public void notifyCurrentWord(String currentWord) {

		this.word.setText("Current word: "+ currentWord);
		
	}

	@Override
	public void notifyConnected(boolean connected) {
		 //TODO newGameButton.setEnabled(connected);

	        if (connected) {
	            message.setText("Connected");
	        } else {
	        	message.setText("Error");
	        }
		
	}

	@Override
	public void notifyMessage(ResponseStatus s) {
		switch (s) {
	        case PLAYED:
	            message.setText("Play time! Type a letter or a word!");
	            break;
	        case LOST:
	        	guessButton.setEnabled(false);
	            message.setText("Haha you lost!! Score decreased!");
	            break;
	        case WON:
	            guessButton.setEnabled(false);
	            message.setText("Genius! Score increased!");
	            break;
	    }
		
	}

	private void setGuessEnabled(boolean bool) {
		guessButton.setEnabled(bool);
		
	}

}
