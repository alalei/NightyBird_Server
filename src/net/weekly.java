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
 * Servlet implementation class weekly
 */
public class weekly extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public weekly() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		long []  startTime = new long[7], endTime = new long[7];
		int[] dailySleepHour = new int[7];
		JSONObject jsObject = new JSONObject();
		try{
			for (int i = 1; i < 8; i++) {
				String start = "start" + i;
				String end = "end" + i;
				System.out.println("test: " + start + "; " + end);
				startTime[i-1] = Long.parseLong(request.getParameter(start));
				endTime[i-1] = Long.parseLong(request.getParameter(end));
				dailySleepHour[i-1] = (int)  ((endTime[i-1] - startTime[i-1])/1000/60);
				if (endTime[i-1] <= startTime[i-1]) {
					throw new Exception();
				}
			}
		} catch (Exception e) {
			jsObject.put("error", "data format error");
			jsObject.put("report", null);
			response.getOutputStream().write(jsObject.toJSONString().getBytes());
			return;
		}
		
		StringBuilder reportBuilder = new StringBuilder();
		String reportPart = null;
		
		int weeklyAverageHour = 0 ;
		for (int i=0; i<7; i++) {
			weeklyAverageHour +=dailySleepHour[i];
		}
		weeklyAverageHour /= 7;
		reportPart = ReportAdvisor.averageTimeAdvice(weeklyAverageHour);
		
		jsObject.put("report", reportPart);
		System.out.println("request: "
				+ "startTime[]: " + startTime
				+ "endTime[]: " + endTime
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
