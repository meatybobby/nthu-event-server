package netdb.courses.softwarestudio.finalproject.nthuevent.view.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import netdb.courses.softwarestudio.finalproject.nthuevent.model.domain.Events;
import netdb.courses.softwarestudio.finalproject.nthuevent.model.domain.User;

@SuppressWarnings("serial")
public class EventJsonView extends HttpServlet {
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<User> users=(List<User>)req.getAttribute("userlist");
		String json;
		if(users==null) {
			List<Events> events=(List<Events>)req.getAttribute("model");
			List<EventJson> eventJsons=new ArrayList<EventJson>();
			for(Events e:events) {
				eventJsons.add(new EventJson(e));
			}
			json=JSON.toJSONString(eventJsons);
		}
		else {
			Events event=(Events)req.getAttribute("model");
			EventJson eventJson=new EventJson(event,users);
			json=JSON.toJSONString(eventJson);
		}
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().print(json);
		resp.setStatus(200);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setStatus(201);
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setStatus(200);
	}

}
