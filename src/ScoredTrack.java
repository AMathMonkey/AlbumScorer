public class ScoredTrack implements Scored {
    private String name;
    private int rating;

    public ScoredTrack(String title, int rating) {
        name = title;
        this.rating = rating;
    }

    public double getScore() {

        if (ScoredTools.option == ScoredTools.ScoringMethods.AVERAGE) {
            switch (rating) {
                case 1:
                    return -1.0;
                case 2:
                    return -0.5;
                case 3:
                    return 0;
                case 4:
                    return 0.5;
                case 5:
                    return 1.0;
            }
        } else if (ScoredTools.option == ScoredTools.ScoringMethods.TOTAL) {
            switch (rating) {
                case 1:
                    return -5;
                case 2:
                    return -2;
                case 3:
                    return 0;
                case 4:
                    return 2;
                case 5:
                    return 5;
            }
        }
        return 0;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

}
