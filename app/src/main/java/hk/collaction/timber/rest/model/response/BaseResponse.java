package hk.collaction.timber.rest.model.response;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {

	@SerializedName("code")
	public int code;

	@SerializedName("message")
	public String message;

}
