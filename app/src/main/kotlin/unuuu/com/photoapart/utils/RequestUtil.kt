package unuuu.com.photoapart.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Interceptor
import com.squareup.okhttp.OkHttpClient

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.client.OkClient
import retrofit.converter.GsonConverter;
import java.util.concurrent.TimeUnit

/**
 *
 */
public class RequestUtil {
    companion object {
        public fun getRestAdapter() : RestAdapter {
            val gson = GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create();

            val httpClient = OkHttpClient();
            httpClient.setConnectTimeout(100, TimeUnit.SECONDS);
            httpClient.setReadTimeout(100, TimeUnit.SECONDS);
            httpClient.setWriteTimeout(100, TimeUnit.SECONDS);

            val restAdapter = RestAdapter.Builder()
                    .setEndpoint("http://photoapart.herokuapp.com")
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setConverter(GsonConverter(gson))
                    .setLog(AndroidLog("PhotoApart"))
                    .setClient(OkClient(httpClient))
                    .build();

            return restAdapter;
        }

    }
}
