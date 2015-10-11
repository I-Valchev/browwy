package main;

import java.io.InputStream;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class Window {
	private static final int GRID_NUM = 20, SEARCH_BUTTON_SPAN = 2, SEARCH_AREA_SPAN = 17, SETTINGS_BUTTON_SPAN = 1;	
	
	private Shell shell;
	private Display display;
	private localBrowser localBrowser;
	
	private Button searchButton, settingsButton;
	private GridData grid;
	private Menu settingsMenu;
	
	protected Browser browser;
	protected Text searchArea;
	
	public Window(Display display){
		this.display = display;
		shell = new Shell();
		shell.setText("Browwy");
		shell.setLayout(new GridLayout(GRID_NUM, true));
		InputStream imgStream = this.getClass().getResourceAsStream("/main/logo-big.png");
		Image logoIcon = new Image(display, imgStream);
		shell.setImage(logoIcon);
		this.grid = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		localBrowser = new localBrowser(this);
		
		setupSearchArea();
		setupSearchButton();
		setupSettingsButton();
		setupBrowser();
		
		shell.open();
		shell.layout();
	}
	
	public void setupSearchArea(){
		GridData grid = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		
		searchArea = new Text(shell, SWT.BORDER);
		searchArea.addKeyListener(searchAreaKeyListener);
		grid.horizontalSpan = SEARCH_AREA_SPAN;
		searchArea.setLayoutData(grid);
	}
	
	KeyListener searchAreaKeyListener = new KeyListener() {
		@Override
		public void keyPressed(KeyEvent key) {
			if(key.keyCode == SWT.CR){
				localBrowser.performSearch();
			}		
		}
		@Override
		public void keyReleased(KeyEvent key) {}
	};
	
	private void setupSearchButton(){
		searchButton = new Button(shell, SWT.NONE);
		searchButton.addSelectionListener(searchButtonListener);
		searchButton.setText("Search");
		grid = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		grid.horizontalSpan = SEARCH_BUTTON_SPAN;
		searchButton.setLayoutData(grid);
	}
	
	SelectionListener searchButtonListener = new SelectionListener() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			localBrowser.performSearch();
		}
		@Override
		public void widgetDefaultSelected(SelectionEvent e) {}
	};
	
	private void setupSettingsButton(){
		settingsButton = new Button(shell, SWT.NONE);
		grid = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		grid.horizontalSpan = SETTINGS_BUTTON_SPAN;
		settingsButton.setLayoutData(grid);
		settingsButton.addSelectionListener(settingsButtonListener);
		InputStream imgStream = this.getClass().getResourceAsStream("/main/settings-icon.png");
		Image settingsImg = new Image(display, imgStream);
		settingsButton.setImage(settingsImg);
		
		settingsMenu = new Menu(settingsButton);
		
		MenuItem newWindowItem = new MenuItem(settingsMenu, SWT.NONE);
		newWindowItem.setText("New window");
		newWindowItem.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				new Main().setupNewWindow(display);
			}
		});

		new MenuItem(settingsMenu, SWT.SEPARATOR);
		
		MenuItem closeWindowItem = new MenuItem(settingsMenu, SWT.NONE);
		closeWindowItem.setText("Close window");
		closeWindowItem.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event e) {
				closeWindow();
			}
		});
		
		settingsButton.setMenu(settingsMenu);
	}
	
	SelectionListener settingsButtonListener = new SelectionListener() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			settingsMenu.setVisible(true);
		}
		@Override
		public void widgetDefaultSelected(SelectionEvent e) {}
	};
	
	private void setupBrowser(){
		browser = new Browser(shell, SWT.BORDER);
		browser.setSize(shell.getSize());
		grid = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		grid.horizontalSpan = GRID_NUM;
		grid.verticalAlignment = GridData.FILL;
		grid.grabExcessVerticalSpace = true;
		browser.setLayoutData(grid);
	}
	
	public void closeWindow(){
		shell.close();
	}
	
}
