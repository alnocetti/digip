package com.next.digip.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WindowConfiguration extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtUser;
	private JButton btnOk;

	/**
	 * Launch the application.
	 */
//	public WindowConfiguration() {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					WindowConfiguration frame = new WindowConfiguration();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public WindowConfiguration() {
		setTitle("Configuration");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 296, 155);
		getContentPane().setLayout(null);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 280, 123);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblUser = new JLabel("API-KEY:");
		lblUser.setBounds(10, 35, 73, 14);
		panel.add(lblUser);
		
		txtUser = new JTextField();
		txtUser.setText("User");
		txtUser.setBounds(75, 32, 173, 20);
		panel.add(txtUser);
		txtUser.setColumns(10);
		
		btnOk = new JButton("Ok");
		btnOk.setBounds(95, 78, 89, 23);
		panel.add(btnOk);
		this.btnOk.addActionListener(this);
			
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this.btnOk)) {
			this.setVisible(false);
			this.dispose();
		}
		
	}
}
