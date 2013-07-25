package com.drtshock.willie.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WebHelper {

    public static String readURLToString(URL url) throws IOException {
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setConnectTimeout(10000);
        http.setReadTimeout(10000);
        http.connect();
        return http.getErrorStream() == null ? convertStreamToString((InputStream) url.getContent()) : null;
    }

    public static String convertStreamToString(InputStream stream) {
        try {
            return new Scanner(stream).useDelimiter("\\A").next();
        } catch (java.util.NoSuchElementException e) {
            return "";
        }
    }

    public static String shortenURL(String url) {
        try {
            String str = readURLToString(new URL("http://is.gd/create.php?format=simple&url=" + url));
            return str;
        } catch (IOException e) {
            return "";
        }
    }

}
