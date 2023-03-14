//Lihao Tran Project 3: Traffic Simulation 12/11/2022
//Main GUI method with the main method

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainGUI implements Runnable, ActionListener {

	static Thread gui;
	JLabel title;
	JLabel status;
	JLabel tl1Lab;
	JLabel tl2Lab;
	JLabel tl3Lab;
	JLabel xCar1Lab;
	JLabel yCar1Lab;
	JLabel xCar2Lab;
	JLabel yCar2Lab;
	JLabel xCar3Lab;
	JLabel yCar3Lab;
	JLabel Car1SpdLab;
	JLabel Car2SpdLab;
	JLabel Car3SpdLab;
	JLabel Car1Spd;
	JLabel Car2Spd;
	JLabel Car3Spd;

	JTextField time;
	final JTextField numCars;
	final JTextField numInt;
	JButton btnPause;

	JFrame jfrm;

	FileClock clock1;
	Thread threadTime;

	TrafficLight tl1;
	TrafficLight tl2;
	TrafficLight tl3;

	Car car1;
	Car car2;
	Car car3;

	boolean simStarted = false;

	private static boolean isRunning;
	private static boolean isPaused = false;

	MainGUI() {
		isRunning = Thread.currentThread().isAlive();
		tl1 = new TrafficLight(TrafficLightColor.RED);
		tl2 = new TrafficLight(TrafficLightColor.RED);
		tl3 = new TrafficLight(TrafficLightColor.RED);

		car1 = new Car();
		car2 = new Car();
		car3 = new Car();

		// Create a new JFrame container.
		jfrm = new JFrame("Traffic Simulation");

		// Specify FlowLayout for the layout manager.
		jfrm.setLayout(null);

		// Give the frame an initial size.
		jfrm.setSize(500, 400);

		// Terminate the program when the user closes the application.
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Make two buttons.
		JButton btnStart = new JButton("Start");
		btnPause = new JButton("Pause");
		JButton btnStop = new JButton("Stop");

		btnStart.setBounds(100, 40, 100, 25);
		btnPause.setBounds(200, 40, 100, 25);
		btnStop.setBounds(300, 40, 100, 25);

		// Create a text field.
		time = new JTextField(15);
		time.setBounds(150, 80, 215, 25);

		numCars = new JTextField("How many cars?");
		numCars.setBounds(175, 225, 150, 25);

		numInt = new JTextField("How many Intersections?");
		numInt.setBounds(150, 250, 175, 25);

		// Add action listeners.
		btnStart.addActionListener(this);
		btnPause.addActionListener(this);
		btnStop.addActionListener(this);

		// Add the buttons to the content pane.
		jfrm.add(btnStart);
		jfrm.add(btnPause);
		jfrm.add(btnStop);
		jfrm.add(time);
		jfrm.add(numCars);
		jfrm.add(numInt);

		// Create a label.
		title = new JLabel("Traffic Simulation Program");
		title.setBounds(175, 15, 300, 25);

		status = new JLabel("Status");
		status.setBounds(225, 60, 300, 25);

		tl1Lab = new JLabel("Traffic Light 1 Color");
		tl1Lab.setBounds(100, 110, 200, 25);
		tl2Lab = new JLabel("Traffic Light 2 Color");
		tl2Lab.setBounds(200, 110, 200, 25);
		tl3Lab = new JLabel("Traffic Light 3 Color");
		tl3Lab.setBounds(300, 110, 200, 25);

		xCar1Lab = new JLabel("Car 1 x Position");
		xCar1Lab.setBounds(100, 130, 200, 25);
		yCar1Lab = new JLabel("Car 1 x Position");
		yCar1Lab.setBounds(100, 150, 200, 25);
		xCar2Lab = new JLabel("Car 2 x Position");
		xCar2Lab.setBounds(200, 130, 200, 25);
		yCar2Lab = new JLabel("Car 2 x Position");
		yCar2Lab.setBounds(200, 150, 200, 25);
		xCar3Lab = new JLabel("Car 3 x Position");
		xCar3Lab.setBounds(300, 130, 200, 25);
		yCar3Lab = new JLabel("Car 3 x Position");
		yCar3Lab.setBounds(300, 150, 200, 25);

		Car1Spd = new JLabel("Car 1 Speed");
		Car1Spd.setBounds(100, 170, 200, 25);
		Car1SpdLab = new JLabel("Car 1 Speed");
		Car1SpdLab.setBounds(100, 185, 200, 25);
		Car2Spd = new JLabel("Car 2 Speed");
		Car2Spd.setBounds(200, 170, 200, 25);
		Car2SpdLab = new JLabel("Car 2 Speed");
		Car2SpdLab.setBounds(200, 185, 200, 25);
		Car3Spd = new JLabel("Car 3 Speed");
		Car3Spd.setBounds(300, 170, 200, 25);
		Car3SpdLab = new JLabel("Car 3 Speed");
		Car3SpdLab.setBounds(300, 185, 200, 25);

		// Add the label to the frame.
		jfrm.add(title);
		jfrm.add(status);
		jfrm.add(tl1Lab);
		jfrm.add(tl2Lab);
		jfrm.add(tl3Lab);
		jfrm.add(xCar1Lab);
		jfrm.add(yCar1Lab);
		jfrm.add(xCar2Lab);
		jfrm.add(yCar2Lab);
		jfrm.add(xCar3Lab);
		jfrm.add(yCar3Lab);
		jfrm.add(Car1SpdLab);
		jfrm.add(Car2SpdLab);
		jfrm.add(Car3SpdLab);
		jfrm.add(Car1Spd);
		jfrm.add(Car2Spd);
		jfrm.add(Car3Spd);

		// Display the frame.
		jfrm.setVisible(true);

		clock1 = new FileClock(time);
		threadTime = new Thread(clock1);
		threadTime.start();
	}

	// Handle button events.
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals("Start")) {
			status.setText("Program started");
			simStarted = true;
			tl1.start();
			car1.start();
			car2.start();
			car3.start();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tl2.start();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tl3.start();

			// Check how many number of intersection and cars
			if (numCars.getText().equals("1")) {
				System.out.println("HI::=");
				jfrm.remove(xCar2Lab);
				jfrm.remove(yCar2Lab);
				jfrm.remove(xCar3Lab);
				jfrm.remove(yCar3Lab);
				jfrm.remove(Car2SpdLab);
				jfrm.remove(Car3SpdLab);
				jfrm.remove(Car2Spd);
				jfrm.remove(Car3Spd);
				jfrm.revalidate();
				jfrm.repaint();
			}
			if (numInt.getText().equals("1")) {
				jfrm.remove(tl2Lab);
				jfrm.remove(tl3Lab);
				jfrm.revalidate();
				jfrm.repaint();
			}
			if (numCars.getText().equals("2")) {
				jfrm.remove(xCar3Lab);
				jfrm.remove(yCar3Lab);
				jfrm.remove(Car3SpdLab);
				jfrm.remove(Car3Spd);
				jfrm.revalidate();
				jfrm.repaint();
			}
			if (numInt.getText().equals("2")) {
				jfrm.remove(tl3Lab);
				jfrm.revalidate();
				jfrm.repaint();
			}
		}
		//check is pause button is pressed
		if (ae.getActionCommand().equals("Pause")) {
			status.setText("Program is paused");
			tl1.pause();
			tl1.interrupt();
			tl2.pause();
			tl2.interrupt();
			tl3.pause();
			tl3.interrupt();
			car1.pause();
			car2.interrupt();
			car2.pause();
			car2.interrupt();
			car3.pause();
			car3.interrupt();
			btnPause.setText("Continue");
		} //check if continue button is pressed
		if (ae.getActionCommand().equals("Continue")) {
			status.setText("Program is continued");
			tl1.resume();
			tl2.resume();
			tl3.resume();
			car1.resume();
			car2.resume();
			car3.resume();
			btnPause.setText("Pause");
		} //check is stop button is pressed
		if (ae.getActionCommand().equals("Stop") && isPaused == false) {
			status.setText("Program is stopped");
			tl1.stop();
			tl2.stop();
			tl3.stop();
			car1.stop();
			car2.stop();
			car3.stop();
		}
	}

	@Override
	public void run() {
		while (isRunning) {
			tl1Lab.setText(tl1.getColor().toString());
			tl2Lab.setText(tl2.getColor().toString());
			tl3Lab.setText(tl3.getColor().toString());
			if (tl1.changed == true) { //if intersection is red, the car should stop 
				if (tl1.getColor().toString() == "RED") {
					System.out.print("Car is at RED Light");
					car1.atLight = true;
					car2.atLight = true;
					car3.atLight = true;
				} else { //if light is green or yellow, the car is not atLight and should move
					System.out.print("Car is at Yellow/Green Light");
					car1.atLight = false;
					car2.atLight = false;
					car3.atLight = false;
				}
				System.out.println(tl1.getColor().toString());
				tl1.changed = false;
			} //update the labels for car position and speed
			xCar1Lab.setText(String.valueOf(car1.getXPosition()));
			xCar2Lab.setText(String.valueOf(car2.getXPosition()));
			xCar3Lab.setText(String.valueOf(car3.getXPosition()));
			Car1Spd.setText(String.valueOf(car1.getSpeed()));
			Car2Spd.setText(String.valueOf(car1.getSpeed()));
			Car3Spd.setText(String.valueOf(car1.getSpeed()));
		}
	}

	public static void main(String args[]) {
		// Create the frame on the event dispatching thread.
		MainGUI test = new MainGUI();
		gui = new Thread(test);
		gui.start();
	}
}
