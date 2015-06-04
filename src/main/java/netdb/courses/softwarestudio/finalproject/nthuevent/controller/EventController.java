package netdb.courses.softwarestudio.finalproject.nthuevent.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import netdb.courses.softwarestudio.finalproject.nthuevent.model.domain.Events;
import netdb.courses.softwarestudio.finalproject.nthuevent.service.HttpService;


@SuppressWarnings("serial")
public class EventController extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path=req.getPathInfo();
		if(path!=null&&!path.equals("/")) {
			String id[]=path.split("/");
			if(id.length>2) {
				resp.setStatus(404);
				return;
			}
			req.setAttribute("model", Long.parseLong(id[1]));
			req.getRequestDispatcher("/model/business/events-dao").include(req, resp);
			Events event=(Events)req.getAttribute("model");
			if(event==null) {
				resp.setStatus(404);
				return;
			}
			req.getRequestDispatcher("/view/json/events-json-view").forward(req, resp);
		}
		else {
			req.setAttribute("model", null);
			req.getRequestDispatcher("/model/business/events-dao").include(req, resp);
			req.getRequestDispatcher("/view/json/events-json-view").forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Long userId=HttpService.getUserId(req);
		if(userId==null) {
			resp.setStatus(401);
			return;
		}
		String json=HttpService.getRequestBody(req);
		Events event=JSON.parseObject(json,Events.class);
		event.setPosterId(userId);
		req.setAttribute("model", event);
		req.getRequestDispatcher("/model/business/events-dao").include(req, resp);
		req.getRequestDispatcher("/view/json/events-json-view").forward(req, resp);
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path=req.getPathInfo();
		if(path==null||path.equals("/")) {
			resp.setStatus(404);
			return;
		}
		String id[]=path.split("/");
		if(id.length>2) {
			resp.setStatus(404);
			return;
		}
		Long userId=HttpService.getUserId(req);
		if(userId==null) {
			resp.setStatus(401);
			return;
		}
		Events event=new Events();
		event.setId(Long.parseLong(id[1]));
		event.setPosterId(userId);
		req.setAttribute("model", event);
		req.getRequestDispatcher("/model/business/events-dao").include(req, resp);
		req.getRequestDispatcher("/view/json/events-json-view").forward(req, resp);
	}
	
}
