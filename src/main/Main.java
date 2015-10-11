package main;

import org.eclipse.swt.widgets.Display;

public class Main {

	public Main(){
		Display.setAppName("Browwy");
		Display display = Display.getDefault();
		setupNewWindow(display);
		while (!display.isDisposed()) {
			if (display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	public Window setupNewWindow(Display display){
		Window win = new Window(display);
		return win;
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
