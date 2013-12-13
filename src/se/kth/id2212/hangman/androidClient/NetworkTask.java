package se.kth.id2212.hangman.androidClient;

import se.kth.id2212.common.Request;
import se.kth.id2212.common.Response;
import se.kth.id2212.hangman.androidClient.client.Controller;
import android.os.AsyncTask;

public class NetworkTask extends AsyncTask<Request, Void, Response> {

	@Override
	protected Response doInBackground(Request... reqests) {
		// TODO Auto-generated method stub
		for (Request request : reqests) {
			return Controller.getInstance().getConnectionHandler().sendRequest(request);
		}
		return null;
	}

}
