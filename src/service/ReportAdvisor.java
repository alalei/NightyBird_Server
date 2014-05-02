package service;

public class ReportAdvisor {
	final static int daylightStart = 8;
	final static int nightThreshold = 23;
	final static int dailyHourThreshold = 6;
	final static int averageHourThreshold = 7;

	public static String startSleepTimeAdvice (int hour) {
		if ( hour >= nightThreshold || hour < daylightStart){
			return "It is better to sleep before " + nightThreshold + " at night";
		} else {
			return null;
		}
	}
	
	public static String dailySleepTimeAdvice (int hour) {
		if ( hour < dailyHourThreshold){
			return "Eat Vitamin C would help you regain lost VC at the night";
		} else {
			return null;
		}
	}
	
	public static String averageTimeAdvice (int hour) {
		if ( hour < averageHourThreshold ){
			return "You slept too little in the past week."
					+ " A bird sleeps early will be more energetic than a bird stay up late.";
		} else {
			return "The average sleep time is okay";
		}
	}

}
