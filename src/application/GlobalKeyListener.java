package application;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;


public class GlobalKeyListener implements NativeKeyListener {
	
	ActivityCounterInterface counterInterface;
	
	public void setActivityCounterInterface(ActivityCounterInterface counterInterface){
		this.counterInterface = counterInterface;
	}
		
public void nativeKeyPressed(NativeKeyEvent e) {
	//System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
	
	if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
		try {
			GlobalScreen.unregisterNativeHook();
		} catch (NativeHookException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

public void nativeKeyReleased(NativeKeyEvent e) {
	counterInterface.addKeyActivityCount();
	//System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
}

public void nativeKeyTyped(NativeKeyEvent e) {
	//System.out.println("Key Typed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
}

}