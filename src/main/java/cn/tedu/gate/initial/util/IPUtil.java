package cn.tedu.gate.initial.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class IPUtil{

    private static final String TENCENT_MAP_API_KEY = "JOCBZ-ESBKJ-B7VFV-DYB6E-TQAB6-RTFZ7"; //替换你们自己的key
    private static final String TENCENT_MAP_IP_API_URL = "https://apis.map.qq.com/ws/location/v1/ip";

    public String ipSearch(String ip) {
        String url = TENCENT_MAP_IP_API_URL + "?key=" + TENCENT_MAP_API_KEY + "&ip=" + ip;
        try {
            String response = sendGetRequest(url);
            return response;
        } catch (IOException e) {
            System.err.println("解析 IP 地址时出错：" + e.getMessage());
        }
        return null;
    }

    /**
     * 发送ip定位请求
     * @param apiUrl
     * @return
     * @throws IOException
     */
    private static String sendGetRequest(String apiUrl) throws IOException {
        StringBuilder responseBuilder = new StringBuilder();

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            responseBuilder.append(line);
        }
        reader.close();

        return responseBuilder.toString();
    }


}