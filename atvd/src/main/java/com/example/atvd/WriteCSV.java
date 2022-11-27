package com.example.atvd;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class WriteCSV {

    private static final String USER_AGENT = "Mozilla/5.0";

    private static final String POST_URL = "https://pastebin.com/api/api_post.php";

    private static String POST_PARAMS = "userName=Ramesh&password=Pass@123";
    private  static final String DEV_KEY = "sTcplD3cmf85Gmcw3MN7MeD2QjCgEAwt";

    public static String run(String path, String postParams) throws IOException {
        //POST_URL = path;
        return sendPOST(postParams);
    }

    private static String sendPOST(String content) throws IOException {

        try {
            URL url = new URL(POST_URL);
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection)con;
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.setDoInput(true);
            Map<String,String> arguments = new HashMap<>();
            arguments.put("api_dev_key", DEV_KEY);
            arguments.put("api_option", "paste");
            arguments.put("api_paste_code", content);
            // Repeat this for for all required API arguments
            // ...

            StringJoiner sj = new StringJoiner("&");
            for(Map.Entry<String,String> entry : arguments.entrySet())
                sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                        + URLEncoder.encode(entry.getValue(), "UTF-8"));
            byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
            int length = out.length;
            //http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            http.connect();
            OutputStream os = http.getOutputStream();
            os.write(out);
            InputStream is = http.getInputStream();
            String response = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
            return response;
        } catch (IOException urlException) {
            urlException.printStackTrace();
        }
        return null;
    }
}
