package com.lab_2.generators;

import java.util.Random;

import com.lab_2.components.Bus;
import com.lab_2.components.WaitingArea;

public class GeneratorBus implements Runnable {

    private final float inter_arrival_mean_time;
    private final WaitingArea waiting_area;
    private static Random random;

    public GeneratorBus(float inter_arrival_mean_time, WaitingArea waiting_area) {
        this.inter_arrival_mean_time = inter_arrival_mean_time;
        this.waiting_area = waiting_area;
        random = new Random();
    }

    @Override
    public void run() {

        int bus_index = 1;

        // Spawn bus threads for the user specified value
        while (!Thread.currentThread().isInterrupted()) {

            try {
                // Initialize and start the bus threads
                Bus bus = new Bus(
                                    bus_index,
                                    waiting_area,
                                    WaitingArea.get_mutex(),
                                    WaitingArea.get_semaphore_bus_departure(),
                                    WaitingArea.get_semaphore_rider_boarding_area_entrance()
                                );

                (new Thread(bus)).start();

                bus_index++;

                // Sleep the thread to obtain the inter arrival time between the bus threads
                Thread.sleep(get_exponentially_distributed_bus_inter_arrival_time());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("All buses have finished arriving");
    }

    // get the exponentially distributed bus inter arrival time
    public long get_exponentially_distributed_bus_inter_arrival_time() {
        float lambda = 1 / inter_arrival_mean_time;
        return Math.round(-Math.log(1 - random.nextFloat()) / lambda);
    }
}