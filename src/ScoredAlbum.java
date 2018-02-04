import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ScoredAlbum implements Scored {
    private boolean isEP;
    private boolean isInvalidAlbum = false;
    private String name;
    private List<ScoredTrack> tracks;

    ScoredAlbum(String title, ScoredTrack... tracks) {
        name = title;
        this.tracks = new ArrayList<>();
        this.tracks.addAll(Arrays.asList(tracks));
        isEP = name.contains("(EP)");
    }

    void addTrack(ScoredTrack track) {
        tracks.add(track);
    }

    public boolean isEP() {
        return isEP;
    }

    public boolean isInvalidAlbum() {
        return isInvalidAlbum;
    }

    public double getScore() {

        double sum = 0;
        int invalidRatings = 0;
        for (ScoredTrack track : tracks) {
            int rating = track.getRating();
            if (rating < 1 || rating > 5) {
                invalidRatings++;
            } else {
                sum += track.getScore();
            }
        }
        if (ScoredTools.option == ScoredTools.ScoringMethods.AVERAGE) {
            if (tracks.size() - invalidRatings == 0) {
                isInvalidAlbum = true;
            }
            return isInvalidAlbum ? 9999 : sum / (tracks.size() - invalidRatings);
        } else if (ScoredTools.option == ScoredTools.ScoringMethods.TOTAL) {
            return sum;
        }
        return 0;
    }

    public String getName() {
        return name;
    }

    public List<ScoredTrack> getTracks() {
        return Collections.unmodifiableList(tracks);
    }

    List<ScoredTrack> getBestTracks() {
        return tracks.stream()
                .filter(t -> t.getRating() == 5)
                .collect(Collectors.toList());
    }

    List<ScoredTrack> getWorstTracks() {
        return tracks.stream()
                .filter(t -> t.getRating() == 1)
                .collect(Collectors.toList());
    }

}
