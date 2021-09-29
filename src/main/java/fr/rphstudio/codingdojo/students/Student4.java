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
    public Student4(Pod p){
        super(p);
    }
    
    //-------------------------------------------------------
    // DECLARE YOUR OWN VARIABLES AND FUNCTIONS HERE

    // Create updateChargingMode variable to check if battery
    public static boolean updateChargingMode(float a){
        return a <= 30;
    }


    // All variables


    // END OF VARIABLES/FUNCTIONS AREA
    //-------------------------------------------------------
    
    @Override
    public void process(int delta)
    {   
        //-------------------------------------------------------
        // WRITE YOUR OWN CODE HERE
        
        setPlayerName("Wallah tu es trop beau");
        selectShip(1);
        setPlayerColor(247, 143, 179,255);

        accelerateOrBrake(1f);

        //moveAndRecharge(1f, 0, 100);


        // Check if the battery is below 30%.
        // If battry < 30% go to Charging CkeckPoint
        // Otherwise continue as normal
        if(updateChargingMode(getShipBatteryLevel())) {
            turnTowardFirstChargingCheckPoint();
        } else {
            turnTowardNextCheckPoint();
        }

         System.out.println(getShipBatteryLevel());




        // END OF CODE AREA
        //-------------------------------------------------------
    }
    
}
