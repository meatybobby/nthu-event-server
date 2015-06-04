package netdb.courses.softwarestudio.finalproject.nthuevent.service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import netdb.courses.softwarestudio.finalproject.nthuevent.model.domain.Comment;
import netdb.courses.softwarestudio.finalproject.nthuevent.model.domain.Events;
import netdb.courses.softwarestudio.finalproject.nthuevent.model.domain.User;
import netdb.courses.softwarestudio.finalproject.nthuevent.model.domain.UserLike;

import com.googlecode.objectify.ObjectifyService;

public class ObjectifyInitailizer implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ObjectifyService.register(Events.class);
		ObjectifyService.register(User.class);
		ObjectifyService.register(Comment.class);
		ObjectifyService.register(UserLike.class);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
