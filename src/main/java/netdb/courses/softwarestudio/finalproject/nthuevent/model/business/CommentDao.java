package netdb.courses.softwarestudio.finalproject.nthuevent.model.business;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netdb.courses.softwarestudio.finalproject.nthuevent.model.domain.Comment;
import netdb.courses.softwarestudio.finalproject.nthuevent.model.domain.User;

import com.googlecode.objectify.ObjectifyService;

@SuppressWarnings("serial")
public class CommentDao extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Long eventId=(Long)req.getAttribute("model");
		if(eventId==null) {
			req.setAttribute("model", null);
			return;
		}
		List<Comment> comments=ObjectifyService.ofy().load().type(Comment.class).filter("eventId", eventId).order("timeStamp").list();
		req.setAttribute("model", comments);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Comment comment=(Comment)req.getAttribute("model");
		User user=ObjectifyService.ofy().load().type(User.class).id(comment.getUserId()).now();
		comment.setUserName(user.getName());
		comment.setTimeStamp(System.currentTimeMillis());
		ObjectifyService.ofy().save().entity(comment);
	}

}
