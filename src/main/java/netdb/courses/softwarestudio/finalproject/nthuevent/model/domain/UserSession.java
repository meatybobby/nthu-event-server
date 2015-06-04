package netdb.courses.softwarestudio.finalproject.nthuevent.model.domain;

public class UserSession {
	
	private String fbAccessToken;
	private Long userId;
	public String getFbAccessToken() {
		return fbAccessToken;
	}
	public void setFbAccessToken(String fbAccessToken) {
		this.fbAccessToken = fbAccessToken;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
