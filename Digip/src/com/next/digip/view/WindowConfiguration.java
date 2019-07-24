package com.next.digip.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class WindowConfiguration extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtUser;
	private JPasswordField pwdPassword;
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
		setBounds(100, 100, 197, 173);
		getContentPane().setLayout(null);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 181, 134);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblUser = new JLabel("User:");
		lblUser.setBounds(10, 11, 46, 14);
		panel.add(lblUser);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(10, 36, 76, 14);
		panel.add(lblPassword);
		
		txtUser = new JTextField();
		txtUser.setText("User");
		txtUser.setBounds(76, 8, 86, 20);
		panel.add(txtUser);
		txtUser.setColumns(10);
		
		pwdPassword = new JPasswordField();
		pwdPassword.setText("Password");
		pwdPassword.setBounds(76, 33, 86, 20);
		panel.add(pwdPassword);
		
		btnOk = new JButton("Ok");
		btnOk.setBounds(46, 86, 89, 23);
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
