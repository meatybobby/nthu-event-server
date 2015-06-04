package netdb.courses.softwarestudio.finalproject.nthuevent.model.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import netdb.courses.softwarestudio.finalproject.nthuevent.model.domain.Events;
import netdb.courses.softwarestudio.finalproject.nthuevent.model.domain.User;

@SuppressWarnings("serial")
public class HostEventDao  extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Long userId=(Long)req.getAttribute("model");
		if(userId==null) {
			req.setAttribute("model", null);
			return;
		}
		User user=ObjectifyService.ofy().load().type(User.class).id(userId).now();
		if(user==null) {
			req.setAttribute("model", null);
			return;
		}
		List<Events> events=new ArrayList<Events>();
		for(Key<Events> k:user.getHostEvent()) {
			Events e=ObjectifyService.ofy().load().key(k).now();
			events.add(e);
		}
		req.setAttribute("model", events);
	}

}
