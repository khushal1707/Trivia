package com.example.trivia.controller;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
//singleton hai ye .... faltu saala

public class AppControler extends Application {
    public static final String TAG = AppControler.class.getSimpleName();
    private static AppControler mInstance;
    //private Context mContext;
    private RequestQueue mRequestQueue;

   // private AppControler(Context mContext) {
      //  this.mContext = mContext;
  //  }


    public static synchronized AppControler getInstance() {
//        if (mInstance == null) {
//            mInstance = new AppControler(context);
//        }
        return mInstance;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequest(Object tag){
        if(mRequestQueue != null){
            mRequestQueue.cancelAll(tag);
        }
    }

//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(this);
//    }


}
