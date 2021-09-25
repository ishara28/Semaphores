package com.lab_2;

import java.util.Scanner;

import com.lab_2.components.WaitingArea;
import com.lab_2.generators.GeneratorBus;
import com.lab_2.generators.GeneratorRider;


public class Main {

    public static void main(String[] args) throws InterruptedException {

//        float rider_inter_arrival_mean_time = 30f * 1000; // inter arrival mean time of rider
//        float bus_inter_arrival_mean_time = 20 * 60f * 1000 ; // inter arrival mean time of bus


        float rider_inter_arrival_mean_time = 1f * 1000; // inter arrival mean time of rider
        float bus_inter_arrival_mean_time = 60f * 1000 ; // inter arrival mean time of bus

        Scanner scanner = new Scanner(System.in);
        String user_input;
        WaitingArea waiting_area = new WaitingArea();

        System.out.println("\n** Press any key for stopping the simulation. **\n" );

        GeneratorRider generator_rider = new GeneratorRider(rider_inter_arrival_mean_time, waiting_area);
        (new Thread(generator_rider)).start();

        GeneratorBus generator_bus = new GeneratorBus(bus_inter_arrival_mean_time,waiting_area);
        (new Thread(generator_bus)).start();

        // Terminate the program with a user input
        while(true){
            user_input = scanner.nextLine();
            if(user_input != null)
            	System.out.println("--------------------");
            	System.out.println("Programme Stopped..!");
            	System.out.println("--------------------");
                System.exit(0);
        }
    }
}