package hk.collaction.timber.rest.service;

import hk.collaction.timber.rest.ApiConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
	private static final ApiConfig API_CONFIG = ApiConfig.BASE_URL;

	private final BaseApi restClient;

	private static String ROOT = API_CONFIG.getGateway();

	public ApiClient() {
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

		OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient().newBuilder();

		if (ApiConfig.BASE_URL.isDev()) {
			okHttpClientBuilder.addInterceptor(interceptor);
		}

		OkHttpClient okHttpClient = okHttpClientBuilder.build();

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(ROOT + "/")
				.addConverterFactory(GsonConverterFactory.create())
				.client(okHttpClient)
				.build();

		restClient = retrofit.create(BaseApi.class);
	}

	public BaseApi getApiClient() {
		return restClient;
	}
}
