package in.bitcode.progressdialog_threading;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    ProgressDialog mProgressDialog;
    Button mBtnDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnDownload = findViewById(R.id.btnDownload);

        mBtnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadThread().execute( (String[] )null );
            }
        });
    }


    class DownloadThread extends AsyncTask<String, Integer, Float> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mt("onPre Thread: " + Thread.currentThread().getName() );

            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("BitCode Services");
            mProgressDialog.setMessage("Downloading....");
            mProgressDialog.setIcon(R.mipmap.ic_launcher);
            //mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.show();
        }

        @Override
        protected Float doInBackground(String... strings) {

            mt("doInBG Thread: " + Thread.currentThread().getName() );

            for(int i = 0; i <= 100; i++) {
                mt("Downloading: " + i + "%");
                mProgressDialog.setProgress(i);
                //mBtnDownload.setText(i+"%");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Float aFloat) {
            super.onPostExecute(aFloat);
            mt("OnPost Thread: " + Thread.currentThread().getName() );
            mProgressDialog.dismiss();
        }
    }

    private void mt(String text) {
        Log.e("tag", text);
    }
}