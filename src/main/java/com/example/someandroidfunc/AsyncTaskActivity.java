package com.example.someandroidfunc;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class AsyncTaskActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private Button downloadButton;
    private ImageView imageView;

    Random random = new Random();
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);

        progressBar = findViewById(R.id.asyncProgressBar);
        downloadButton = findViewById(R.id.asyncDownloadButton);
        imageView = findViewById(R.id.asyncImageView);

        mp = MediaPlayer.create(this,R.raw.sample);

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundTask().execute((Void)null);
            }
        });

    }

    public class BackgroundTask extends AsyncTask<Void, Integer, Void> {

        int sum = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setMax(100);
            progressBar.setProgress(0);

        }

        @Override
        protected Void doInBackground(Void... params) {
            while (sum <100) {
                sum+= random.nextInt(40);
                try {
                    publishProgress(sum);
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            imageView.setImageResource(R.drawable.gazelle);
            mp.start();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Integer currentProgress = values[0];
            progressBar.setProgress(currentProgress);
        }

        @Override
        protected void onCancelled(Void result) {
            super.onCancelled(result);

        }
    }
}
