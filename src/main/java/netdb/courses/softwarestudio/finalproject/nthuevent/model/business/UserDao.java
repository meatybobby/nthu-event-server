package netdb.courses.softwarestudio.finalproject.nthuevent.model.business;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netdb.courses.softwarestudio.finalproject.nthuevent.model.domain.User;
import netdb.courses.softwarestudio.finalproject.nthuevent.service.FacebookService;
import netdb.courses.softwarestudio.finalproject.nthuevent.service.FacebookUser;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

@SuppressWarnings("serial")
public class UserDao extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<User> users=ObjectifyService.ofy().load().type(User.class).list();
		req.setAttribute("model",users);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		User user=(User)req.getAttribute("model");
		User newUser=new User();
		FacebookUser fbUser=FacebookService.getFacebookUser(user.getFbAccessToken());
		if(fbUser==null) {
			req.setAttribute("model", null);
			return;
		}
		newUser.setFbId(fbUser.id);
		newUser.setName(fbUser.name);
		List<User> result=ObjectifyService.ofy().load().type(User.class).filter("fbId", newUser.getFbId()).list();
		if(!result.isEmpty()) {
			req.setAttribute("model", user);
			return;
		}
		Key<User> key=ObjectifyService.ofy().save().entity(newUser).now();
		newUser.setId(key.getId());
		req.setAttribute("model", newUser);
	}

}
