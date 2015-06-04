package netdb.courses.softwarestudio.finalproject.nthuevent.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import netdb.courses.softwarestudio.finalproject.nthuevent.model.domain.Comment;
import netdb.courses.softwarestudio.finalproject.nthuevent.service.HttpService;

@SuppressWarnings("serial")
public class CommentController extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path=req.getPathInfo();
		if(path==null||path.equals("/")) {
			resp.setStatus(404);
			return;
		}
		String id[]=path.split("/");
		Long eventId=Long.parseLong(id[1]);
		req.setAttribute("model", eventId);
		req.getRequestDispatcher("/model/business/comment-dao").include(req, resp);
		if(req.getAttribute("model")==null) {
			resp.setStatus(404);
			return;
		}
		req.getRequestDispatcher("/view/comment-json-view").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path=req.getPathInfo();
		if(path==null||path.equals("/")) {
			resp.setStatus(404);
			return;
		}
		String[] id=path.split("/");
		String json=HttpService.getRequestBody(req);
		Comment comment=JSON.parseObject(json, Comment.class);
		comment.setEventId(Long.parseLong(id[1]));
		Long userId=HttpService.getUserId(req);
		if(userId==null) {
			resp.setStatus(401);
			return;
		}
		comment.setUserId(userId);
		req.setAttribute("model", comment);
		req.getRequestDispatcher("/model/business/comment-dao").include(req, resp);
		req.getRequestDispatcher("/view/comment-json-view").forward(req, resp);
	}
}
