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
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
// Developed by Ali Reza (Iron Man)
public class MainController  implements Initializable{
	@FXML
	private Text msgLogin;
	@FXML
	private Text timeLabel;
	@FXML
	private Text empName;
	@FXML
	private Text dayLabel;
	@FXML
	private Button startBtn;
	@FXML
	private Button logInBtn;
	@FXML
	private Button logOutBtn;
	@FXML
	private TextField textEmail;
	@FXML
	private PasswordField textPassword;
	public LogInfo logInfo;
	private TimeCalculator timeCalculator;
	private ImageHandeler imageHandeler;
	private ApiCaller apiCaller;
	private Employee employee;

	
	GlobalKeyListener globalKeyListener;
	GlobalMouseListener globalMouseListener;
	
	@FXML
	private AnchorPane loginPane;
	@FXML
	private AnchorPane TrakingPane;
	
	private int taskid = 1; // need take task id from user
	
	@FXML
	public void logInBtnAction(ActionEvent event) {
		String email = textEmail.getText();
		String password = textPassword.getText();
		System.out.println("email " + email);
		System.out.println("password " + password);

		if(email !="" || password != "") {
			employee = apiCaller.empLogin(email, password);
			//System.out.println("Welcome " + employee.getEmpName());	
		}
		if(employee.getEmpid() > 0) {
			empName.setText(employee.getEmpName());
			trakingPaneUp();
		}
	}
	
	@FXML
	public void logOutBtnAction(ActionEvent event) {
		
		if (startBtn.getText().contains("Pause")) {
			pauseSequence();		
		}
		employee = new Employee();
		loginPaneUp();
		
	}
	
	//www.youtube.com/watch?v=8zOSqvKNTlY&t=4s
	private void trakingPaneUp() {
		TranslateTransition tr1 = new TranslateTransition();
		tr1.setDuration(Duration.millis(100));
		tr1.setToX(0);
		tr1.setToY(-200);
		tr1.setNode(loginPane);
		TranslateTransition tr2 = new TranslateTransition();
		tr2.setDuration(Duration.millis(100));
		tr2.setFromX(0);
		tr2.setFromY(200);
		tr2.setToX(0);
		tr2.setToY(0);
		tr2.setNode(TrakingPane);
		ParallelTransition pt = new ParallelTransition(tr1, tr2);
		pt.play();
	}
	
	private void loginPaneUp() {
		TranslateTransition tr1 = new TranslateTransition();
		tr1.setDuration(Duration.millis(100));
		tr1.setToX(0);
		tr1.setToY(200);
		tr1.setNode(TrakingPane);
		TranslateTransition tr2 = new TranslateTransition();
		tr2.setDuration(Duration.millis(100));
		tr2.setFromX(0);
		tr2.setFromY(-200);
		tr2.setToX(0);
		tr2.setToY(0);
		tr2.setNode(loginPane);
		ParallelTransition pt = new ParallelTransition(tr1, tr2);
		pt.play();
	}
	
	private void pauseSequence() {
		switch(runService.getState().toString()) {
		case "RUNNING":
			runService.cancel();
			break;	
		}
		
		startBtn.setText("Start");
		GlobalScreen.removeNativeKeyListener(globalKeyListener);
		GlobalScreen.removeNativeMouseListener(globalMouseListener);
		Date currentTime = timeCalculator.getCurrentTime();
		Date randomTime = timeCalculator.getRandomTime();
		
		if(randomTime.compareTo(currentTime) > 0) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
    	    String fileName = formatter.format(currentTime);
    	    captureImage(fileName);
		}
		logInfo.setEndtime(currentTime);
		saveActivity();
		
		
	}
	@FXML
	public void startBtnAction(ActionEvent event) {
			
		runService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				// TODO Auto-generated method stub
			}
		});
		
		if (startBtn.getText().contains("Pause")) {
			pauseSequence();
				
		} else if (startBtn.getText().contains("Start")) {
			startBtn.setText("Pause");
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
		employee = new Employee();
		logInfo = new LogInfo();
		apiCaller = new ApiCaller();
		timeCalculator = new TimeCalculator();
		runService = new MyService();
		imageHandeler = new ImageHandeler();
		dayLabel.setText(timeCalculator.getToday());
		
		
		//www.iamkarthi.com/tutorial-jnativehook-control-native-mouse-and-keyboard-calls-outside-java/
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
    		logInfo.setBase64encodedImage(imageHandeler.captureScreen()); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void saveActivity() {
		System.out.println(logInfo.getStarttime());
		logInfo.setEmpid(employee.getEmpid());
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
				        	randomTime = timeCalculator.getRandomTime();
				        }
				        
					}while(run);
					
					return "Start";
				}
				
			};
		}
		
	}

}
