package com.next.digip.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Main extends JFrame implements ActionListener{


	private static final long serialVersionUID = 1L;
	private JFrame frmDigipRest;

	/**
	 * Launch the application.
	 */
	public void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmDigipRest.setVisible(true);
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
		frmDigipRest = new JFrame();
		frmDigipRest.setTitle("Digip - Rest Client");
		frmDigipRest.setBounds(100, 100, 590, 430);
		frmDigipRest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDigipRest.getContentPane().setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		frmDigipRest.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		mntmExit.addActionListener(this);
		
		JMenu mnSync = new JMenu("Sync");
		menuBar.add(mnSync);
		
		JMenuItem mntmSyncFiles = new JMenuItem("Sync files");
		mnSync.add(mntmSyncFiles);
		mntmSyncFiles.addActionListener(this);
		
		JMenuItem mntmConfiguration = new JMenuItem("Configuration");
		mnSync.add(mntmConfiguration);
		mntmConfiguration.addActionListener(this);
		
		JMenuItem mntmPreferences = new JMenuItem("Preferences");
		mnSync.add(mntmPreferences);
		mntmPreferences.addActionListener(this);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		mnHelp.addActionListener(this);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(this);
		mnHelp.add(mntmAbout);
		
		JMenuItem mntmTestWebServices = new JMenuItem("Test web services");
		mnHelp.add(mntmTestWebServices);
		mntmTestWebServices.addActionListener(this);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Exit")) {
			frmDigipRest.dispatchEvent(new WindowEvent(frmDigipRest, WindowEvent.WINDOW_CLOSING));
		}
		
		if (e.getActionCommand().equals("Configuration")) {
			WindowConfiguration windowConfiguration = new WindowConfiguration();
			windowConfiguration.setVisible(true);
		}
		
		if(e.getActionCommand().equals("Test web services")) {
			WindowTestWebServices windowTestWebServices = new WindowTestWebServices();
			windowTestWebServices.setVisible(true);
		}
		
		if(e.getActionCommand().equals("About")) {
			WindowAbout windowAbout = new WindowAbout();
			windowAbout.setVisible(true);
		}
		
		if(e.getActionCommand().equals("Sync files")) {
			WindowSync windowSync = new WindowSync();
			windowSync.setVisible(true);
		}
	}
}
