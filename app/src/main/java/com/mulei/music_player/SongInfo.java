package com.mulei.music_player;

public class SongInfo {
    public String songname,artistName,songUrl;

    public SongInfo() {
    }

    public SongInfo(String songname, String artistName, String songUrl) {
        this.songname = songname;
        this.artistName = artistName;
        this.songUrl = songUrl;
    }

    public String getSongname() {
        return songname;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getSongUrl() {
        return songUrl;
    }
}
