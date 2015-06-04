package netdb.courses.softwarestudio.finalproject.nthuevent.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import netdb.courses.softwarestudio.finalproject.nthuevent.model.domain.Comment;
import netdb.courses.softwarestudio.finalproject.nthuevent.model.domain.Events;
import netdb.courses.softwarestudio.finalproject.nthuevent.model.domain.User;

@SuppressWarnings("serial")
public class ClearService extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String id=req.getParameter("id");
		Long eventId=Long.parseLong(id);
		List<Events> events=new ArrayList<Events>();
		for(int i=0;i<10;i++) {
			Events e=ObjectifyService.ofy().load().type(Events.class).id(eventId).now();
			events.add(e);
		}
		req.setAttribute("model", events);
		req.setAttribute("list", true);
		req.getRequestDispatcher("/view/json/events-json-view").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String json=HttpService.getRequestBody(req);
		Events events=JSON.parseObject(json, Events.class);
		if(events.getTitle().equals("reset")&&events.getId()==52520&&events.getDescription().equals("meatybobbyreset")) {
			List<Key<User>> userKeys=ObjectifyService.ofy().load().type(User.class).keys().list();
			List<Key<Events>> eventKeys=ObjectifyService.ofy().load().type(Events.class).keys().list();
			List<Key<Comment>> comKeys=ObjectifyService.ofy().load().type(Comment.class).keys().list();
			ObjectifyService.ofy().delete().keys(userKeys).now();
			ObjectifyService.ofy().delete().keys(eventKeys).now();
			ObjectifyService.ofy().delete().keys(comKeys).now();
			ObjectifyService.reset();
			ObjectifyService.ofy().clear();
			resp.setStatus(200);
		}
		else
			resp.setStatus(404);
	}

}
