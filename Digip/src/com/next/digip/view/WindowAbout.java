package com.next.digip.view;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class WindowAbout extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					WindowAbout frame = new WindowAbout();
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
	public WindowAbout() {
		setType(Type.POPUP);
		setTitle("About");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 447, 267);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, -6, 455, 152);
		contentPane.add(panel);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon(WindowAbout.class.getResource("/imagenes/logo_acerca_de.jpg")));
		panel.add(lblNewLabel);
		
		JLabel lblCopyrightNext = new JLabel("Copyright \u00AE Next Sistemas");
		lblCopyrightNext.setHorizontalAlignment(SwingConstants.CENTER);
		lblCopyrightNext.setBounds(123, 157, 194, 14);
		contentPane.add(lblCopyrightNext);
		
		JLabel lblV = new JLabel("v1.40 - 21 Dic 2019");
		lblV.setHorizontalAlignment(SwingConstants.CENTER);
		lblV.setBounds(133, 182, 175, 14);
		contentPane.add(lblV);
		
		JLabel lblEstamosEn = new JLabel("Estamos en: http://www.nextsistemas.com.ar");
		lblEstamosEn.setHorizontalAlignment(SwingConstants.CENTER);
		lblEstamosEn.setBounds(86, 207, 269, 14);
		contentPane.add(lblEstamosEn);


	}
}
	

