package netdb.courses.softwarestudio.finalproject.nthuevent.view.json;

import java.util.List;

import netdb.courses.softwarestudio.finalproject.nthuevent.model.domain.Events;
import netdb.courses.softwarestudio.finalproject.nthuevent.model.domain.User;

public class EventJson {
	
	public Long id;
	public String title;
	public String description;
	public Long posterId;
	public String posterName;
	public String location;
	public String tag1;
	public String tag2;
	public long time;
	public long timeStamp;
	public List<User> joinUser;
	public int joinNum=0;
	
	public EventJson(Events e) {
		id=e.getId();
		time=e.getTime();
		title=e.getTitle();
		tag1=e.getTag1();
		tag2=e.getTag2();
		joinNum=e.getJoinNum();
	}
	
	public EventJson(Events e,List<User> users) {
		id=e.getId();
		time=e.getTime();
		title=e.getTitle();
		tag1=e.getTag1();
		tag2=e.getTag2();
		joinNum=e.getJoinNum();
		posterId=e.getPosterId();
		posterName=e.getPosterName();
		location=e.getLocation();
		time=e.getTime();
		timeStamp=e.getTimeStamp();
		this.joinUser=users;
		joinNum=e.getJoinNum();
	}

}
