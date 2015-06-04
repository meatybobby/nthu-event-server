package netdb.courses.softwarestudio.finalproject.nthuevent.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netdb.courses.softwarestudio.finalproject.nthuevent.model.domain.User;
import netdb.courses.softwarestudio.finalproject.nthuevent.service.HttpService;

import com.alibaba.fastjson.JSON;

@SuppressWarnings("serial")
public class UserController extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/model/business/user-dao").include(req, resp);
		req.getRequestDispatcher("/view/json/user-json-view").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String json=HttpService.getRequestBody(req);
		User user=JSON.parseObject(json,User.class);
		if(user.getFbAccessToken()==null)
			throw new RuntimeException("There is no access token");
		req.setAttribute("model", user);
		req.getRequestDispatcher("/model/business/user-dao").include(req, resp);
		User newUser=(User)req.getAttribute("model");
		if(newUser==null) {
			resp.setStatus(401);
			return;
		}
		if(newUser.getId()==null) {
			resp.setStatus(409);
			return;
		}
		HttpService.logIn(req, newUser.getId());
		req.getRequestDispatcher("/view/json/user-json-view").forward(req, resp);
		
	}
	
}
