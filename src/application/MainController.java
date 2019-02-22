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

	boolean status = true;
	
	GlobalKeyListener globalKeyListener;
	
	@FXML
	public void startBtnAction(ActionEvent event) {
		
		System.out.println("Button is clicked");
		if(status) {
			GlobalScreen.addNativeKeyListener(globalKeyListener);
			status = false;
		}
		else {
			GlobalScreen.removeNativeKeyListener(globalKeyListener);
			status = true;
		}

	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		// http://www.iamkarthi.com/tutorial-jnativehook-control-native-mouse-and-keyboard-calls-outside-java/
		globalKeyListener = new GlobalKeyListener();

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
