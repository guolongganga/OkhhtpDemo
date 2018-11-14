package com.example.hp.okhhtpdemo;

import android.app.Dialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private String url = "http://api.expoon.com/AppNews/getNewsList/type/1/";
    private int p = 1;
    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView recyclerView;
    private List<NewsBean.DataBean> data;
    private MyAdapter myAdapter;
    private final int pageSize = 10;
    private Dialog dialog;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e(TAG, "onCreate: " + "onCreat");
        smartRefreshLayout = findViewById(R.id.smart);
        recyclerView = findViewById(R.id.recycler_view);
        WeiboDialogUtils.showprogress(MainActivity.this, "正在加载中......", true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /**
                 * 因为是启动界面  所以会有所延时  在其他界面是没有任何问题的
                 */
               WeiboDialogUtils.dismissprogress();
               getServerFrom();

            }
        },1000);


        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                smartRefreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        data.clear();
                        getServerFrom();
                        myAdapter.notifyDataSetChanged();
                        smartRefreshLayout.finishRefresh();
                        smartRefreshLayout.resetNoMoreData();


                    }
                }, 1000);

            }
        });
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                smartRefreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int itemCount = myAdapter.getItemCount();
                        if (itemCount == data.size()) {

                            smartRefreshLayout.finishLoadmoreWithNoMoreData();
                            //smartRefreshLayout.resetNoMoreData();

                        } else {
                            p++;
                            getServerFrom();
                            smartRefreshLayout.finishLoadmore();
                        }


                    }
                }, 1000);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
       // WeiboDialogUtils.showprogress(MainActivity.this, "正在加载中......", true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // WeiboDialogUtils.showprogress(MainActivity.this,"正在加载中......",true);
    }

    private void getServerFrom() {


        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        StringBuffer buffer = new StringBuffer(url);
        StringBuffer append = buffer.append("p").append("/").append(p);
        String url1 = url + append;
        Log.e(TAG, "onCreate: " + url1);

        final Request request = new Request.Builder().url(url1).get().build();

        Call call = client.newCall(request);
        Log.e(TAG, "getServerFrom: " + call);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: "+"onFailure" );
                // WeiboDialogUtils.closeDialog(dialog);
                WeiboDialogUtils.dismissprogress();


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // WeiboDialogUtils.closeDialog(dialog);
                Log.e(TAG, "onResponse: "+"onResponse" );

                Gson gson = new Gson();
                final NewsBean newsBean = gson.fromJson(response.body().string(), NewsBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        WeiboDialogUtils.dismissprogress();
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        data = newsBean.getData();
                        myAdapter = new MyAdapter(newsBean.getData(), MainActivity.this);
                        recyclerView.setAdapter(myAdapter);
                        // myAdapter.notifyDataSetChanged();

                    }
                });
            }
        });


    }
}
