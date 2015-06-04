package netdb.courses.softwarestudio.finalproject.nthuevent.model.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netdb.courses.softwarestudio.finalproject.nthuevent.model.domain.Events;
import netdb.courses.softwarestudio.finalproject.nthuevent.model.domain.User;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

@SuppressWarnings("serial")
public class JoinEventDao extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Long userId=(Long)req.getAttribute("model");
		User user=ObjectifyService.ofy().load().type(User.class).id(userId).now();
		if(user==null) {
			req.setAttribute("model", null);
			return;
		}
		List<Events> events=new ArrayList<Events>();
		boolean change=false;
		for(Key<Events> key:user.getJoinEvent()) {
			Events e=ObjectifyService.ofy().load().key(key).now();
			if(e==null) {
				user.getJoinEvent().remove(key);
				change=true;
			}
			else events.add(e);
		}
		if(change)
			ObjectifyService.ofy().save().entity(user);
		req.setAttribute("model", events);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Events event=(Events)req.getAttribute("model");
		Key<User> userKey=Key.create(User.class,event.getPosterId());
		Events join=ObjectifyService.ofy().load().type(Events.class).id(event.getId()).now();
		if(join==null||join.getJoinUser().contains(userKey)) {
			req.setAttribute("model", null);
			return;
		}
		req.setAttribute("model", join);
		join.getJoinUser().add(userKey);
		join.setJoinNum(join.getJoinNum() + 1);
		Key<Events> eventKey=ObjectifyService.ofy().save().entity(join).now();
		User user=ObjectifyService.ofy().load().key(userKey).now();
		user.getJoinEvent().add(eventKey);
		ObjectifyService.ofy().save().entity(user).now();
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Events event=(Events)req.getAttribute("model");
		Key<User> userKey=Key.create(User.class,event.getPosterId());
		Events join=ObjectifyService.ofy().load().type(Events.class).id(event.getId()).now();
		int index;
		if(join==null||(index=join.getJoinUser().indexOf(userKey))==-1) {
			req.setAttribute("model", null);
			return;
		}
		req.setAttribute("model", join);
		join.getJoinUser().remove(index);
		join.setJoinNum(join.getJoinNum()-1);
		Key<Events> eventKey=ObjectifyService.ofy().save().entity(join).now();
		User user=ObjectifyService.ofy().load().key(userKey).now();
		user.getJoinEvent().remove(eventKey);
		ObjectifyService.ofy().save().entity(user).now();
	}
}
