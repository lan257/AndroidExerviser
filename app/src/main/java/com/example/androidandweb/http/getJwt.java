package com.example.androidandweb.http;

import android.os.AsyncTask;
import android.util.Log;

import com.example.androidandweb.O_solidObjects.simpleObjects.Result;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class getJwt {
    public interface OnResultListener {
        void onResult(Result result);
    }

    public static void sendGetRequest(String url,getJwt.OnResultListener listener) {

        PackageHttp packageHttp=new PackageHttp(url);
        Log.i("测试",""+packageHttp);

        new getJwt.GetTask(packageHttp.vid,packageHttp.jwt, listener).execute();
    }

    private static class GetTask extends AsyncTask<Void, Void, Result> {
        private final String url;
        private final String jwt;

        private final WeakReference<getJwt.OnResultListener> listenerReference;

        GetTask(String url, String jwt, getJwt.OnResultListener listener) {
            this.url = url;
            this.jwt=jwt;
            this.listenerReference = new WeakReference<>(listener);
        }

        @Override
        protected  Result doInBackground(Void... voids) {
            try {
                // 创建URL对象
                URL apiUrl = new URL(url);

                // 创建HttpURLConnection对象
                HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

                // 设置请求方法为GET
                connection.setRequestMethod("GET");


                // 启用输出流
                connection.setDoInput(true);

                // 设置请求头属性
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Authorization", jwt);


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
                        // 使用Gson解析response

                        return new Gson().fromJson(response.toString(), Result.class);
                    }
                }
                else {

                    System.out.println("HTTP GET request failed. Response Code: " + responseCode);
                    connection.disconnect();
                    // 返回一个错误的Result，提供更多信息
                    return new Result(0, "HTTP GET request failed. Response Code: " +"Forbidden: ", responseCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
                e.printStackTrace();
                // 返回一个错误的Result，提供更多信息
                return new Result(0, "Exception during HTTP GET request: " + e.getMessage(), null);
            }

        }

        @Override
        protected void onPostExecute(Result result) {
            super.onPostExecute(result);

            getJwt.OnResultListener listener = listenerReference.get();
            if (listener != null) {
                listener.onResult(result);
            }
        }
    }

}
