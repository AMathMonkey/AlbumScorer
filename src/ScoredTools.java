import java.util.Comparator;

public class ScoredTools {
     enum ScoringMethods{
        AVERAGE, TOTAL
    }

    static ScoringMethods option = ScoringMethods.AVERAGE;

    static double format(double score){
        return Math.round(score *1000) / 1000.0;
    }

    static Comparator<Scored> comparator = (o1, o2) -> {
        if(o1.getName().equals(o2.getName())) return 0;
        int result = Double.compare(o2.getScore(), o1.getScore());
        if(result == 0) return -1;
        return result;
    };
}
