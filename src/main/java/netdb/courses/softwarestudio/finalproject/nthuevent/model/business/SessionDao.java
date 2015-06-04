package netdb.courses.softwarestudio.finalproject.nthuevent.model.business;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netdb.courses.softwarestudio.finalproject.nthuevent.model.domain.User;
import netdb.courses.softwarestudio.finalproject.nthuevent.model.domain.UserSession;
import netdb.courses.softwarestudio.finalproject.nthuevent.service.FacebookService;

import com.googlecode.objectify.ObjectifyService;

@SuppressWarnings("serial")
public class SessionDao extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		UserSession session=(UserSession)req.getAttribute("model");
		String fbId=FacebookService.getFacebookId(session.getFbAccessToken());
		List<User> result=ObjectifyService.ofy().load().type(User.class).filter("fbId", fbId).list();
		if(result.isEmpty()) {
			req.setAttribute("model", null);
			return;
		}
		User user=result.get(0);
		session.setUserId(user.getId());
		req.setAttribute("model", session);
	}

}
