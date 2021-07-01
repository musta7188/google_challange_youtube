package com.google;

import java.util.ArrayList;
import java.util.List;

/** A class used to represent a Playlist */
class VideoPlaylist {
    private String namePlaylist;
    private List<Video> playListVideos = new ArrayList<>(10);
    private static List<VideoPlaylist> playLists = new ArrayList<>(10);

    boolean setName(String name){
        if (!this.checkPlayListExists(name)) {
            this.namePlaylist = name;
            playLists.add(this);
            return true;
        } else {
            return false;
        }

    }

    public String getNamePlaylist() {
        return namePlaylist;
    }


    public List<Video> getPlayListVideos(){
        return playListVideos;
    }

    public void addVideo(Video v){
        playListVideos.add(v);
    }

    //check if exists any similar playlist by name
    public static boolean checkPlayListExists(String name){
        boolean nameTaken = false;
        for(VideoPlaylist playList: playLists){
            if(playList.getNamePlaylist().toLowerCase().equals(name.toLowerCase())){
                nameTaken = true;
            }
        }
        return nameTaken;
    }

    public boolean checkVideoExists(Video v){
        for(Video video: playListVideos){
            if(video == v)
                return true;
        }
        return false;
    }

    public void removeVideo(String id){

        int index = 0;
        Video video = null;
        for(int i = 0; i < playListVideos.size();i++){
            if(playListVideos.get(i).getVideoId().equals(id))
                index = i;
                video = playListVideos.get(i);
        }

        playListVideos.remove(index);
    }

    public void removePlayList(String name){
        int index = 0;
        for(int i = 0; i<playLists.size();i++){
            if(playLists.get(i).getNamePlaylist().equals(name))
                index = i;
        }

        playLists.remove(index);

    }

    public void clearPlayList(){
        playListVideos = new ArrayList<>(10);
    }

    public static VideoPlaylist getPlayListByName(String name){
        VideoPlaylist pList = null;
        for(VideoPlaylist playlist: playLists){
            if(playlist.getNamePlaylist().toLowerCase().equals(name.toLowerCase()))
                pList = playlist;
        }

        return pList;
    }


    public static List<VideoPlaylist> getAllPlayListsSort(){

        List<VideoPlaylist> vPlayLists = playLists;

        for(int i = 0; i < vPlayLists.size(); ++i) {
            for (int j = i + 1; j < vPlayLists.size(); ++j) {

                if (vPlayLists.get(i).getNamePlaylist().compareTo(vPlayLists.get(j).getNamePlaylist()) > 0) {

                    VideoPlaylist temp = vPlayLists.get(i);
                    vPlayLists.set(i, vPlayLists.get(j));
                    vPlayLists.set(j, temp);
                }
            }
        }

        return vPlayLists;

    }



}



///create_playlist my_playlist

//add_to_playlist my_PLAYlist amazing_cats_video_id

//remove_from_playlist my_playLIST amazing_cats_video_id