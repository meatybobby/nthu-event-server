package netdb.courses.softwarestudio.finalproject.nthuevent.view.json;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netdb.courses.softwarestudio.finalproject.nthuevent.model.domain.User;

import com.alibaba.fastjson.JSON;

@SuppressWarnings("serial")
public class UserJsonView extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		List<User> user=(List<User>)req.getAttribute("model");
		String json=JSON.toJSONString(user);
		
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

}
