package net;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import service.ReportAdvisor;

/**
 * Servlet implementation class daily
 */
public class daily extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public daily() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		long startTime = 0, endTime = 0;
		JSONObject jsObject = new JSONObject();
		try{
			startTime = Long.parseLong(request.getParameter("start"));
			endTime = Long.parseLong(request.getParameter("end"));
			if (endTime <= startTime) {
				throw new Exception();
			}
		} catch (Exception e) {
			jsObject.put("error", "data format error");
			jsObject.put("report", null);
			response.getOutputStream().write(jsObject.toJSONString().getBytes());
			return;
		}
		
		StringBuilder reportBuilder = new StringBuilder();
		String reportPart = null;
		
		// analyze startTime
		Date date = new Date(startTime);
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH");
		int startHour = Integer.parseInt(dateFormat.format(date));	// the hour in startTime
		reportPart = ReportAdvisor.startSleepTimeAdvice(startHour);
		if ( reportPart != null ) {
			reportBuilder.append(reportPart);
		}
		
		// analyze daily sleep hour
		int dailySleepHour = (int)  ((endTime - startTime)/1000/60);
		reportPart = ReportAdvisor.dailySleepTimeAdvice(dailySleepHour);
		if ( reportPart != null ) {
			reportBuilder.append(reportPart);
		}
		
		// if no sleep problem, give a positive feedback
		if (reportBuilder.length() == 0){
			reportBuilder.append("Sound sleep in the day");
		}
		
		jsObject.put("report", reportBuilder.toString());
		System.out.println("request: "
				+ "startTime: " + startTime
				+ "endTime: " + endTime
				);
		System.out.println("report: "
				+ jsObject.toJSONString());
		response.getOutputStream().write(jsObject.toJSONString().getBytes());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}

}
