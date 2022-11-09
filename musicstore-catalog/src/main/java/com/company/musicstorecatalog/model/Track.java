package com.company.musicstorecatalog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "track")
public class Track {
    @Id
    @Column(name = "track_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long trackId;
    @Column(name = "album_id")
    private long albumId;
    private String title;
    @Column(name = "run_time")
    private int runTime;

    public Track() {
    }

    public Track(long trackId, long albumId, String title, int runTime) {
        this.trackId = trackId;
        this.albumId = albumId;
        this.title = title;
        this.runTime = runTime;
    }

    public long getTrackId() {
        return trackId;
    }

    public void setTrackId(long trackId) {
        this.trackId = trackId;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return trackId == track.trackId && albumId == track.albumId && runTime == track.runTime && Objects.equals(title, track.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackId, albumId, title, runTime);
    }

    @Override
    public String toString() {
        return "TrackRepository{" +
                "trackId=" + trackId +
                ", albumId=" + albumId +
                ", title='" + title + '\'' +
                ", runTime=" + runTime +
                '}';
    }
}
