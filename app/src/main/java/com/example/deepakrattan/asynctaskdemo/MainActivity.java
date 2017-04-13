package com.example.deepakrattan.asynctaskdemo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText edtSleepTime;
    private TextView txtResult;
    private Button btnAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //findViewbyId
        edtSleepTime = (EditText) findViewById(R.id.edtTime);
        txtResult = (TextView) findViewById(R.id.txtResult);
        btnAsyncTask = (Button) findViewById(R.id.btnAsyncTask);

        btnAsyncTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sleepTime = edtSleepTime.getText().toString().trim();
                new MyAsyncTask().execute(sleepTime);

            }
        });
    }

    private class MyAsyncTask extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;
        String res;

        @Override
        protected String doInBackground(String... strings) {
            publishProgress("Sleeping...");
            long sleepTime = Long.parseLong(strings[0]) * 1000; //Convert sleep time in milliseconds
            try {
                Thread.sleep(sleepTime);
                res = "Slept for " + strings[0] + " seconds.Please continue using the app";
            } catch (InterruptedException e) {
                e.printStackTrace();
                res = e.getMessage();
            }
            return res;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Wait for "+edtSleepTime.getText().toString()+" seconds");
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            txtResult.setText(res);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            txtResult.setText(values[0]);

        }
    }
}
