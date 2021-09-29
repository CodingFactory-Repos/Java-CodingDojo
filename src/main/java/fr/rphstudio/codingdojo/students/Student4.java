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
        } else if(a >= 97 && needForRecharge){
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


    //
    // END OF VARIABLES/FUNCTIONS AREA
    // -------------------------------------------------------

    @Override
    public void process(int delta) {
        // -------------------------------------------------------
        // WRITE YOUR OWN CODE HERE
        //

        setPlayerName("TDBC"); // Name of Spaceship
        selectShip(1);
        setPlayerColor(247, 143, 179, 255); // Color of Spaceship

        // Check if the battery is below 30%.
        // If battery < 30% go to Charging CkeckPoint
        // Otherwise continue as normal
        if (getUpdateChargingMode(getShipBatteryLevel())) {
            // If the battery is above 30% go to Charging CkeckPoint
            turnTowardPosition(getCheckPointX(checkPointCharging(getNbRaceCheckPoints())), getCheckPointY(checkPointCharging(getNbRaceCheckPoints())));
            accelerateOrBrake(0.40f);
        } else {
            // If the battery is above 30%, go to a normal checkpoint
            turnTowardPosition(getCheckPointX(getNextCheckPointIndex()), getCheckPointY(getNextCheckPointIndex()));
            accelerateOrBrake(0.90f);
        }

        //
        // END OF CODE AREA
        // -------------------------------------------------------

        // -------------------------------------------------------
        // DEBUG AREA
        //

        System.out.println(getShipSpeed());

        //
        // END OF DEBUG AREA
        // -------------------------------------------------------
    }

}
