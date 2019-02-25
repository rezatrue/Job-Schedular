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
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class MainController  implements Initializable{

	@FXML
	private Button startBtn;
	public int keyCount = 0;
	public int MouseCount = 0;
	boolean status = true;
	
	GlobalKeyListener globalKeyListener;
	GlobalMouseListener globalMouseListener;
	
	@FXML
	public void startBtnAction(ActionEvent event) {
		MyService runService = new MyService();
		runService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				// TODO Auto-generated method stub
			}
		});
		
		if (startBtn.getText().contains("Pause")) {
			startBtn.setText("Start");
			//GlobalScreen.removeNativeKeyListener(globalKeyListener);
			//GlobalScreen.removeNativeMouseListener(globalMouseListener);
			status = false;
			getCurrentTimeUsingCalendar();
			switch(runService.getState().toString()) {
			case "RUNNING":
				runService.cancel();
				break;
			}	
		} else if (startBtn.getText().contains("Start")) {
			startBtn.setText("Pause");
			status = true;
			// calling MyService start / cancel /restart based on getState() 
			System.out.println(runService.getState().toString());
			//GlobalScreen.addNativeKeyListener(globalKeyListener);
			//GlobalScreen.addNativeMouseListener(globalMouseListener);
			switch(runService.getState().toString()) {
			case "READY":
				runService.start();
				break;
			case "CANCELLED":
			case "SUCCEEDED":
				runService.restart();
				break;
			}

		}
		
		System.out.println("Button is clicked");
		
	}
	
	Date startTime;
	Date endTime;
	Date randomTime;
//	String startTime;
//	String endTime;
//	String randomTime;
	
	Calendar cal;
	public void getCurrentTimeUsingCalendar() {
	    cal = Calendar.getInstance();
	    startTime = setTimes(0);
	    System.out.println(startTime);
	    endTime = setTimes(10);
	    System.out.println(endTime);
	    randomTime = setTimes(getRandomTime() - 10 );
	    System.out.println(randomTime);

	}
	
	public Date setTimes(int num) {
		cal.add(Calendar.MINUTE, num);
	    Date date = cal.getTime();
	    return date;
//	    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
//	    String formattedDate = dateFormat.format(date);
//	    return formattedDate;
	}
	
	public int getRandomTime() {
		Random random = new Random();
		return (random.nextInt(9) + 1) ;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		// http://www.iamkarthi.com/tutorial-jnativehook-control-native-mouse-and-keyboard-calls-outside-java/
		globalKeyListener = new GlobalKeyListener();

		globalKeyListener.setActivityCounterInterface(new ActivityCounterInterface() {
			
			@Override
			public void addKeyActivityCount() {
				keyCount++;
				System.out.println("Key activity : " + keyCount);
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
				MouseCount++;
				System.out.println("Mouse activity : " + MouseCount);
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

		
	public class MyService extends Service<String>{

		@Override
		protected Task<String> createTask() {
			// TODO Auto-generated method stub
			return new Task<String>() {

				@Override
				protected String call() throws Exception {
					// capturing images 
					while(startTime.compareTo(endTime) < 0) {
						System.out.println("capturing images --- ");
					}
					return "Start";
				}
				
			};
		}
		
	}

}
