package com.tzw.noah.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tzw.noah.cache.UserCache;
import com.tzw.noah.logger.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;

/**
 * Created by yzy on 2017/6/12.
 */

public class HttpTool {
    private static long CONNECT_TIMEOUT = 10;

//    static MyHttpConnectionImpl mhc;
    static HttpTool instance;
    private OkHttpClient mOkHttpClient;

    public static HttpTool getInstance() {
        if (instance == null) {
            synchronized (HttpTool.class) {
                HttpTool.CONNECT_TIMEOUT = UserCache.getTimeOut();
                instance = new HttpTool();
//                mhc = MyHttpConnectionImpl.getInstance();
            }
        }
        return instance;
    }

    HttpTool() {
        mOkHttpClient = new OkHttpClient().newBuilder().connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS).build();
    }

    public void setTimeout(int timeout) {
        mOkHttpClient = new OkHttpClient().newBuilder().connectTimeout(timeout, TimeUnit.SECONDS).build();
    }

    public void createNewClient() {
        mOkHttpClient = new OkHttpClient();
    }

//    public static String Get(String url) {
//        return mhc.Get("", url);
//    }

    //HttpGet异步请求
    public void HttpGet(String url, final Callback callback) {
        Request.Builder builder = new Request.Builder();
        final Request request = builder
                .url(url)
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onFailure(call, e);
                    Log.httpcall(request, e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callback != null) {
                    IMsg imsg = null;
                    String ret = "";
                    try {
                        ret = response.body().string();
                        Log.httpcall(request, ret);
                        imsg = new IMsg();
                        imsg.setSucceed(true);
                        imsg.Data = ret;
                    } catch (Exception e) {
                        imsg = CreateErrorMsgResponse(e.getMessage());
                        Log.httpcall(request, e);
                    }
                    callback.onResponse(imsg);
                }
            }
        });
    }

