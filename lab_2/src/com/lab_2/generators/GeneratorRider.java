package com.lab_2.generators;

import com.lab_2.components.WaitingArea;

import java.util.Random;

import com.lab_2.components.Rider;

public class GeneratorRider implements Runnable {

    private final float inter_arrival_mean_time;
    private WaitingArea waiting_area;
    private static Random random;

    public GeneratorRider(float inter_arrival_mean_time, WaitingArea waiting_area) {
        this.inter_arrival_mean_time = inter_arrival_mean_time;
        this.waiting_area = waiting_area;
        random = new Random();
    }

    @Override
    public void run() {

        int rider_index = 1;

        // Spawn rider threads for the user specified value
        while (!Thread.currentThread().isInterrupted()) {

            try {
                // Initialize and start the rider threads
                Rider rider = new Rider(
                                            rider_index,
                                            WaitingArea.get_mutex(),
                                            waiting_area,
                                            WaitingArea.get_semaphore_rider_waiting_area_entrance(),
                                            WaitingArea.get_semaphore_rider_boarding_area_entrance(),
                                            WaitingArea.get_semaphore_bus_departure()
                                        );
                (new Thread(rider)).start();

                rider_index++;

                // Sleep the thread to obtain the inter arrival time between the threads
                Thread.sleep(get_exponentially_distributed_rider_inter_arrival_time());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public long get_exponentially_distributed_rider_inter_arrival_time() {
        float lambda = 1 / inter_arrival_mean_time;
        return Math.round(-Math.log(1 - random.nextFloat()) / lambda);
    }
}
