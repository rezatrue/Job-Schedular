package application;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

public class GlobalMouseListener implements NativeMouseInputListener {

	ActivityCounterInterface counterInterface;
	
	public void setActivityCounterInterface(ActivityCounterInterface counterInterface){
		this.counterInterface = counterInterface;
	}
	
public void nativeMouseClicked(NativeMouseEvent e) {
//System.out.println("Mouse Clicked: " + e.getClickCount());
}

public void nativeMousePressed(NativeMouseEvent e) {
// System.out.println("Mouse Pressed: " + e.getButton());
}

public void nativeMouseReleased(NativeMouseEvent e) {
	counterInterface.addMouseActivityCount();
	//System.out.println("Mouse Released: " + e.getButton());
}

public void nativeMouseMoved(NativeMouseEvent e) {
//System.out.println("Mouse Moved: " + e.getX() + ", " + e.getY());
}

// this add too many count so can't use
public void nativeMouseDragged(NativeMouseEvent e) {
	//counterInterface.addMouseActivityCount();
	//System.out.println("Mouse Dragged: " + e.getX() + ", " + e.getY());
}

}