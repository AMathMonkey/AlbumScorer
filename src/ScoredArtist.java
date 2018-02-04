import java.util.*;

public class ScoredArtist implements Scored {
    private String name;
    private List<ScoredAlbum> albums;

    public ScoredArtist(String name, ScoredAlbum... albums) {
        this.albums = new ArrayList<>();

        this.name = name;
        Collections.addAll(this.albums, albums);
    }

    public void addAlbum(ScoredAlbum album) {
        if (!albums.contains(album))
            albums.add(album);
    }

    public String getName() {
        return name;
    }

    List<ScoredAlbum> getAlbums() {
        albums.sort(ScoredTools.comparator);
        return Collections.unmodifiableList(albums);
    }

    public double getScore() {
        double sum = 0;
        for (ScoredAlbum a : albums) {
            if(a.isInvalidAlbum()) continue;
            sum += a.getScore();
        }
        if (ScoredTools.option == ScoredTools.ScoringMethods.AVERAGE) {
            return sum / albums.size();
        } else if (ScoredTools.option == ScoredTools.ScoringMethods.TOTAL) {
            return sum;
        }
        return 0;

    }

}


