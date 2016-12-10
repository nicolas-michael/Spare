package com.example.nicolas.spotifyshare;

import android.os.AsyncTask;

/**
 * Created by Nicolas on 10/2/2016.
 */
public abstract class ExceptionAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    private Exception e = null;
    private Params[] params;

    @Override
    final protected Result doInBackground(Params... params) {
        try {
            this.params = params;
            return doInBackground();
        }
        catch (Exception e) {
            this.e = e;
            return null;
        }
    }

    abstract protected Result doInBackground() throws Exception;

    public void executeInParallel() {
        super.executeOnExecutor(ExceptionAsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    final protected void onPostExecute(Result result) {
        if (e != null) {
            onException(e);
        } else {
            onSuccess(result);
        }
    }

    abstract protected void onException(Exception e);

    abstract protected void onSuccess(Result result);

    public Params[] getParams() {
        return params;
    }

}
