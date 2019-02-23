package application;

import java.net.URL;
import java.util.ResourceBundle;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class MainController implements Initializable{

	@FXML
	private Button startBtn;
	public int keyCount = 0;
	public int MouseCount = 0;
	boolean status = true;
	
	GlobalKeyListener globalKeyListener;
	GlobalMouseListener globalMouseListener;
	
	@FXML
	public void startBtnAction(ActionEvent event) {
		
		System.out.println("Button is clicked");
		if(status) {
			GlobalScreen.addNativeKeyListener(globalKeyListener);
			GlobalScreen.addNativeMouseListener(globalMouseListener);
			//GlobalScreen.addNativeMouseMotionListener(globalMouseListener);
			startBtn.setText("Pause");
			status = false;
		}
		else {
			GlobalScreen.removeNativeKeyListener(globalKeyListener);
			GlobalScreen.removeNativeMouseListener(globalMouseListener);
			//GlobalScreen.removeNativeMouseMotionListener(globalMouseListener);
			startBtn.setText("Start");
			status = true;
		}

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


}
