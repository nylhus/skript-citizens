package ca.nylhus.skriptcitizens.util;

import com.google.common.io.CharStreams;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.citizensnpcs.api.util.Messaging;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MojangSkinGenerator {

    public static JsonObject generateFromURL(final String url, boolean slim)
            throws InterruptedException, ExecutionException {
        return EXECUTOR.submit(() -> {
            DataOutputStream out = null;
            InputStreamReader reader = null;
            try {
                URL target = new URL("https://api.mineskin.org/generate/url");
                HttpURLConnection con = (HttpURLConnection) target.openConnection();
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                con.setRequestProperty("User-Agent", "Citizens/2.0");
                con.setRequestProperty("Cache-Control", "no-cache");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestProperty("Content-Type", "application/json");
                con.setConnectTimeout(1000);
                con.setReadTimeout(30000);
                out = new DataOutputStream(con.getOutputStream());
                JsonObject req = new JsonObject();
                req.addProperty("url", url);
                req.addProperty("name", "");
                if (slim) {
                    req.addProperty("variant", "slim");
                }
                out.writeBytes(req.toString().replace("\\", ""));
                out.close();
                reader = new InputStreamReader(con.getInputStream());
                String str = CharStreams.toString(reader);
                if (Messaging.isDebugging()) {
                    Messaging.debug(str);
                }
                if (con.getResponseCode() != 200)
                    return null;
                JsonObject output = new JsonParser().parse(str).getAsJsonObject();
                JsonObject data = output.get("data").getAsJsonObject();
                con.disconnect();
                return data;
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                    }
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                    }
                }
            }
        }).get();
    }

    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();
}
