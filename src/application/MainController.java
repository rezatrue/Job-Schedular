package application;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import application.MainController.MyService;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MainController  implements Initializable{
	@FXML
	private Label timeLabel;
	@FXML
	private Button startBtn;
	public LogInfo logInfo;
	boolean status = true; // no use now
	private TimeCalculator timeCalculator;
	private ActivityHandeler activityHandeler;
	private ApiCaller apiCaller;
	
	GlobalKeyListener globalKeyListener;
	GlobalMouseListener globalMouseListener;
	
	@FXML
	public void startBtnAction(ActionEvent event) {
				
		runService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				// TODO Auto-generated method stub
			}
		});
		
		if (startBtn.getText().contains("Pause")) {
			startBtn.setText("Start");
			GlobalScreen.removeNativeKeyListener(globalKeyListener);
			GlobalScreen.removeNativeMouseListener(globalMouseListener);
			status = false;
			Date currentTime = timeCalculator.getCurrentTime();
			Date randomTime = timeCalculator.getRandomTime();
			
			if(randomTime.compareTo(currentTime) > 0) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
	    	    String fileName = formatter.format(currentTime);
	    	    captureImage(fileName);
			}
			saveActivity();
			System.out.println(runService.getState().toString());
			switch(runService.getState().toString()) {
			case "RUNNING":
				runService.cancel();
				break;	
			}	
		} else if (startBtn.getText().contains("Start")) {
			startBtn.setText("Pause");
			status = true;
			logInfo = new LogInfo();
			initTime();
			// calling MyService start / cancel /restart based on getState() 
			System.out.println(runService.getState().toString());
			GlobalScreen.addNativeKeyListener(globalKeyListener);
			GlobalScreen.addNativeMouseListener(globalMouseListener);
			
			switch(runService.getState().toString()) {
			case "READY":
				runService.start();
				break;
			case "CANCELLED":	
				runService.restart();
				break;

			}

		}
		
		System.out.println("Button is clicked");
		
	}
	
	
	
	MyService runService;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		logInfo = new LogInfo();
		apiCaller = new ApiCaller();
		timeCalculator = new TimeCalculator();
		runService = new MyService();
		activityHandeler = new ActivityHandeler();
		
		// http://www.iamkarthi.com/tutorial-jnativehook-control-native-mouse-and-keyboard-calls-outside-java/
		globalKeyListener = new GlobalKeyListener();

		globalKeyListener.setActivityCounterInterface(new ActivityCounterInterface() {
			
			@Override
			public void addKeyActivityCount() {
				logInfo.increaseKeycount();
				System.out.println("Key activity : " + logInfo.getKeycount());
			}

			@Override
			public void addMouseActivityCount() { ;	}
		});
		
		try {
			GlobalScreen.registerNativeHook();
			}
			catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
			}

		globalMouseListener = new GlobalMouseListener();
		globalMouseListener.setActivityCounterInterface(new ActivityCounterInterface() {
			
			@Override
			public void addMouseActivityCount() {
				logInfo.increaseMousecount();
				System.out.println("Mouse activity : " + logInfo.getMousecount());
			}
			
			@Override
			public void addKeyActivityCount() {;}
		});
		
		try {
			GlobalScreen.registerNativeHook();
			}
			catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
			}
			
	}

	int hour = 0;
	int minute = 0;
	int second = 0 ;
	
	public String timer() {
		second++;
		if(second == 60) {minute ++; second = 0;}
		if(minute == 60) {hour ++; minute = 0;}
		String time = hour + ":"+ minute +":"+second ;
		return time;
	}
	
	private void captureImage(String fileName) {
	    logInfo.setImage(fileName);
    	try {
    		//logInfo.setBase64encodedImage(activityHandeler.captureScreen()); 
    		logInfo.setBase64encodedImage(activityHandeler.encoder()); 

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private int empid = 1; // Need to take id from login session
	private int taskid = 1;
	
	private void saveActivity() {
		System.out.println(logInfo.getStarttime());
		logInfo.setEmpid(empid);
		logInfo.setTaskid(taskid);
		apiCaller.saveLog(logInfo);
		logInfo = new LogInfo();
	}
	
	private void initTime() {
		timeCalculator.initTimeLogger();
		logInfo.setStarttime(timeCalculator.getStartTime());
		logInfo.setEndtime(timeCalculator.getEndTime());
	}
	
	public class MyService extends Service<String>{

		@Override
		protected Task<String> createTask() {
			// TODO Auto-generated method stub
			return new Task<String>() {
	        	
	        	
				@Override
				protected String call() throws Exception {
					// capturing images
					boolean run = true;
					Date randomTime = timeCalculator.getRandomTime();
					Date currentTime;
					boolean screenShot = true;
					do {
						Thread.sleep(1000);
						//stackoverflow.com/questions/47655695/javafx-countdown-timer-in-label-settext
				        Platform.runLater(() -> timeLabel.setText(timer()));
				        currentTime = timeCalculator.getCurrentTime();
				        
				        if(randomTime.compareTo(currentTime) <= 0 && screenShot) { 
				    	    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
				    	    String fileName = formatter.format(randomTime);
				    	    captureImage(fileName);
				        	screenShot = false;
				        }
				        if(logInfo.getEndtime().compareTo(currentTime) <= 0) {
				        	saveActivity();
				        	screenShot = true;
				        	initTime();
				        }
				        
					}while(run);
					
					return "Start";
				}
				
			};
		}
		
	}

}
