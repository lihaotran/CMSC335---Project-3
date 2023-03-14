//Lihao Tran Project 3: Traffic Simulation 12/11/2022
//Car object, show the x position of the car and stops on RED lights

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class Car implements Runnable {

	private int xPos = 0;
	private int yPos = 0; // yPos always 0
	private AtomicBoolean running = new AtomicBoolean(false);
	public Boolean atLight = false;
	public Boolean paused = false;

	public Thread thread;
	private int speed = 0;
	
	//create random x Position for the car to start at
	public Car() {
		this.xPos = ThreadLocalRandom.current().nextInt(0, 3000);
	}

	//return the xPos 
	public synchronized int getXPosition() {
		return xPos;
	}
	//create new thread when car object is started
	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
			System.out.println("CAR STARTED");
		}

	}
	//stop the car object thread
	public void stop() {
		thread.interrupt();
		running.set(false);

	}
	//pause the thread
	public void pause() {
		paused = true;
	}
	//resume the thread
	public synchronized void resume() {
		if (paused || atLight) {
			paused = false;
			atLight = false;
			notify();
		}
	}
	//interrupt the thread when car thread is paused
	public void interrupt() {
		thread.interrupt();
	}
	//return the speed of the car
	public int getSpeed() {
		if (running.get()) {
			if (atLight)
				speed = 0;
			else
				speed = 36; // kph
		} else
			speed = 0;
		return speed;
	}
	//run the car object
	public void run() {
		running.set(true);
		System.out.println(running);
		while (running.get()) {

			try { //if car over 3000 xPos reset to 0
				while (xPos < 3000) {
					synchronized (this) { //stop the car at the light or if thread paused
						while (paused || atLight) {
							wait();
						}
					} //increment 1m every 1/10th of second
					Thread.sleep(100);
					xPos += 1;
				}
				xPos = 0;
			} catch (InterruptedException e) {
				System.out.print(e);
				return;
			}
		}
	}
}