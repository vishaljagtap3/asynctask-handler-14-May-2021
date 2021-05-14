package in.bitcode.progressdialog_threading;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class DownloadThread extends AsyncTask<String, Integer, Float> {

    private ProgressDialog mProgressDialog;
    private Context mContext;
    private Handler mHandler;

    public DownloadThread(Context context, Handler handler) {
        this.mContext = context;
        mHandler = handler;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("BitCode Services");
        mProgressDialog.setMessage("Downloading....");
        mProgressDialog.setIcon(R.mipmap.ic_launcher);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.show();

    }

    @Override
    protected Float doInBackground(String ... urls) {

        int curProgress = 0;

        for(String url : urls) {

            String [] parts = url.split("/");

            for (int i = 0; i <= 100; i++) {

                mProgressDialog.setSecondaryProgress(i);

                Integer [] progress = new Integer[2];
                progress[0] = curProgress;
                progress[1] = i;
                publishProgress(progress);

                //To be discussed later
                /*Message message = new Message();
                message.obj = progress;
                message.what = 1;
                mHandler.sendMessage(message);*/

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            curProgress += (100/urls.length);
            mProgressDialog.setProgress( curProgress);
        }

        return 12.12F;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        Message message = new Message();
        message.obj = progress;
        message.what = 1;
        mHandler.sendMessage(message);
    }

    @Override
    protected void onPostExecute(Float res) {
        super.onPostExecute(res);
        mProgressDialog.dismiss();

        Message message = new Message();
        message.obj = res;
        message.what = 2;
        mHandler.sendMessage(message);
    }
}
