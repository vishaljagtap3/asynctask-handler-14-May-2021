package in.bitcode.progressdialog_threading;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    Button mBtnDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnDownload = findViewById(R.id.btnDownload);

        mBtnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] urls = {
                        "https://bitcode.in/java/file1.java",
                        "https://bitcode.in/java/file2.java",
                        "https://bitcode.in/java/file3.java",
                        "https://bitcode.in/java/file4.java",
                };

                new DownloadThread(
                        MainActivity.this,
                        new MyHandler()
                ).execute(urls);
            }
        });
    }

    @Override
    protected void onDestroy() {
        Toast.makeText(this, "onDestroy", Toast.LENGTH_LONG).show();
        super.onDestroy();
    }

    //you can send message to a handler only from the thread on which it is created.

    class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg != null && msg.obj != null) {

                if(msg.what == 1) {
                    Integer[] progress = (Integer[]) msg.obj;
                    mBtnDownload.setText("Pri: " + progress[0] + " Sec: " + progress[1]);
                }
                if(msg.what == 2) {
                    Float res = (Float) msg.obj;
                    mBtnDownload.setText("Res = " + res);
                }
            }
        }
    }


    private void mt(String text) {
        Log.e("tag", text);
    }
}