/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.codingdojo.students;


import fr.rphstudio.codingdojo.game.Pod;
import fr.rphstudio.codingdojo.game.PodPlugIn;

import java.lang.reflect.Array;

/**
 *
 * @author Romuald GRIGNON
 */
public class Student4 extends PodPlugIn {
    public Student4(Pod p){
        super(p);
    }

    //-------------------------------------------------------
    // DECLARE YOUR OWN VARIABLES AND FUNCTIONS HERE
    //

    // Create updateChargingMode variable to check if battery
    public static boolean updateChargingMode(float a){
        return a <= 30;
    }


    public int checkpointCharging(int a) {
        for(int i = 1; i <= a; i++) {
            if (isCheckPointCharging(i)) {
                return i;
            }
        }
        return 0;
    }

    //
    // END OF VARIABLES/FUNCTIONS AREA
    //-------------------------------------------------------

    @Override
    public void process(int delta)
    {
        //-------------------------------------------------------
        // WRITE YOUR OWN CODE HERE
        //

        setPlayerName("Wallah tu es trop beau");
        selectShip(1);
        setPlayerColor(247, 143, 179,255);
        // getFirstChargingCheckPointIndex();

        //moveAndRecharge(1f, 0, 100);

        // Check if the battery is below 30%.
        // If battery < 30% go to Charging CkeckPoint
        // Otherwise continue as normal
        if(updateChargingMode(getShipBatteryLevel())) {
            turnTowardPosition(getCheckPointX(checkpointCharging(getNbRaceCheckPoints())),getCheckPointY(checkpointCharging(getNbRaceCheckPoints())));
            accelerateOrBrake(0.5f);
        } else {
            turnTowardPosition(getCheckPointX(getNextCheckPointIndex()),getCheckPointY(getNextCheckPointIndex()));
            accelerateOrBrake (1f);
        }

        //
        // END OF CODE AREA
        //-------------------------------------------------------
    }

}
