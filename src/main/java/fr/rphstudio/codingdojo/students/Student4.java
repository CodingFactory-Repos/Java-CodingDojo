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
    public static boolean needSpeed = true;
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

    public static float checkPointDist;

    // Create updateChargingMode function to check if battery
    public boolean getUpdateChargingMode() {
        float a = getShipBatteryLevel();

        if(a <= 15 && !needForRecharge){
            needForRecharge = true;
            return true;
        } else if(a >= 98 && needForRecharge){
            needForRecharge = false;
            return false;
        } else return needForRecharge;
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
    // public float shipBatteryRadiusX(float x0, float x1){
    //    return x0 - x1;
    //}

    public float goToChargingCheckpoint(){
        float radiusX = shipPositionX - chargingCheckPointX;
        float radiusY = shipPositionY - chargingCheckPointY;

        if(distanceBetweenPositions(chargingCheckPointX, chargingCheckPointY) > ((this.getShipSpeed() * this.getShipSpeed()) / 5f)){
            if(radiusX <= 0.30 && radiusX >= -0.30 && radiusY <= 0.30 && radiusY >= -0.30){
                turn(relativeAngle);
                return -1f;
            } else {
                turn(chargingRelativeAngle);
                return 1f;
            }
        }

        turn(relativeAngle);

        return -1f;
    }

    public float goToCheckpoint(){
        if(distanceBetweenPositions(checkPointX, checkPointY) > ((this.getShipSpeed() * this.getShipSpeed()) / 10f)){
            turn(relativeAngle);
            return 1f;
        }

        turn(nextRelativeAngle);
        return -1f;
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

    public float distanceBetweenPositions(float a, float b){
        float positionX = shipPositionX - a;
        float positionY = shipPositionY - b;

        float convertX = positionX * positionX;
        float convertY = positionY * positionY;

        return this.sqrt(convertY + convertX);
    }

    //
    // END OF VARIABLES/FUNCTIONS AREA
    // -------------------------------------------------------

    @Override
    public void process(int delta) {
        // Clear Console
        System.out.print("\033[H\033[2J");

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

        //  normal check point
        absoluteAngle = atan2(checkPointY-shipPositionY , checkPointX-shipPositionX);
        nextAbsoluteAngle = atan2(nextCheckPointY-shipPositionY , nextCheckPointX-shipPositionX);
        chargingAbsoluteAngle = atan2(chargingCheckPointY-shipPositionY , chargingCheckPointX-shipPositionX);

        relativeAngle = relativeAngleDifference(shipAngle, absoluteAngle);
        nextRelativeAngle = relativeAngleDifference(shipAngle, nextAbsoluteAngle);
        chargingRelativeAngle = relativeAngleDifference(shipAngle, chargingAbsoluteAngle);

        checkPointDist = checkPointX + checkPointY - shipPositionX - shipPositionX;
        // END TEMPORARY VARIABLES ARE

        // Check if the battery is below 15%.
        // If battery < 15% go to Charging CheckPoint
        // Otherwise continue as normal
        if (getUpdateChargingMode()) {
            // If the battery is above 15% go to Charging CheckPoint
            accelerateOrBrake(goToChargingCheckpoint());
        } else {
            if(chargingCheckPointX == checkPointX && chargingCheckPointY == checkPointY){
                if(getShipBatteryLevel() <= 40){
                    System.out.println("ChargingCheckPointX : " + chargingCheckPointX + " : " + checkPointX + " | " + chargingCheckPointY + " | " + checkPointY);

                    needForRecharge = true;
                    accelerateOrBrake(goToChargingCheckpoint());
                } else {
                    if ((getShipBoostLevel() == 100 && getShipSpeed() < 1) || (getShipBoostLevel() == 100 && checkPointDist > 12)) {
                        useBoost();
                    }

                    accelerateOrBrake(goToCheckpoint());
                }
            } else {
                if ((getShipBoostLevel() == 100 && getShipSpeed() < 1) || (getShipBoostLevel() == 100 && checkPointDist > 12)) {
                    useBoost();
                }

                accelerateOrBrake(goToCheckpoint());
            }

            if(getShipSpeed() == 0){
                accelerateOrBrake(1f);
            }
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
