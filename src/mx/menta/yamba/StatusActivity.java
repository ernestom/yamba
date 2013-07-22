package mx.menta.yamba;

// CMD + SHIFT + O

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class StatusActivity extends Activity {
	
	private static final String TAG = "StatusActivity";
	private Button statusButton;
	private EditText statusText;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        
        // TODO: rename ids
        statusButton = (Button) findViewById(R.id.status_button_status);
        statusText = (EditText) findViewById(R.id.status_text_status);
        
        statusButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				String status = statusText.getText().toString();
				Log.d(TAG, "onClick with text: " + status);
				new PostTask().execute(status);
			}
		});
    }
    
    //                                           <Params, Progress, Result>
    private class PostTask extends AsyncTask<String, Void, String> {
    	
    	private ProgressDialog dialog;
    	
    	@Override
    	protected void onPreExecute() {
    		super.onPreExecute();
    		dialog = ProgressDialog.show(StatusActivity.this, "Posting", "Please wait...");
    	}

		@Override
		protected String doInBackground(String... params) {
			YambaClient yamba = new YambaClient("student", "password");
			try {
				yamba.postStatus(params[0]);
				return "Succesfully posted";
			} catch (YambaClientException e) {
				Log.e(TAG, "Failed to post status", e);
				e.printStackTrace();
				return "Failed to post";
			}
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dialog.dismiss();
		}
    	
    }
    
}
