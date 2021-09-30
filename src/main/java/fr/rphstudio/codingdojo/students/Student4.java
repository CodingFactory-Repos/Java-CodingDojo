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
public class  Student4 extends PodPlugIn {
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
        } else if (radius <= 1 && radius >= -1){ // If the radius is between 0.75 and -0.75
            return 0.5f;
        }

        return 1f;
    }

    public float goToCheckpoint(float xShip, float xCheckpoint, float yShip, float yCheckpoint){
        float radiusX = xShip - xCheckpoint;
        float radiusY = yShip - yCheckpoint;

        if (radiusX <= 0.50 && radiusX >= -0.50 && radiusY <= 0.50 && radiusY >= -0.50){ // If the radius is between 0.75 and -0.75
            System.out.println("-0.5f " + radiusX + ":" + radiusY);
            return -0.5f;
        } else if (radiusX <= 1 && radiusX >= -1 && radiusY <= 1 && radiusY >= -1){
            System.out.println("0.05f " + radiusX + ":" + radiusY);
            return 0.05f;
        } else if (radiusX <= 2 && radiusX >= -2 && radiusY <= 2 && radiusY >= -2){ // If the radius is between 0.75 and -0.75
            System.out.println("0.30f " + radiusX + ":" + radiusY);
            return 0.30f;
        } else if (radiusX <= 2.5 && radiusX >= -2.5 && radiusY <= 2.5 && radiusY >= -2.5){
            System.out.println("0.75f " + radiusX + ":" + radiusY);
            return 0.75f;
        }

        return 1f;
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

        // -------------------------------------------------------
        // TEMPORARY VARIABLES
        //

        // normal check point variables (return positions)
        float checkPointX = getCheckPointX(getNextCheckPointIndex());
        float checkPointY = getCheckPointY(getNextCheckPointIndex());

        // charging checkpoint variables (return positions)
        float chargingCheckPointX = getCheckPointX(checkPointCharging(getNbRaceCheckPoints()));
        float chargingCheckPointY = getCheckPointY(checkPointCharging(getNbRaceCheckPoints()));

        // ship position ( float x , y )
        float shipPositionX = getShipPositionX();
        float shipPositionY = getShipPositionY();

        float shipAngle = getShipAngle(getShipIndex());

        //  normal check point
        float absoluteAngle = getAbsoluteAngleFromPositions(shipPositionX, shipPositionY, checkPointX, checkPointY);
        float relativeAngle = getRelativeAngleDifference(shipAngle, absoluteAngle);
        //  charging checkpoint
        float chargingAbsoluteAngle = getAbsoluteAngleFromPositions(shipPositionX, shipPositionY, chargingCheckPointX, chargingCheckPointY);
        float chargingRelativeAngle = getRelativeAngleDifference(shipAngle, chargingAbsoluteAngle);
        //
        // END TEMPORARY VARIABLES ARE
        // -------------------------------------------------------


        // Check if the battery is below 30%.
        // If battery < 30% go to Charging CheckPoint
        // Otherwise continue as normal
        if (getUpdateChargingMode(getShipBatteryLevel())) {
            // If the battery is above 30% go to Charging CheckPoint
            turn(chargingRelativeAngle);
            accelerateOrBrake(goToChargingCheckpoint(shipPositionX, chargingCheckPointX));
        } else {
            // If the battery is above 30%, go to a normal checkpoint
            turn(relativeAngle);
            accelerateOrBrake(goToCheckpoint(shipPositionX, checkPointX, shipPositionY,  checkPointY));
        }

        //
        // END OF CODE AREA
        // -------------------------------------------------------

        // -------------------------------------------------------
        // DEBUG AREA
        //

        // System.out.println("getNbShips: " + getNbShips());
        // System.out.println("getShipIndex: " + getShipIndex());
        // System.out.println("X " + shipBatteryRadiusX(getShipPositionX(), getCheckPointX(checkPointCharging(getNbRaceCheckPoints()))) + " | Y " + getCheckPointY(checkPointCharging(getNbRaceCheckPoints())));

        //
        // END OF DEBUG AREA
        // -------------------------------------------------------
    }

}
