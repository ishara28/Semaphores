package com.lab_2.components;

import java.util.concurrent.Semaphore;


public class Rider implements Runnable {

    private final int index;
    private final Semaphore mutex;
    private WaitingArea waiting_area;
    private final Semaphore semaphore_rider_waiting_area_entrance;
    private final Semaphore semaphore_rider_boarding;
    private final Semaphore semaphore_bus_departure;

    public Rider(
                    int index,
                    Semaphore mutex,
                    WaitingArea waiting_area,
                    Semaphore semaphore_rider_waiting_area_entrance,
                    Semaphore semaphore_rider_boarding,
                    Semaphore semaphore_bus_departure
    ) {

        this.index = index;
        this.mutex = mutex;
        this.waiting_area = waiting_area;
        this.semaphore_rider_waiting_area_entrance = semaphore_rider_waiting_area_entrance;
        this.semaphore_rider_boarding = semaphore_rider_boarding;
        this.semaphore_bus_departure = semaphore_bus_departure;

    }

    @Override
    public void run() {

        try {
            // Acquire the semaphore in trying to enter the rider waiting area, only 50 allowed at a given time
            semaphore_rider_waiting_area_entrance.acquire();

                // Enter the boarding area and incrementing the riders count
                mutex.acquire();
                    enter_boarding_area();
                    waiting_area.riders_count_increment();
                mutex.release();

                //Acquire the semaphore to board the bus
                semaphore_rider_boarding.acquire();
                board_bus();

            // Release the semaphore for enter waiting area
            semaphore_rider_waiting_area_entrance.release();

            // Decrement the rider count once boarded
            waiting_area.riders_count_decrement();

            // When the riders are boarded, allowing the bus to depart by releasing the bus departure semaphore
            if (waiting_area.get_riders_count() == 0)
                semaphore_bus_departure.release();
            // If there are more riders waiting, allowing them to get into the bus
            else
                semaphore_rider_boarding.release();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void enter_boarding_area() {
        System.out.println("Rider - " + index + " entered boarding area");
    }

    public void board_bus() {
        System.out.println("Rider - " + index + " boarded");
    }

}