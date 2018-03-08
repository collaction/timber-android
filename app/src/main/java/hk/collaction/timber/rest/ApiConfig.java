package hk.collaction.timber.rest;

import hk.collaction.timber.BuildConfig;

public enum ApiConfig {

	// DEVELOPMENT
	BASE_URL(BuildConfig.API_BASE_URL, true);
	public static final String SERVER_CONNECTION = "D";

	private final String gateway;
	private final boolean isDev;

	ApiConfig(String gateway, boolean isDev) {
		this.gateway = gateway;
		this.isDev = isDev;
	}

	public String getGateway() {
		return gateway;
	}

	public boolean isDev() {
		return isDev;
	}
}
