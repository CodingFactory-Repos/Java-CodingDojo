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
    public static float nextCheckPointX;
    public static float nextCheckPointY;

    public static float chargingCheckPointX;
    public static float chargingCheckPointY;

    public static float shipPositionX;
    public static float shipPositionY;

    public static float shipAngle;

    public static float absoluteAngle;
    public static float nextAbsoluteAngle;
    public static float relativeAngle;
    public static float nextRelativeAngle;

    public static float relativeAngleDifference;
    public static float nextRelativeAngleDifference;
    public static float chargingRelativeAngleDifference;

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

    public float goToChargingCheckpoint(){
        float radius = shipPositionX - chargingCheckPointX;

        double[][] speed = { { 0.1, -1f }, { 0.5, 0.05f }, { 1, 0.5f }, {5, 0.6} };

        if(radius <= speed[0][0] && radius >= -speed[0][0]){
            turn(relativeAngle);
        } else {
            turn(chargingRelativeAngle);
        }

        for (int i = 0; i < speed.length; i++){
            if (radius <= speed[i][0] && radius >= -speed[i][0]){ // If the radius is between 0.75 and -0.75
                System.out.println(speed[i][1] + "f " + radius);
                return (float) speed[i][1];
            }
        }

        return 1f;
    }

    public float goToCheckpoint(){
        float radiusX = shipPositionX - checkPointX;
        float radiusY = shipPositionY - checkPointY;

        double[][] speed = { { 0.5, -0.9f }, {1, -0.5f} };

        //if(radiusX <= 1 && radiusX >= -1 && radiusY <= 1 && radiusY >= -1){
        //    turn(nextRelativeAngle);
        //   return  -0.75f;
        //} else {
            turn(relativeAngle);
        //}

        for(int i = 0; i < speed.length; i++){
            if (radiusX <= speed[i][0] && radiusX >= -speed[i][0] && radiusY <= speed[i][0] && radiusY >= -speed[i][0]){ // If the radius is between 0.75 and -0.75
                System.out.println(speed[i][1] + "f " + radiusX + ":" + radiusY);
                return (float) speed[i][1];
            }
        }

        return 1f;
    }

    public float relativeAngleDifference(float a, float b) {
        float radius = b - a;
        if (radius > 180.0f) {
            radius -= 360.0f;
        }

        if (radius < -180.0f) {
            radius += 360.0f;
        }

        return radius;
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

        try {
            nextCheckPointX = getCheckPointX(getNextCheckPointIndex() + 1);
            nextCheckPointY = getCheckPointY(getNextCheckPointIndex() + 1);
        } catch (Exception e){
            nextCheckPointX = getCheckPointX(0);
            nextCheckPointY = getCheckPointY(0);
        }

        // charging checkpoint variables (return positions)
        chargingCheckPointX = getCheckPointX(checkPointCharging(getNbRaceCheckPoints()));
        chargingCheckPointY = getCheckPointY(checkPointCharging(getNbRaceCheckPoints()));

        // ship position ( float x , y )
        shipPositionX = getShipPositionX();
        shipPositionY = getShipPositionY();

        shipAngle = getShipAngle(getShipIndex());

        relativeAngleDifference = (absoluteAngle - shipAngle) ;
        nextRelativeAngleDifference = (nextAbsoluteAngle - shipAngle) ;
        chargingRelativeAngleDifference = (chargingAbsoluteAngle - shipAngle);
        //tang =  atan2(checkPointY-shipPositionY , checkPointX-shipPositionX);


        //  normal check point
        absoluteAngle = atan2(checkPointY-shipPositionY , checkPointX-shipPositionX);
        nextAbsoluteAngle = atan2(nextCheckPointY-shipPositionY , nextCheckPointX-shipPositionX);
        chargingAbsoluteAngle = atan2(chargingCheckPointY-shipPositionY , chargingCheckPointX-shipPositionX);

        relativeAngle = relativeAngleDifference(shipAngle, absoluteAngle);
        nextRelativeAngle = relativeAngleDifference(shipAngle, nextAbsoluteAngle) - 360;
        chargingRelativeAngle = relativeAngleDifference(shipAngle, chargingAbsoluteAngle);
        //  charging checkpoint
        //

        //if ((relativeAngleDifference > 180 || relativeAngleDifference < -180) || (chargingRelativeAngleDifference > 180 || chargingRelativeAngleDifference < -180) || (chargingRelativeAngleDifference > 180 || chargingRelativeAngleDifference < -180) ){
        //    relativeAngle = -relativeAngleDifference;
        //    nextRelativeAngle = -nextRelativeAngleDifference;
        //    chargingRelativeAngle = -chargingRelativeAngleDifference;
        //} else {
        //    relativeAngle = relativeAngleDifference;
        //    nextRelativeAngle = nextRelativeAngleDifference;
        //    chargingRelativeAngle = chargingRelativeAngleDifference;
        //}

        // END TEMPORARY VARIABLES ARE

        // Check if the battery is below 30%.
        // If battery < 30% go to Charging CheckPoint
        // Otherwise continue as normal
        if (getUpdateChargingMode(getShipBatteryLevel())) {
            // If the battery is above 30% go to Charging CheckPoint
            accelerateOrBrake(goToChargingCheckpoint());
        } else {
            // If the battery is above 30%, go to a normal checkpoint
            accelerateOrBrake(goToCheckpoint());
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