//    public static String Post(String url, Param params) {
//        try {
//            return mhc.post(url, params).body().toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "";
//        }
//    }


    //HttpGet同步请求
    public IMsg HttpGet(String url, Param[] headers) {

        Request.Builder builder = new Request.Builder();
        for (Param param : headers) {
            builder.header(param.key, (String) param.value);
        }
        final Request request = builder
                .url(url)
                .build();

        Call call = mOkHttpClient.newCall(request);
        Response execute = null;
        try {
            execute = call.execute();
            String ret = execute.body().string();
            Log.httpcall(request, ret);
            IMsg imsg = null;
            if (execute.code() == 200)
                imsg = IMsg.getInstance(ret);
            else
                imsg = CreateErrorMsgResponse("服务器返回错误:" + execute.code());
            return imsg;
        } catch (Exception e) {
            Log.httpcall(request, e);
            return CreateErrorMsgResponse(e.getMessage());
        }
    }

    //HttpGet异步请求
    public void HttpGet(String url, Param[] headers, final Callback callback) {
        Request.Builder builder = new Request.Builder();
        for (Param param : headers) {
            builder.header(param.key, (String) param.value);
        }
        final Request request = builder
                .url(url)
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onFailure(call, e);
                    Log.httpcall(request, e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callback != null) {
                    IMsg imsg = null;
                    String ret = "";
                    try {
                        ret = response.body().string();
                        Log.httpcall(request, ret);
                        if (response.code() == 200)
                            imsg = IMsg.getInstance(ret);
                        else
                            imsg = CreateErrorMsgResponse("服务器返回错误:" + response.code());
                    } catch (Exception e) {
                        imsg = CreateErrorMsgResponse(e.getMessage());
                        Log.httpcall(request, e);
                    }
                    callback.onResponse(imsg);
                }
            }
        });
    }

    //HttpGet同步请求
    public IMsg HttpDelete(String url, Param[] headers) {

        Request.Builder builder = new Request.Builder();
        for (Param param : headers) {
            builder.header(param.key, (String) param.value);
        }
        final Request request = builder
                .url(url)
                .delete()
                .build();

        Call call = mOkHttpClient.newCall(request);
        Response execute = null;
        try {
            execute = call.execute();
            String ret = execute.body().string();
            Log.httpcall(request, ret);
            IMsg imsg = null;
            if (execute.code() == 200)
                imsg = IMsg.getInstance(ret);
            else
                imsg = CreateErrorMsgResponse("服务器返回错误:" + execute.code());
            return imsg;
        } catch (Exception e) {
            Log.httpcall(request, e);
            return CreateErrorMsgResponse(e.getMessage());
        }
    }

    //HttpDelete异步请求
    public void HttpDelete(String url, Param[] headers, final Callback callback) {
        Request.Builder builder = new Request.Builder();
        for (Param param : headers) {
            builder.header(param.key, (String) param.value);
        }
        final Request request = builder
                .url(url)
                .delete()
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onFailure(call, e);
                    Log.httpcall(request, e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callback != null) {
                    IMsg imsg = null;
                    String ret = "";
                    try {
                        ret = response.body().string();
                        Log.httpcall(request, ret);
                        if (response.code() == 200)
                            imsg = IMsg.getInstance(ret);
                        else
                            imsg = CreateErrorMsgResponse("服务器返回错误:" + response.code());
                    } catch (Exception e) {
                        imsg = CreateErrorMsgResponse(e.getMessage());
                        Log.httpcall(request, e);
                    }
                    callback.onResponse(imsg);
                }
            }
        });
    }

    public IMsg CreateErrorMsgResponse(String ex) {
        IMsg imsg = new IMsg();
        imsg.setSucceed(false);
        imsg.setMsg(ex);
        return imsg;
    }

    private static final MediaType MEDIA_TYPE_IMAGE = MediaType.parse("image/*");

    //HttpPOST异步请求
    public void HttpPost(String url, Param[] headers, final String json, final Callback callback) {

        Request.Builder builder = new Request.Builder();
        MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = null;

        if (json != null && !json.equals("")) {
            requestBody = RequestBody.create(MEDIA_TYPE_JSON, json);
            builder.post(requestBody);
        } else {
            builder.post(Util.EMPTY_REQUEST);
        }

        for (Param param : headers) {
            builder.header(param.key, (String) param.value);
        }
        builder.header("Content-Type", "application/json;charset=utf-8");
        final Request request = builder
                .url(url)
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onFailure(call, e);
                    Log.httpcall(request, e, json);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callback != null) {
                    IMsg imsg = null;
                    try {
                        String ret = response.body().string();
                        Log.httpcall(request, ret, json);
                        if (response.code() == 200)
                            imsg = IMsg.getInstance(ret);
                        else
                            imsg = CreateErrorMsgResponse("服务器返回错误:" + response.code());

                    } catch (Exception e) {
                        imsg = CreateErrorMsgResponse(e.getMessage());
                        Log.httpcall(request, e, json);
                    }
                    callback.onResponse(imsg);
                }
            }
        });
    }

    //HttpPOST同步请求
    public IMsg HttpPost(String url, Param[] headers, String json) {
        Request.Builder builder = new Request.Builder();

        MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = null;
        if (json != null && !json.equals("")) {
            requestBody = RequestBody.create(MEDIA_TYPE_JSON, json);
            builder.post(requestBody);
        } else {
            builder.post(Util.EMPTY_REQUEST);
        }
        for (Param param : headers) {
            builder.header(param.key, (String) param.value);
        }
        builder.header("Content-Type", "application/json;charset=utf-8");

        final Request request = builder
                .url(url)
                .build();

        Call call = mOkHttpClient.newCall(request);

        Response execute = null;
        try {
            execute = call.execute();
            String ret = execute.body().string();

            Log.httpcall(request, ret, json);
            IMsg imsg = null;
            if (execute.code() == 200)
                imsg = IMsg.getInstance(ret);
            else
                imsg = CreateErrorMsgResponse("服务器返回错误:" + execute.code());
            return imsg;
        } catch (Exception e) {

            Log.httpcall(request, e, json);
            return CreateErrorMsgResponse(e.getMessage());
        }
    }


    //HttpPut异步请求
    public void HttpPut(String url, Param[] headers, final String json, final Callback callback) {

        Request.Builder builder = new Request.Builder();
        MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = null;
        if (json != null && !json.equals("")) {
            requestBody = RequestBody.create(MEDIA_TYPE_JSON, json);
            builder.put(requestBody);
        } else {
            builder.put(Util.EMPTY_REQUEST);
        }

        for (Param param : headers) {
            builder.header(param.key, (String) param.value);
        }
        builder.header("Content-Type", "application/json;charset=utf-8");
        final Request request = builder
                .url(url)
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onFailure(call, e);
                    Log.httpcall(request, e, json);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callback != null) {
                    IMsg imsg = null;
                    try {
                        String ret = response.body().string();
                        Log.httpcall(request, ret, json);
                        if (response.code() == 200)
                            imsg = IMsg.getInstance(ret);
                        else
                            imsg = CreateErrorMsgResponse("服务器返回错误:" + response.code());
                    } catch (Exception e) {
                        imsg = CreateErrorMsgResponse(e.getMessage());
                        Log.httpcall(request, e, json);
                    }
                    callback.onResponse(imsg);
                }
            }
        });
    }

    //HttpPUT同步请求
    public IMsg HttpPut(String url, Param[] headers, String json) {
        Request.Builder builder = new Request.Builder();

        MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = null;
        if (json != null && !json.equals("")) {
            requestBody = RequestBody.create(MEDIA_TYPE_JSON, json);
            builder.put(requestBody);
        } else {
            builder.put(Util.EMPTY_REQUEST);
        }
        for (Param param : headers) {
            builder.header(param.key, (String) param.value);
        }
        builder.header("Content-Type", "application/json;charset=utf-8");

        final Request request = builder
                .url(url)
                .build();

        Call call = mOkHttpClient.newCall(request);

        Response execute = null;
        try {
            execute = call.execute();
            String ret = execute.body().string();

            Log.httpcall(request, ret, json);
            IMsg imsg = null;
            if (execute.code() == 200)
                imsg = IMsg.getInstance(ret);
            else
                imsg = CreateErrorMsgResponse("服务器返回错误:" + execute.code());
            return imsg;
        } catch (Exception e) {

            Log.httpcall(request, e, json);
            return CreateErrorMsgResponse(e.getMessage());
        }
    }


    //HttpPOST异步请求
    public void HttpPostFile(String url, Param[] headers, final List<Param> body, Map<String, File> fileBody, final Callback callback) {

        Request.Builder builder = new Request.Builder();
        MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = null;

        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (fileBody != null && fileBody.size() > 0) {
            for (Map.Entry<String, File> entry : fileBody.entrySet()) {
                if (entry.getValue() != null) {
                    if (entry.getValue() instanceof File) {
                        File f = (File) entry.getValue();
                        multipartBodyBuilder.addFormDataPart(entry.getKey(), f.getName(),
                                RequestBody.create(MEDIA_TYPE_IMAGE, f));
                    }
                }
            }
        }
        String json = "";
        if (body != null && body.size() > 0) {
            for (Param param : body) {
                multipartBodyBuilder.addFormDataPart(param.key, param.value.toString());
            }
            Gson gson = new GsonBuilder().create();
            json = gson.toJson(body);
        }
        requestBody = multipartBodyBuilder.build();
        builder.post(requestBody);

        for (Param param : headers) {
            builder.header(param.key, (String) param.value);
        }
        builder.header("Content-Type", "application/json;charset=utf-8");
        final Request request = builder
                .url(url)
                .build();

        Call call = mOkHttpClient.newCall(request);
        final String finalJson = json;
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onFailure(call, e);
                    Log.httpcall(request, e, finalJson);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callback != null) {
                    IMsg imsg = null;
                    try {
                        String ret = response.body().string();
                        Log.httpcall(request, ret, finalJson);
                        if (response.code() == 200)
                            imsg = IMsg.getInstance(ret);
                        else
                            imsg = CreateErrorMsgResponse("服务器返回错误:" + response.code());

                    } catch (Exception e) {
                        imsg = CreateErrorMsgResponse(e.getMessage());
                        Log.httpcall(request, e, finalJson);
                    }
                    callback.onResponse(imsg);
                }
            }
        });
    }

}
