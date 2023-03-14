//Lihao Tran Project 3: Traffic Simulation 12/11/2022
//TrafficLight Object: shows the traffic light signal

import java.util.concurrent.atomic.AtomicBoolean;

enum TrafficLightColor {
	RED, GREEN, YELLOW
}

// A computerized traffic light. 
class TrafficLight implements Runnable {
	private TrafficLightColor tlc; // holds the current traffic light color
	private boolean stop = false; // set to true to stop the simulation
	public boolean changed = false; // true when the light has changed
	public boolean pause = false;
	public final AtomicBoolean suspended = new AtomicBoolean(false);
	Thread thread;

	TrafficLight(TrafficLightColor init) {
		tlc = init;
	}

	TrafficLight() {
		tlc = TrafficLightColor.RED;
	}

	// Start up the light.
	public void run() {
		while (!stop) {
			try {
				synchronized (this) {
					while (suspended.get()) {
						wait();
					}
				}
				switch (tlc) {
				case GREEN:
					Thread.sleep(5000); // green for 10 seconds
					break;
				case YELLOW:
					Thread.sleep(2000); // yellow for 2 seconds
					break;
				case RED:
					Thread.sleep(2000); // red for 12 seconds
					break;
				}
			} catch (InterruptedException exc) {
				System.out.println(exc);
				suspended.set(true);
			}
			changeColor();
		}
	}

	// Change color.
	synchronized void changeColor() {
		switch (tlc) {
		case RED:
			tlc = TrafficLightColor.GREEN;
			break;
		case YELLOW:
			tlc = TrafficLightColor.RED;
			break;
		case GREEN:
			tlc = TrafficLightColor.YELLOW;
		}

		changed = true;
		notify(); // signal that the light has changed
	}

	// Wait until a light change occurs.
	synchronized void waitForChange() {
		try {
			while (!changed)
				wait(); // wait for light to change
			changed = false;
		} catch (InterruptedException exc) {
			System.out.println(exc);
		}
	}

	// Return current color.
	synchronized TrafficLightColor getColor() {
		return tlc;
	}
	//get the status of the program
	synchronized boolean getStatus1() {
		return stop;
	}

	// Stop the traffic light.
	synchronized void stop() {
		stop = true;
	}
	// pause the traffic light program
	public void pause() {
		suspended.set(true);
	}
	//resume the traffic light program
	public synchronized void resume() {
		suspended.set(false);
		notify();
	}
	//interrupt the tread when pausing
	public void interrupt() {
		thread.interrupt();
	}
	//create a new traffic light thread when start is pressed
	public void start() {
		System.out.println("Starting Light");
		if (thread == null) {
			stop = false;
			thread = new Thread(this);
			thread.start();
		}
	}
}