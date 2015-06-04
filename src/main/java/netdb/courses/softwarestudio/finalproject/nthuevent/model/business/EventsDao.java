package netdb.courses.softwarestudio.finalproject.nthuevent.model.business;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import netdb.courses.softwarestudio.finalproject.nthuevent.model.domain.Events;
import netdb.courses.softwarestudio.finalproject.nthuevent.model.domain.User;
import netdb.courses.softwarestudio.finalproject.nthuevent.service.HttpService;
import netdb.courses.softwarestudio.finalproject.nthuevent.service.RecommandService;

@SuppressWarnings("serial")
public class EventsDao extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Long id=(Long)req.getAttribute("model");
		if(id==null) {
			String sort=req.getParameter("SortType");
			List<Events> events;
			Query<Events> query;
			if(sort==null) {
				query=ObjectifyService.ofy().load().type(Events.class).order("-timeStamp");
			}
			else if(sort.equals("recommend")) {
				Long userId=HttpService.getUserId(req);
				if(userId!=null) {
					events=RecommandService.recommandEvents(userId);
					req.setAttribute("model", events);
					return;
				}
				query=ObjectifyService.ofy().load().type(Events.class).order("-timeStamp");
			}
			else if(sort.equals("latest")) {
				query=ObjectifyService.ofy().load().type(Events.class).order("-timeStamp");
			}
			else if(sort.equals("nearest")) {
				long currentTime=System.currentTimeMillis();
				query=ObjectifyService.ofy().load().type(Events.class).filter("time >=", currentTime).order("time");
			}
			else if(sort.equals("hottest")) {
				query=ObjectifyService.ofy().load().type(Events.class).order("-joinNum");
			}
			else {
				query=ObjectifyService.ofy().load().type(Events.class).order("-timeStamp");
			}
			String tag1=req.getParameter("tag1");
			String tag2=req.getParameter("tag2");
			if(tag1!=null) {
				tag1=URLDecoder.decode(tag1,StandardCharsets.UTF_8.name());
				query=query.filter("tag1", tag1);
			}
			if(tag2!=null) {
				tag2=URLDecoder.decode(tag2,StandardCharsets.UTF_8.name());
				query=query.filter("tag2", tag2);
			}
			events=query.list();
			req.setAttribute("model",events);
		}
		else {
			Events event=ObjectifyService.ofy().load().type(Events.class).id(id).now();
			Long userId=HttpService.getUserId(req);
			if(userId!=null) {
				RecommandService.addUserLike(userId, event);
			}
			req.setAttribute("model", event);
			List<User> users=new ArrayList<User>();
			for(Key<User> k:event.getJoinUser()) {
				User u=ObjectifyService.ofy().load().key(k).now();
				u.setFbId(null);
				u.setHostEvent(null);
				u.setJoinEvent(null);
				users.add(u);
			}
			req.setAttribute("userlist", users);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Events event=(Events)req.getAttribute("model");
		event.setTimeStamp(System.currentTimeMillis());
		User user=ObjectifyService.ofy().load().type(User.class).id(event.getPosterId()).now();
		event.setPosterName(user.getName());
		Key<Events> key=ObjectifyService.ofy().save().entity(event).now();
		user.getHostEvent().add(key);
		ObjectifyService.ofy().save().entity(user).now();
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Events event=(Events)req.getAttribute("model");
		Key<Events> eventKey=Key.create(Events.class, event.getId());
		User user=ObjectifyService.ofy().load().type(User.class).id(event.getPosterId()).now();
		user.getHostEvent().remove(eventKey);
		ObjectifyService.ofy().save().entity(user);
		ObjectifyService.ofy().delete().key(eventKey);
	}
		

}
