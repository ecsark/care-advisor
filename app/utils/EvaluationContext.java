package utils;

import java.util.*;

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

    public EvaluationContext () {
        eval = new HashMap<T, Double>();
    }

    public EvaluationContext(double baseAdd, double baseMultiply, double initial) {
        this();
        this.baseAdd = baseAdd;
        this.baseMultiply = baseMultiply;
        this.initial = initial;
    }

    public double getOrZero (T key) {
        if (!eval.containsKey(key))
            return 0;
        return eval.get(key);
    }

    public void put (T key, Double value) {
        eval.put(key, value);
    }

    public void plus (T key) {
        plus(key, baseAdd);
    }

    public void plus (T key, Double addValue) {
        if (!eval.containsKey(key))
            eval.put(key, initial);
        eval.put(key, eval.get(key) + addValue);
    }

    public void multiply (T key) {
        multiply(key, baseMultiply);
    }

    public void multiply (T key, Double multiplyValue) {
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

    public double getMaxValue() {
        double maxValue = Double.MIN_VALUE;
        for (Map.Entry<T, Double> entry : eval.entrySet()) {
            if (entry.getValue() > maxValue) {
                maxValue = entry.getValue();
            }
        }
        return maxValue;
    }

    public List<Map.Entry<T, Double>> getEntryOfTopNValue(int n) {

        List<Map.Entry<T, Double>> entries =new ArrayList<>();

        eval.entrySet().stream()
                .sorted(Comparator.comparing(e -> -e.getValue()))
                .limit(n)
                .forEach(e -> entries.add(e));

        return entries;
    }

    public List<T> getKeyOfTopNValue(int n) {

        List<T> keys =new ArrayList<>();

        eval.entrySet().stream()
                .sorted(Comparator.comparing(e -> -e.getValue()))
                .limit(n)
                .forEach(e -> keys.add(e.getKey()));

        return keys;
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