package com.example.androidandweb.http;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUploadTask extends AsyncTask<Void, Void, Void> {

    private final String serverUrl;
    private final String filePath;
    int x;

    String jwt;
    public FileUploadTask(String serverUrl, String filePath) {
        this.filePath = filePath;
        PackageHttp x=new PackageHttp(serverUrl,"");
        this.serverUrl=x.getVid();
       jwt =x.getJwt();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            URL url = new URL(serverUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 设置请求方法为POST
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            // 设置请求头信息
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + "*****");
            connection.setRequestProperty("Authorization", jwt);
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());

            // 写入文件数据
            outputStream.writeBytes("--*****\r\n");
            outputStream.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\"" + file.getName() + "\"\r\n");
            outputStream.writeBytes("\r\n");

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.writeBytes("\r\n");
            outputStream.writeBytes("--*****--\r\n");
            outputStream.flush();
            outputStream.close();
            fileInputStream.close();

            // 获取服务器的响应
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 文件上传成功
                Log.d("FileUpload", "File uploaded successfully");
                this.x=1;
            } else {
                this.x=0;
                // 文件上传失败
                String Forbidden= connection.getResponseMessage();
                Log.e("FileUpload", "File upload failed with error code " + responseCode+ "Forbidden: " + Forbidden);
                // 返回一个错误的Result，提供更多信息
                return null;

            }

        } catch (Exception e) {
            this.x=0;
            e.printStackTrace();
        }
        return null;
    }
}