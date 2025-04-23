package api;


//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//import okhttp3.OkHttpClient;
//import okhttp3.logging.HttpLoggingInterceptor;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class RetrofitClient {
//    private static Retrofit retrofit;
//    private static final String BASE_URL = "http://192.168.1.2/lutayanrhu/api/";
//
//    public static Retrofit getRetrofitInstance() {
//
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//                .build();
//
//        if (retrofit == null) {
//            Gson gson = new GsonBuilder()
//                    .setLenient()
//                    .create();
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .build();
//        }
//        return retrofit;
//    }
//}
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    // Singleton instance
    private static Retrofit retrofit = null;

    // Default Base URL
    private static final String BASE_URL = "http://192.168.100.46/lutayanrhu/api/";

    // Method to get a Retrofit instance
    public static synchronized Retrofit getRetrofitInstance() {

        if (retrofit == null) {
            // Configure logging interceptor for debugging API requests and responses
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Build OkHttpClient with logging interceptor
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();

            // Configure Gson for lenient parsing
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            // Initialize Retrofit
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }

    // Optional: Method to create a Retrofit instance with a custom base URL
    public static synchronized Retrofit getRetrofitInstance(String customBaseUrl) {

        if (retrofit == null || !retrofit.baseUrl().toString().equals(customBaseUrl)) {
            // Configure logging interceptor
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Build OkHttpClient
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();

            // Configure Gson
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            // Initialize Retrofit with the custom base URL
            retrofit = new Retrofit.Builder()
                    .baseUrl(customBaseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }
}
