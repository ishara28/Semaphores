package com.lab_2.components;

import java.util.concurrent.Semaphore;



public class WaitingArea {

    private static int riders_count = 0;
    private static final int maximum_bus_capacity = 50;

    // Semaphore used to handle the access to the riders count variable
    private static final Semaphore mutex = new Semaphore(1);

    // Semaphore used for riders to enter the boarding area
    private static final Semaphore semaphore_rider_boarding_area_entrance = new Semaphore(0);

    // Semaphore used for riders to enter the waiting area, allowing 50 riders to remain at the waiting area
    private static final Semaphore semaphore_rider_waiting_area_entrance = new Semaphore(maximum_bus_capacity);

    // Semaphore used for bus to depart after the riders are boarded
    private static final Semaphore semaphore_bus_departure = new Semaphore(0);


    // get the riders count
    public int get_riders_count() {
        return riders_count;
    }

    // increment the riders count
    public void riders_count_increment() {
        riders_count++;
    }

    // decrement the riders count
    public void riders_count_decrement() {
        riders_count--;
    }


    // access the semaphore that is used for handle access to the riders count
    public static Semaphore get_mutex() {
        return mutex;
    }

    // access the semaphore that is used for riders to board the bus
    public static Semaphore get_semaphore_rider_boarding_area_entrance() {
        return semaphore_rider_boarding_area_entrance;
    }

    // access the semaphore that is used for riders to enter the waiting area
    public static Semaphore get_semaphore_rider_waiting_area_entrance() {
        return semaphore_rider_waiting_area_entrance;
    }

    // access the semaphore that is used for the bus to depart
    public static Semaphore get_semaphore_bus_departure() {
        return semaphore_bus_departure;
    }

}