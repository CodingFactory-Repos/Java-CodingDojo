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
    public static float checkPointX;
    public static float checkPointY;

    public static float chargingCheckPointX;
    public static float chargingCheckPointY;

    public static float shipPositionX;
    public static float shipPositionY;

    public static float shipAngle;

    public static float tang;
    public static float chargingTang;

    public static float absoluteAngle;
    public static float relativeAngle;

    public static float chargingAbsoluteAngle;
    public static float chargingRelativeAngle;

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

        double[][] speed = { { 0.2, -1f }, { 0.5, 0.1f }, { 1, 0.5f } };

        for (int i = 0; i < speed.length; i++){
            if (radius <= speed[i][0] && radius >= -speed[i][0]){ // If the radius is between 0.75 and -0.75
                System.out.println(speed[i][1] + "f " + radius);
                return (float) speed[i][1];
            }
        }

        return 1f;

        //if (radius <= 0.20 && radius >= -0.20){ // If the radius is between 0.75 and -0.75
        //    return -1f;
        //} else if (radius <= 0.50 && radius >= -0.50){ // If the radius is between 0.75 and -0.75
        //    return 0.1f;
        //} else if (radius <= 1 && radius >= -1){ // If the radius is between 0.75 and -0.75
        //    return 0.5f;
        //}
    }

    public float goToCheckpoint(float xShip, float xCheckpoint, float yShip, float yCheckpoint){
        float radiusX = xShip - xCheckpoint;
        float radiusY = yShip - yCheckpoint;

        double[][] speed = { { 0.5, -0.5f }, { 1, 0.05f }, { 2, 0.20f }, { 2.5, 0.30f }, { 3, 0.50 } };

        for (int i = 0; i < speed.length; i++){
            if (radiusX <= speed[i][0] && radiusX >= -speed[i][0] && radiusY <= speed[i][0] && radiusY >= -speed[i][0]){ // If the radius is between 0.75 and -0.75
                System.out.println(speed[i][1] + "f " + radiusX + ":" + radiusY);
                return (float) speed[i][1];
            }
        }

        return 1f;

        //if (radiusX <= 0.50 && radiusX >= -0.50 && radiusY <= 0.50 && radiusY >= -0.50){ // If the radius is between 0.75 and -0.75
        //    System.out.println("-0.5f " + radiusX + ":" + radiusY);
        //    return -0.5f;
        //} else if (radiusX <= 1 && radiusX >= -1 && radiusY <= 1 && radiusY >= -1){
        //    System.out.println("0.05f " + radiusX + ":" + radiusY);
        //    return 0.05f;
        //} else if (radiusX <= 2 && radiusX >= -2 && radiusY <= 2 && radiusY >= -2){ // If the radius is between 0.75 and -0.75
        //    System.out.println("0.30f " + radiusX + ":" + radiusY);
        //    return 0.30f;
        //} else if (radiusX <= 2.5 && radiusX >= -2.5 && radiusY <= 2.5 && radiusY >= -2.5){
        //    System.out.println("0.75f " + radiusX + ":" + radiusY);
        //    return 0.75f;
        //}
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
        checkPointX = getCheckPointX(getNextCheckPointIndex());
        checkPointY = getCheckPointY(getNextCheckPointIndex());

        // charging checkpoint variables (return positions)
        chargingCheckPointX = getCheckPointX(checkPointCharging(getNbRaceCheckPoints()));
        chargingCheckPointY = getCheckPointY(checkPointCharging(getNbRaceCheckPoints()));

        // ship position ( float x , y )
        shipPositionX = getShipPositionX();
        shipPositionY = getShipPositionY();

        shipAngle = getShipAngle(getShipIndex());

        tang =  atan2(checkPointY-shipPositionY , checkPointX-shipPositionX);
        chargingTang =  atan2(chargingCheckPointY-shipPositionY , chargingCheckPointX-shipPositionX);
        //tang =  atan2(checkPointY-shipPositionY , checkPointX-shipPositionX);


        //  normal check point
        absoluteAngle =  tang;
        relativeAngle = getRelativeAngleDifference(shipAngle, absoluteAngle);
        //  charging checkpoint
        chargingAbsoluteAngle = chargingTang;
        chargingRelativeAngle = getRelativeAngleDifference(shipAngle, chargingAbsoluteAngle);
        //

        // END TEMPORARY VARIABLES ARE

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
