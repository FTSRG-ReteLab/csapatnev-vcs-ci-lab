package hu.bme.mit.train.controller;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import hu.bme.mit.train.interfaces.TrainController;

public class TrainControllerImpl implements TrainController {

	private int step = 0;
	private int referenceSpeed = 0;
	private int speedLimit = 0;
	private int time = 0;

	private Table<Integer, Integer, Integer> tachometer;

	public TrainControllerImpl() {
		tachometer = HashBasedTable.create();

	}

	private void storeValuesInTachometer(int a, int b, int c) {
		tachometer.put(a, b, c);
	}

	public int getNumberOfStoredValues() {
		return tachometer.size();
	}

	@Override
	public void followSpeed() {
		// This is the Follow speed
		if (referenceSpeed < 0) {
			referenceSpeed = 0;
		} else {
			referenceSpeed += step;
		}

		storeValuesInTachometer(time++, step, referenceSpeed);
		enforceSpeedLimit();
	}

	@Override
	public int getReferenceSpeed() {
		return referenceSpeed;
	}

	@Override
	public void setSpeedLimit(int speedLimit) {
		if (speedLimit < 0) {
			speedLimit = 0; // dummy comment
		}
		this.speedLimit = speedLimit;
		enforceSpeedLimit();

	}

	private void enforceSpeedLimit() {
		// Check whether if the speed is too high and limit it.
		if (referenceSpeed > speedLimit) {
			referenceSpeed = speedLimit;
		}

		// Check whether if the train has negative velocity
		else if (referenceSpeed < 0) {
			referenceSpeed = 0;
		}
	}

	@Override
	public void setJoystickPosition(int joystickPosition) {
		this.step = joystickPosition;
	}

}
