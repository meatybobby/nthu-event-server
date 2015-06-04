package netdb.courses.softwarestudio.finalproject.nthuevent.model.domain;

import java.util.HashMap;
import java.util.Map;

import com.googlecode.objectify.annotation.EmbedMap;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class UserLike {
	@Id
	private Long userId;
	@EmbedMap
	private Map<String, Integer> userView=new HashMap<String, Integer>();
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Map<String, Integer> getUserView() {
		return userView;
	}
	public void setUserView(Map<String, Integer> userView) {
		this.userView = userView;
	}
}
