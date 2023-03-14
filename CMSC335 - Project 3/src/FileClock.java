//Lihao Tran Project 3: Traffic Simulation 12/11/2022
//Clock class where it returns the current time to the GUI

import java.util.Date;
import javax.swing.*;

class FileClock implements Runnable {
	JTextField jtf;

	public FileClock(JTextField jtf) {
		this.jtf = jtf;
	}

	@Override
	public void run() {
		while (true) {
			System.out.printf("%s\n", new Date());
			Date d1 = new Date();
			jtf.setText(d1.toString());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.printf("The FileClock has been interrupted");
			}
		}
	}
}
