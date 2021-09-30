/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.rphstudio.codingdojo.students;

import fr.rphstudio.codingdojo.game.Pod;
import fr.rphstudio.codingdojo.game.PodPlugIn;

/**
 *
 * @author Romuald GRIGNON
 */
public class Student4 extends PodPlugIn {
    public Student4(Pod p) {
        super(p);
    }

    // -------------------------------------------------------
    // DECLARE YOUR OWN VARIABLES AND FUNCTIONS HERE
    //

    public static boolean needForRecharge = false;

    // Create updateChargingMode function to check if battery
    public boolean getUpdateChargingMode(float a) {
        if(a <= 15 && !needForRecharge){
            needForRecharge = true;
            return true;
        } else if(a >= 98 && needForRecharge){
            needForRecharge = false;
            return false;
        } else if(needForRecharge){
            return true;
        } else {
            return false;
        }
    }
    
    // Create checkPointCharging function to check if the CheckPoint is chargeable 
    public int checkPointCharging(int a) {
        for (int i = 1; i <= a; i++) {
            if (isCheckPointCharging(i)) {
                return i;
            }
        }
        return 0;
    }

    // Create a ShipBatteryRadius function that will allow to have the coordinates between the SpaceShip and the Battery CheckPoint
    public float shipBatteryRadiusX(float x0, float x1){
        return x0 - x1;
    }

    public float goToChargingCheckpoint(float xShip, float xCheckpoint){
        float radius = xShip - xCheckpoint;

        if (radius <= 0.20 && radius >= -0.20){ // If the radius is between 0.75 and -0.75
            return -1f;
        } else if (radius <= 0.50 && radius >= -0.50){ // If the radius is between 0.75 and -0.75
            return 0.1f;
        } else if (radius <= 0.75 && radius >= -0.75){ // If the radius is between 0.75 and -0.75
            return 0.5f;
        }

        return 1f;
    }

    public float goToCheckpoint(float xShip, float xCheckpoint, float yShip, float yCheckpoint){
        float radiusX = xShip - xCheckpoint;
        float radiusY = yShip - yCheckpoint;

        //if (radiusX <= 0.20 && radiusX >= -0.20 && radiusY <= 0.20 && radiusY >= -0.20){ // If the radius is between 0.75 and -0.75
        //    System.out.println("-1f " + radiusX + ":" + radiusY);
        //    return -1f;
        //} else if (radiusX <= 0.75 && radiusX >= -0.75 && radiusY <= 0.75 && radiusY >= -0.75){
        //    System.out.println("0.05f " + radiusX + ":" + radiusY);
        //    return 0.05f;
        //} else if (radiusX <= 0.85 && radiusX >= -0.85 && radiusY <= 0.85 && radiusY >= -0.85){ // If the radius is between 0.75 and -0.75
        //    System.out.println("-0.5f " + radiusX + ":" + radiusY);
        //    return -0.9f;
        //} else if (radiusX <= 1.5 && radiusX >= -0.5 && radiusY <= 0.5 && radiusY >= -0.5){
        //    System.out.println("0.75f " + radiusX + ":" + radiusY);
        //    return 0.75f;
        //}

        return 0.8f;
    }

    //
    // END OF VARIABLES/FUNCTIONS AREA
    // -------------------------------------------------------

    @Override
    public void process(int delta) {
        // -------------------------------------------------------
        // WRITE YOUR OWN CODE HERE
        //

        setPlayerName("Bebou"); // Name of Spaceship
        selectShip(1);
        setPlayerColor(247, 143, 179, 255); // Color of Spaceship

        // Check if the battery is below 30%.
        // If battery < 30% go to Charging CheckPoint
        // Otherwise continue as normal
        if (getUpdateChargingMode(getShipBatteryLevel())) {
            // If the battery is above 30% go to Charging CheckPoint
            turnTowardPosition(getCheckPointX(checkPointCharging(getNbRaceCheckPoints())), getCheckPointY(checkPointCharging(getNbRaceCheckPoints())));

            // Coordonées de notre SpaceShip
            // getShipPositionX
            // getShipPositionY

            // Coordonées de la batterie
            // getCheckPointX(checkPointCharging(getNbRaceCheckPoints()))
            // getCheckPointY(checkPointCharging(getNbRaceCheckPoints()))

            accelerateOrBrake(goToChargingCheckpoint(getShipPositionX(), getCheckPointX(checkPointCharging(getNbRaceCheckPoints()))));
        } else {
            // If the battery is above 30%, go to a normal checkpoint
            turnTowardPosition(getCheckPointX(getNextCheckPointIndex()), getCheckPointY(getNextCheckPointIndex()));
            accelerateOrBrake(goToCheckpoint(getShipPositionX(), getCheckPointX(getNextCheckPointIndex()), getShipPositionY(),  getCheckPointY(getNextCheckPointIndex())));
        }

        //
        // END OF CODE AREA
        // -------------------------------------------------------

        // -------------------------------------------------------
        // DEBUG AREA
        //

        // System.out.println("X " + shipBatteryRadiusX(getShipPositionX(), getCheckPointX(checkPointCharging(getNbRaceCheckPoints()))) + " | Y " + getCheckPointY(checkPointCharging(getNbRaceCheckPoints())));

        //
        // END OF DEBUG AREA
        // -------------------------------------------------------
    }

}
