package netdb.courses.softwarestudio.finalproject.nthuevent.model.domain;

import java.util.LinkedList;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Events {
	
	@Id
	private Long id;
	private String title;
	private String description;
	private Long posterId;
	private String posterName;
	private String location;
	@Index
	private String tag1;
	@Index
	private String tag2;
	@Index
	private long time;
	@Index
	private long timeStamp;
	private List<Key<User>> joinUser=new LinkedList<Key<User>>();
	@Index
	private int joinNum=0;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id=id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getPosterId() {
		return posterId;
	}
	public void setPosterId(Long posterId) {
		this.posterId = posterId;
	}
	public String getPosterName() {
		return posterName;
	}
	public void setPosterName(String posterName) {
		this.posterName = posterName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getTag1() {
		return tag1;
	}
	public void setTag1(String tag1) {
		this.tag1 = tag1;
	}
	public String getTag2() {
		return tag2;
	}
	public void setTag2(String tag2) {
		this.tag2 = tag2;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time=time;
	}
	public Long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Long timeStamp) {
		this.timeStamp=timeStamp;
	}
	public List<Key<User>> getJoinUser() {
		return joinUser;
	}
	public void setJoinUser(List<Key<User>> joinUser) {
		this.joinUser = joinUser;
	}
	public int getJoinNum() {
		return joinNum;
	}
	public void setJoinNum(int joinNum) {
		this.joinNum = joinNum;
	}
}
