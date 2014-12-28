package utils;

import java.util.Map;

/**
 * User: ecsark
 * Date: 12/28/14
 * Time: 11:49
 */
public class EvaluationContext<T> {
    public Map<T, Double> eval;

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