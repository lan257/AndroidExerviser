package com.example.androidandweb.http;

// 创建URL对象
//            String vid="http://localhost:8080";//vid需要改成变量，app前端可以设置。
//            PackageURL packageURL=new PackageURL(vid);
//            URL apiUrl = new URL(packageURL.getUrl(url));

import android.os.AsyncTask;
import android.util.Log;

import com.example.androidandweb.O_solidObjects.simpleObjects.Result;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class postNoJwt {

    public interface OnResultListener {
        void onResult(Result result);
    }

    public static void sendPostRequest(String url, Object data, OnResultListener listener) {

            PackageHttp packageHttp=new PackageHttp(url,data);
            Log.i("测试",""+packageHttp);

        new PostTask(packageHttp.vid, packageHttp.data, listener).execute();
    }

    static class PostTask extends AsyncTask<Void, Void, Result> {
        private final String url;
        private final Object data;
        private final WeakReference<OnResultListener> listenerReference;

        PostTask(String url, Object data, OnResultListener listener) {
            this.url = url;
            this.data = data;
            this.listenerReference = new WeakReference<>(listener);
        }

        @Override
        protected Result doInBackground(Void... voids) {
            try {
                // 创建URL对象
                URL apiUrl = new URL(url);

                // 创建HttpURLConnection对象
                HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

                // 设置请求方法为POST
                connection.setRequestMethod("POST");

                // 启用输入输出流
                connection.setDoOutput(true);
                connection.setDoInput(true);

                // 设置请求头属性
                connection.setRequestProperty("Content-Type", "application/json");

                // 获取输出流并写入数据
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = data.toString().getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                // 获取响应码
                int responseCode = connection.getResponseCode();

                // 读取响应数据
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                        String inputLine;
                        StringBuilder response = new StringBuilder();

                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }

                        return new Gson().fromJson(response.toString(), Result.class);
                    }
                } else {
                    System.out.println("HTTP POST request failed. Response Code: " + responseCode);
                    String Forbidden= connection.getResponseMessage();
                    // 返回一个错误的Result，提供更多信息
                    return new Result(0, "HTTP POST request failed. Response Code: " + responseCode, "Forbidden: " + Forbidden);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Result result) {
            super.onPostExecute(result);

            OnResultListener listener = listenerReference.get();
            if (listener != null) {
                listener.onResult(result);
            }
        }
    }
}
