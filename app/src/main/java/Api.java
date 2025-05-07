import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class Api {
    private OkHttpClient client;
    private String token;

    public Api(String token) {
        this.client = new OkHttpClient();
        this.token = token;
    }

    public String getJson(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + token)
                .addHeader("Accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Erreur HTTP : " + response.code());
            }

            return response.body().string();
        }
    }
}