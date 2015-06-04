package netdb.courses.softwarestudio.finalproject.nthuevent.model.domain;

import java.util.LinkedList;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class User {
	
	@Id
	private Long id;
	@Index
	private String fbId;
	private String fbAccessToken;
	private String name;
	private List<Key<Events>> hostEvent=new LinkedList<Key<Events>>();
	private List<Key<Events>> joinEvent=new LinkedList<Key<Events>>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id=id;
	}
	public String getFbId() {
		return fbId;
	}
	public void setFbId(String fbId) {
		this.fbId=fbId;
	}
	public String getFbAccessToken() {
		return fbAccessToken;
	}
	public void setFbAccessToken(String fbAccessToken) {
		this.fbAccessToken = fbAccessToken;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Key<Events>> getHostEvent() {
		return hostEvent;
	}
	public void setHostEvent(List<Key<Events>> hostEvent) {
		this.hostEvent = hostEvent;
	}
	public List<Key<Events>> getJoinEvent() {
		return joinEvent;
	}
	public void setJoinEvent(List<Key<Events>> joinEvent) {
		this.joinEvent = joinEvent;
	}
}
