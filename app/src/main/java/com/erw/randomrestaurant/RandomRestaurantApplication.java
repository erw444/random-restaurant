package com.erw.randomrestaurant;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RandomRestaurantApplication extends Application {

    ExecutorService executorService = Executors.newFixedThreadPool(4);
}
