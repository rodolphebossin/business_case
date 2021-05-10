package com.humanbooster.Business_case_admin.services;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.springframework.stereotype.Service;

@Service
public class CountDownService {
	
	Timer timer;
	
	JFrame window;
	JLabel counterLabel;
	Font font1 = new Font("Arial", Font.PLAIN, 50);
	
	int second, minute;
	String ddSecond, ddMinute;
	
	DecimalFormat dFormat = new DecimalFormat("00");
	
	public void launchCountdown() {
		window = new JFrame();
		window.setSize(400,300);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLayout(null);
		
		counterLabel = new JLabel("this is smple");
		counterLabel.setBounds(150, 120, 100, 50);
		counterLabel.setHorizontalAlignment(JLabel.CENTER);
		counterLabel.setFont(font1);
		
		window.add(counterLabel);
		window.setVisible(true);
		
		counterLabel.setText("00:00");
		second = 0;
		minute = 0;
		countdownTimer();
		timer.start();
	}
	
	
	public void countdownTimer() {
		
		timer = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				second--;
				ddSecond = dFormat.format(second);
				ddMinute = dFormat.format(minute);
				counterLabel.setText(ddMinute + ":" + ddSecond);
				
				if(second==-1) {
					second = 59;
					minute--;
					ddSecond = dFormat.format(second);
					ddMinute = dFormat.format(minute);
					counterLabel.setText(ddMinute + ":" + ddSecond);
				}
				if(minute == 0 && second == 0) {
					timer.stop();
				}
			}
			
		});
	}

}
