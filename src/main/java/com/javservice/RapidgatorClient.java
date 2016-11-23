package com.javservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;

/**
 * Created by Administrator on 11/15/2016.
 */
public class RapidgatorClient {
  OkHttpClient client = new OkHttpClient();
  ObjectMapper objectMapper = new ObjectMapper();

  public SessionResponse getSessionId(String username,String password) throws IOException {
    Response response = null;
    MediaType mediaType = MediaType.parse("multipart/form-data; boundary=---011000010111000001101001");

    RequestBody formBody = new FormBody.Builder()
        .add("username", username).add("password",password)
        .build();
    Request request = new Request.Builder()
        .url("http://rapidgator.net/api/user/login")
        .post(formBody)
        .addHeader("content-type", "multipart/form-data; boundary=---011000010111000001101001")
        .addHeader("cache-control", "no-cache")
        .addHeader("postman-token", "755a1e04-b742-3c9e-530e-2a0dc4a8d78e")
        .build();

    response = client.newCall(request).execute();
    SessionResponse sessionResponse = objectMapper.readValue(response.body().string(),SessionResponse.class);
    return sessionResponse;
  }

  public DownloadResponse getLinkDownload(String sessionId,String link) throws IOException {
    Request request = new Request.Builder()
        .url("http://rapidgator.net/api/file/download?sid="+sessionId+"&url="+link)
        .addHeader("content-type", "multipart/form-data; boundary=---011000010111000001101001")
        .addHeader("cache-control", "no-cache")
        .addHeader("postman-token", "b5cfe31a-0d8c-ca61-b7c1-a9debc7613c0")
        .build();
    Response response = client.newCall(request).execute();
    DownloadResponse downloadResponse = objectMapper.readValue(response.body().string(),DownloadResponse.class);
    return downloadResponse;
  }
}
