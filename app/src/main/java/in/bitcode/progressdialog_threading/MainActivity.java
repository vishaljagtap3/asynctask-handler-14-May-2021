package in.bitcode.progressdialog_threading;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

                String [] urls = {
                        "https://bitcode.in/java/file1.java",
                        "https://bitcode.in/java/file2.java",
                        "https://bitcode.in/java/file3.java",
                        "https://bitcode.in/java/file4.java",
                };

                new DownloadThread().execute( urls );
            }
        });
    }

    @Override
    protected void onDestroy() {
        Toast.makeText(this, "onDestroy", Toast.LENGTH_LONG).show();
        super.onDestroy();
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
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            //mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.show();

        }

        @Override
        protected Float doInBackground(String ... urls) {

            mt("doInBG Thread: " + Thread.currentThread().getName() );
            int curProgress = 0;

            for(String url : urls) {

                String [] parts = url.split("/");

                for (int i = 0; i <= 100; i++) {
                    mt("Downloading: " + parts[parts.length-1]);
                    mProgressDialog.setSecondaryProgress(i);
                    //mBtnDownload.setText(i+"%");
                    curProgress += curProgress + i;
                    mProgressDialog.setProgress( curProgress/url.length());

                    Integer [] progress = new Integer[2];
                    progress[0] = curProgress;
                    progress[1] = i;
                    publishProgress(progress);

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //curProgress += (100/urls.length);
                //mProgressDialog.setProgress( curProgress);
            }

            return 12.12F;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            mBtnDownload.setText("Pri: " + progress[0]  + " Sec: " + progress[1]);
        }

        @Override
        protected void onPostExecute(Float res) {
            super.onPostExecute(res);
            mt("OnPost Thread: " + Thread.currentThread().getName() );
            mProgressDialog.dismiss();
            mBtnDownload.setText(res + "");
        }
    }

    private void mt(String text) {
        Log.e("tag", text);
    }
}