package com.lab_2.components;

import java.util.concurrent.Semaphore;


public class Bus implements Runnable {

    private final int index;
    private final WaitingArea waiting_area;
    private final Semaphore mutex;
    private final Semaphore semaphore_bus_departure;
    private final Semaphore semaphore_rider_boarding;

    public Bus(
                int index,
                WaitingArea waiting_area,
                Semaphore mutex,
                Semaphore semaphore_bus_departure,
                Semaphore semaphore_rider_boarding
    ) {

        this.index = index;
        this.waiting_area = waiting_area;
        this.mutex = mutex;
        this.semaphore_bus_departure = semaphore_bus_departure;
        this.semaphore_rider_boarding = semaphore_rider_boarding;

    }

    @Override
    public void run() {

        try {
            mutex.acquire();
                //  bus arrive
                arrived();

                // Check for waiting riders
                if (waiting_area.get_riders_count() > 0) {

                    // Release the rider boarding semaphore allowing a rider to get into the bus
                    semaphore_rider_boarding.release();
                    // Acquire the bus departure semaphore to wait the bus until the riders get boarded
                    semaphore_bus_departure.acquire();
                }

            mutex.release();

            // bus depart
            depart();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void arrived() {
        System.out.println("** Bus - " + index + " arrived");
        System.out.println("** Waiting rider count - " + waiting_area.get_riders_count());
    }

    public void depart() {
        System.out.println("** Bus - " + index + " departed");
    }

}