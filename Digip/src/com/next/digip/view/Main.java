package com.next.digip.view;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Main extends JFrame implements ActionListener{


	private static final long serialVersionUID = 1L;
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		mntmExit.addActionListener(this);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenuItem mntmConfiguration = new JMenuItem("Configuration");
		mnEdit.add(mntmConfiguration);
		mntmConfiguration.addActionListener(this);
		
		JMenuItem mntmPreferences = new JMenuItem("Preferences");
		mnEdit.add(mntmPreferences);
		mntmPreferences.addActionListener(this);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		
		JMenuItem mntmTestWebServices = new JMenuItem("Test web services");
		mnHelp.add(mntmTestWebServices);
		mntmTestWebServices.addActionListener(this);
		
//		System.out.println(mntmTestWebServices.getActionCommand());
		
		
		frame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		mntmAbout.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Exit")) {
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		}
		
		if (e.getActionCommand().equals("Configuration")) {
			WindowConfiguration windowConfiguration = new WindowConfiguration();
			windowConfiguration.setVisible(true);
		}
		
		if(e.getActionCommand().equals("Test web services")) {
			WindowTestWebServices windowTestWebServices = new WindowTestWebServices();
			windowTestWebServices.setVisible(true);
		}
	}
}
