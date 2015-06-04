package netdb.courses.softwarestudio.finalproject.nthuevent.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HttpService {
	static public String getRequestBody(HttpServletRequest req) throws IOException {
		StringBuffer buff=new StringBuffer();
		String s;
		while((s=req.getReader().readLine())!=null)
			buff.append(s);
		return buff.toString();
	}
	static public void logIn(HttpServletRequest req,Long id) {
		HttpSession session=req.getSession(true);
		session.setMaxInactiveInterval(12*60*60);
		session.setAttribute("login", id);
	}
	static public Long getUserId(HttpServletRequest req) {
		HttpSession session=req.getSession(false);
		if(session==null)
			return null;
		return (Long)session.getAttribute("login");
	}
	static public void logOut(HttpServletRequest req) {
		HttpSession session=req.getSession(false);
		if(session!=null)
			session.invalidate();
	}
}
