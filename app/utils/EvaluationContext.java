package utils;

import java.util.HashMap;
import java.util.Map;

/**
 * User: ecsark
 * Date: 12/28/14
 * Time: 11:49
 */
public class EvaluationContext<T> {
    public Map<T, Double> eval;


    public double baseAdd = 1.0;
    public double baseMultiply = 1.2;
    public double initial = 0.0;

    public EvaluationContext () {}

    public EvaluationContext(double baseAdd, double baseMultiply, double initial) {
        this.baseAdd = baseAdd;
        this.baseMultiply = baseMultiply;
        this.initial = initial;
    }

    public void put (T key, Double value) {
        if (eval == null)
            eval = new HashMap<>();
        eval.put(key, value);
    }

    public void plus (T key) {
        plus(key, baseAdd);
    }

    public void plus (T key, Double addValue) {
        if (eval == null)
            eval = new HashMap<>();
        if (!eval.containsKey(key))
            eval.put(key, initial);
        eval.put(key, eval.get(key) + addValue);
    }

    public void multiply (T key) {
        multiply(key, baseMultiply);
    }

    public void multiply (T key, Double multiplyValue) {
        if (eval == null)
            eval = new HashMap<>();
        if (!eval.containsKey(key))
            eval.put(key, initial);
        eval.put(key, eval.get(key) * multiplyValue);
    }


    public T getKeyOfMaxValue() {
        T maxValueKey = null;
        double maxValue = Double.MIN_VALUE;
        for (Map.Entry<T, Double> entry : eval.entrySet()) {
            if (entry.getValue() > maxValue) {
                maxValue = entry.getValue();
                maxValueKey = entry.getKey();
            }
        }
        return maxValueKey;
    }

    public static <R> R getKeyOfMaxValue(Map<R, Double> map) {
        R maxValueKey = null;
        double maxValue = Double.MIN_VALUE;
        for (Map.Entry<R, Double> entry : map.entrySet()) {
            if (entry.getValue() > maxValue) {
                maxValue = entry.getValue();
                maxValueKey = entry.getKey();
            }
        }
        return maxValueKey;
    }
}