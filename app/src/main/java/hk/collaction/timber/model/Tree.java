package hk.collaction.timber.model;

import com.google.gson.annotations.SerializedName;

public class Tree {

	@SerializedName("id")
	private Integer id;

	@SerializedName("ref_no")
	private String refNo;

	@SerializedName("ovt_no")
	private String ovtNo;

	@SerializedName("code")
	private String code;

	@SerializedName("name")
	private String name;

	@SerializedName("address")
	private String address;

	@SerializedName("lat")
	private Double lat;

	@SerializedName("lng")
	private Double lng;

	@SerializedName("district")
	private String district;

	@SerializedName("condition")
	private String condition;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("photo_path")
	private TreePhotoPath photoPath;

//	@SerializedName("like_count")
//	private Integer likeCount;
//
//	@SerializedName("dislike_count")
//	private Integer dislikeCount;


	public Integer getId() {
		return id;
	}

	public String getRefNo() {
		return refNo;
	}

	public String getOvtNo() {
		return ovtNo;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public Double getLat() {
		return lat;
	}

	public Double getLng() {
		return lng;
	}

	public String getDistrict() {
		return district;
	}

	public String getCondition() {
		return condition;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public TreePhotoPath getPhotoPath() {
		return photoPath;
	}

	@Override
	public String toString() {
		return "Tree{" +
				"id=" + id +
				", refNo='" + refNo + '\'' +
				'}';
	}

	public String getCode() {
		return code;
	}
}
