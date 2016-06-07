package com.timesinternet.alive.search.services;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Scheduler {

	Timer timer;

	public void start() {
		timer = new Timer(true);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		calendar.set(Calendar.HOUR, 04);
		calendar.set(Calendar.MINUTE, 11);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		System.out.println(calendar.getTime());
		//timer.schedule(new Task(), calendar.getTime(),24*60*60*1000);
	}
}

class Task extends TimerTask {
	QueryTracker queryTracker = new QueryTracker();
	EmailService emailService = new EmailService();
	List<String> queryList;

	@Override
	public void run() {		
		queryList = queryTracker.getSearchQueriesList();
		if(queryList!=null && !queryList.isEmpty()){
			emailService.sendEmail(queryList);	
		}
		
	}

}
