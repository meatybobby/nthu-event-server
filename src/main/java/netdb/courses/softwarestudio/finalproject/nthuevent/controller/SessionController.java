package netdb.courses.softwarestudio.finalproject.nthuevent.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import netdb.courses.softwarestudio.finalproject.nthuevent.model.domain.UserSession;
import netdb.courses.softwarestudio.finalproject.nthuevent.service.HttpService;

@SuppressWarnings("serial")
public class SessionController extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if(HttpService.getUserId(req)!=null) HttpService.logOut(req);
		String json=HttpService.getRequestBody(req);
		UserSession session=JSON.parseObject(json, UserSession.class);
		if(session.getFbAccessToken()==null) {
			resp.setStatus(400);
			throw new RuntimeException("There is no access token");
		}
		req.setAttribute("model", session);
		req.getRequestDispatcher("/model/business/session-dao").include(req, resp);
		session=(UserSession)req.getAttribute("model");
		if(session==null) {
			resp.setStatus(404);
			return;
		}
		HttpService.logIn(req, session.getUserId());
		req.getRequestDispatcher("/view/json/session-json-view").forward(req, resp);
	}
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpService.logOut(req);
		req.getRequestDispatcher("/view/json/session-json-view").forward(req, resp);
	}
	
}
