package netdb.courses.softwarestudio.finalproject.nthuevent.service;

import java.util.Comparator;
import java.util.Map;

import netdb.courses.softwarestudio.finalproject.nthuevent.model.domain.Events;
import netdb.courses.softwarestudio.finalproject.nthuevent.model.domain.UserLike;

public class EventComparator implements Comparator<Events> {
	private Map<String, Integer> map;
	
	public EventComparator(UserLike like) {
		this.map=like.getUserView();
	}

	@Override
	public int compare(Events e1, Events e2) {
		int com1=0,com2=0;
		Integer num;
		num=map.get(e1.getTag1());
		if(num!=null) com1+=num;
		num=map.get(e1.getTag2());
		if(num!=null) com1+=num;
		num=map.get(e2.getTag1());
		if(num!=null) com2+=num;
		num=map.get(e2.getTag2());
		if(num!=null) com2+=num;
		if(com1>com2) return -1;
		if(com1<com2) return 1;
		if(e1.getTime()>e2.getTime()) return 1;
		else return -1;
	}

}
