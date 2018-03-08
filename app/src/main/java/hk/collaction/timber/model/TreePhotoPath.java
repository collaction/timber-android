package hk.collaction.timber.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Himphen on 23/8/2017.
 */

public class TreePhotoPath {
	@SerializedName("filename")
	private String filename;

	@SerializedName("original")
	private String original;

	public String getFilename() {
		return filename;
	}

	public String getOriginal() {
		return original;
	}
}
