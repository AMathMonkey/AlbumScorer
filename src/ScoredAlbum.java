import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ScoredAlbum implements Scored {
    private String name;
    private List<ScoredTrack> tracks;

    public ScoredAlbum(String title, ScoredTrack... tracks) {
        name = title;
        this.tracks = new ArrayList<>();
        this.tracks.addAll(Arrays.asList(tracks));
    }

    public void addTrack(ScoredTrack track) {
        tracks.add(track);
    }

    public double getScore() {

        double sum = 0;
        for (ScoredTrack track : tracks) {
            sum += track.getScore();
        }
        if (ScoredTools.option == ScoredTools.ScoringMethods.AVERAGE) {
            return sum / tracks.size();
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

    public List<ScoredTrack> getBestTracks(){
        return tracks.stream()
                .filter(t -> t.getRating() == 5)
                .collect(Collectors.toList());
    }

    public List<ScoredTrack> getWorstTracks(){
        return tracks.stream()
                .filter(t -> t.getRating() == 1)
                .collect(Collectors.toList());
    }

}
