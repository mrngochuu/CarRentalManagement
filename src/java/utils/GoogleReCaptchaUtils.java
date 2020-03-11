/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author ngochuu
 */
public class GoogleReCaptchaUtils implements Serializable {

    private static final String SITE_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
    private static final String SECRET_KEY = "6LcEWOAUAAAAADXg1N3eNeWjSpoHhDJcdpTQylCS";

    public static boolean verify(String googleReCatcha) throws MalformedURLException, IOException {
        if (googleReCatcha == null || googleReCatcha.length() == 0) {
            return false;
        }
        URL verifyUrl = new URL(SITE_VERIFY_URL);
        HttpsURLConnection conn = (HttpsURLConnection) verifyUrl.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        String postParams = "secret=" + SECRET_KEY + "&response=" + googleReCatcha;
        conn.setDoOutput(true);
        OutputStream outStream = conn.getOutputStream();
        outStream.write(postParams.getBytes());
        outStream.flush();
        outStream.close();
        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            InputStream is = conn.getInputStream();
            JsonReader jsonReader = Json.createReader(is);
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();
            return jsonObject.getBoolean("success");
        } else {
            return false;
        }
    }
}
