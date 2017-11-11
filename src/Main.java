import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Reader in = new FileReader("RatedTrackInput.csv");
        Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader("Artist", "Album", "Track", "Rating").parse(in);
        Set<ScoredArtist> artists = new HashSet<>();
        boolean foundFlag;

        ScoredArtist artistPointer = null;
        ScoredArtist prevArtistPointer = null;
        ScoredAlbum albumPointer = null;
        ScoredAlbum prevAlbumPointer = null;

        for (CSVRecord record : records) {
            String trackString = record.get("Track");
            if (trackString.equals("")) continue;
            String artistString = record.get("Artist");
            String albumString = record.get("Album");
            int rating = Integer.parseInt(record.get("Rating"));

            foundFlag = false;
            if (artistString.equals("")) {
                artistPointer = prevArtistPointer;
            } else {
                for (ScoredArtist artist : artists) {
                    if (artist.getName().equals(artistString)) {
                        artistPointer = artist;
                        foundFlag = true;
                        break;
                    }
                }
                if (!foundFlag) {
                    artistPointer = new ScoredArtist(artistString);
                }
            }
            foundFlag = false;

            if (albumString.equals("")) {
                albumPointer = prevAlbumPointer;
            } else {
                for (ScoredAlbum album : artistPointer.getAlbums()) {
                    if (album.getName().equals(albumString)) {
                        albumPointer = album;
                        foundFlag = true;
                        break;
                    }
                }
                if (!foundFlag) {
                    albumPointer = new ScoredAlbum(albumString);
                }
            }

            ScoredTrack trackPointer = new ScoredTrack(trackString, rating);
            albumPointer.addTrack(trackPointer);

            artistPointer.addAlbum(albumPointer);
            artists.add(artistPointer);
            prevArtistPointer = artistPointer;
            prevAlbumPointer = albumPointer;

        }

        for (ScoredArtist a : artists) {
            System.out.println(a.getName() + ":");
            int i = 1;
            for (ScoredAlbum al : a.getAlbums()) {
                System.out.print("\t" + i + ") " + al.getName() + ": ");
                System.out.println(ScoredTools.format(al.getScore()));
                i++;
            }

            System.out.println(a.getName() + "'s final score: " + ScoredTools.format(a.getScore()) + '\n');
        }
        for (ScoredArtist a : artists) {
            System.out.println("\n\t\t" + a.getName() + "'s Best Tracks:");
            for (ScoredAlbum al : a.getAlbums()) {
                for (ScoredTrack t : al.getBestTracks()) {
                    System.out.println("\t\t" + t.getName());
                }
            }
            System.out.println("\n\t\t" + a.getName() + "'s Worst Tracks:");
            for (ScoredAlbum al : a.getAlbums()) {
                for (ScoredTrack t : al.getWorstTracks()) {
                    System.out.println("\t\t" + t.getName());
                }
            }
        }
    }
}

