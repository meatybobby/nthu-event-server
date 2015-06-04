package netdb.courses.softwarestudio.finalproject.nthuevent.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import netdb.courses.softwarestudio.finalproject.nthuevent.model.domain.Events;
import netdb.courses.softwarestudio.finalproject.nthuevent.model.domain.User;
import netdb.courses.softwarestudio.finalproject.nthuevent.model.domain.UserLike;

public class RecommandService {
	static public void addUserLike(Long userId,Events event) {
		UserLike like=ObjectifyService.ofy().load().type(UserLike.class).id(userId).now();
		if(like==null) {
			like=new UserLike();
			like.setUserId(userId);
		}
		Map<String, Integer> map=like.getUserView();
		String tag1=event.getTag1(),tag2=event.getTag2();
		Integer num=map.get(tag1);
		if(num==null) map.put(tag1, 1);
		else map.put(tag1, num+1);
		num=map.get(tag2);
		if(num==null) map.put(tag2, 1);
		else map.put(tag2, num+1);
		ObjectifyService.ofy().save().entity(like);
	}
	static public List<Events> recommandEvents(Long userId) {
		List<Key<Events>> joinEventsKey=ObjectifyService.ofy().load().type(User.class).id(userId).now().getJoinEvent();
		Map<Long,Boolean> joinEventId=new HashMap<Long,Boolean>();
		for(Key<Events> k:joinEventsKey)
			joinEventId.put(k.getId(), true);
		List<Events> events=ObjectifyService.ofy().load().type(Events.class).filter("time >=", System.currentTimeMillis()).list();
		for(int i=0;i<events.size();i++) {
			if(joinEventId.containsKey(events.get(i).getId())) {
				events.remove(i);
				i--;
			}
		}
		UserLike like=ObjectifyService.ofy().load().type(UserLike.class).id(userId).now();
		Collections.sort(events, new EventComparator(like));
		return events;
	}
}
