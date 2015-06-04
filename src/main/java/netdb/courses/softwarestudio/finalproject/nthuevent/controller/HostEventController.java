package netdb.courses.softwarestudio.finalproject.nthuevent.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netdb.courses.softwarestudio.finalproject.nthuevent.service.HttpService;

@SuppressWarnings("serial")
public class HostEventController extends HttpServlet {
		
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path=req.getPathInfo();
		Long userId;
		if(path==null||path.equals("/")) userId=HttpService.getUserId(req);
		else {
			String id[]=path.split("/");
			userId=Long.parseLong(id[1]);
		}
		req.setAttribute("model", userId);
		req.getRequestDispatcher("/model/business/host-dao").include(req, resp);
		if(req.getAttribute("model")==null) {
			resp.setStatus(404);
			return;
		}
		req.getRequestDispatcher("/view/json/events-json-view").forward(req, resp);
	}

}
