package main;

public class localBrowser {
	private Window win;
	
	public localBrowser(Window win){
		this.win = win;
	}
	
	public void performSearch(){
		String url = win.searchArea.getText();
		if(!url.isEmpty())
			win.browser.setUrl(url);
	}
}
