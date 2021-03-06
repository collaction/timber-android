package hk.collaction.timber.ui.tree;

/**
 * Created by himphen on 24/5/16.
 */

public class TreeInfoItem {
	private String titleText;
	private String contentText;

	public TreeInfoItem() {
	}

	public TreeInfoItem(String titleText, String contentText) {
		this.titleText = titleText;
		this.contentText = contentText;
	}

	public void setTitleText(String titleText) {
		this.titleText = titleText;
	}

	public void setContentText(String contentText) {
		this.contentText = contentText;
	}

	public String getTitleText() {
		return titleText;
	}

	public String getContentText() {
		return contentText;
	}
}