package com.anad.mobile.post.Models;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by elias.mohammadi on 96/10/27
 */

public class Points implements Comparable<Points> {
    private double N;
    private double E;

    public double getN() {
        return N;
    }

    public void setN(double n) {
        N = n;
    }

    public double getE() {
        return E;
    }

    public void setE(double e) {
        E = e;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    private double speed;

    @Override
    public int compareTo(@NonNull Points o) {
        return 0;
    }

    public static Comparator<Points> CompareBaseOnN = new Comparator<Points>() {
        @Override
        public int compare(Points o1, Points o2) {
            return (int)(o1.N - o2.N);
        }
    };

    public static Comparator<Points> CompareBaseOnE = new Comparator<Points>() {
        @Override
        public int compare(Points o1, Points o2) {
            return (int)(o1.E - o2.E);
        }
    };

    public static Comparator<Points> CompareBaseOnSpeed = new Comparator<Points>() {
        @Override
        public int compare(Points o1, Points o2) {
            return (int)(o2.speed - o1.speed);
        }
    };

}
