package com.google;

import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;

  private boolean isVideoPlaying = false;
  private Video currentVideoPlaying;

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {

    List<Video> videos = videoLibrary.getVideoSort();
    System.out.println("Here's a list of all available videos:");

   for(Video video: videos){
     String title =  video.getTitle();
     String id = video.getVideoId();
     List<String> tags = video.getTags();
     String flagged = video.getFlag() == null? "": " - FLAGGED (reason: "+video.getFlag()+")";

     System.out.println(title+" "+"(" + id + ") "+ (tags.size()>0? tags: "")+flagged);
   }

  }

  public void playVideo(String videoId) {

    Video video = videoLibrary.getVideo(videoId);

    if(video.getFlag() != null){
      System.out.println("Cannot play video: Video is currently flagged (reason: "+video.getFlag()+")");
      return;
    }

    if(this.currentVideoPlaying != null && video != null){

      System.out.println("Stopping video: "+currentVideoPlaying.getTitle());
      System.out.println("Playing video: "+ video.getTitle());
      currentVideoPlaying = video;
      this.isVideoPlaying = true;

    }

    if(video == null){
      System.out.println("Cannot play video: Video does not exist");
    }

    if(this.currentVideoPlaying == null && video != null){
      System.out.println("Playing video: "+ video.getTitle());
      currentVideoPlaying = video;
      this.isVideoPlaying = true;
    }


  }

  public void stopVideo() {

    if(this.currentVideoPlaying == null){
      System.out.println("Cannot stop video: No video is currently playing");
    } else{
      System.out.println("Stopping video: "+this.currentVideoPlaying.getTitle());
      this.isVideoPlaying  = false;
      this.currentVideoPlaying = null;
    }




  }

  public void playRandomVideo() {
    Random rand = new Random();


    List<Video> newListNotFlaggedVideo = new ArrayList<>(videoLibrary.getVideos().size());

    for(Video v: videoLibrary.getVideos()){
      if(v.getFlag() == null)
        newListNotFlaggedVideo.add(v);
    }

    if(newListNotFlaggedVideo.size() == 0){
      System.out.println("No videos available");
      return;
    }

    int rand_number = rand.nextInt(newListNotFlaggedVideo.size());
    Video newRandomVideo = newListNotFlaggedVideo.get(rand_number);

    if(videoLibrary.getVideos().size() == 0){
      System.out.println("No video available");
    }

    if(this.isVideoPlaying){
      System.out.println("Stopping video: "+this.currentVideoPlaying.getTitle());
      this.currentVideoPlaying = newRandomVideo;
    }
    System.out.println("Playing video: "+newRandomVideo.getTitle());
    this.currentVideoPlaying = newRandomVideo;
    this.isVideoPlaying = true;

  }

  public void pauseVideo() {
    if(!isVideoPlaying && this.currentVideoPlaying !=null){
      System.out.println("Video already paused: "+this.currentVideoPlaying.getTitle());

    }
    if(this.currentVideoPlaying == null){
      System.out.println("Cannot pause video: No video is currently playing");
    }

    if(isVideoPlaying && this.currentVideoPlaying != null){
      System.out.println("Pausing video: "+ this.currentVideoPlaying.getTitle());
      this.isVideoPlaying = false;

    }

  }


  public void continueVideo() {

    if(this.isVideoPlaying){
      System.out.println("Cannot continue video: Video is not paused");
    }

    if(this.currentVideoPlaying == null){
      System.out.println("Cannot continue video: No video is currently playing");
    }

    if(!this.isVideoPlaying && this.currentVideoPlaying != null){

      System.out.println("Continuing video: "+this.currentVideoPlaying.getTitle());
      this.isVideoPlaying = true;

    }


  }

  public void showPlaying() {

    if(this.currentVideoPlaying == null){
      System.out.println("No video is currently playing");
      return;
    }

      String title = currentVideoPlaying.getTitle();
      String id = currentVideoPlaying.getVideoId();
      List<String> tags = currentVideoPlaying.getTags();


    if(this.currentVideoPlaying != null && this.isVideoPlaying){
      System.out.println("Currently playing: "+title+" "+"("+id+") "+(tags.size()>0? tags: ""));
    }
    if(!this.isVideoPlaying && this.currentVideoPlaying != null){
      System.out.println("Currently playing: "+title+" "+"("+id+") "+(tags.size()>0? tags: "")+" - PAUSED");
    }


  }

  public void createPlaylist(String playlistName) {


      VideoPlaylist playlist = new VideoPlaylist();
      if(playlist.setName(playlistName)) {
        System.out.println("Successfully created new playlist: "+ playlist.getNamePlaylist());
      }else{
        System.out.println("Cannot create playlist: A playlist with the same name already exists");
      }

  }



  public void addVideoToPlaylist(String playlistName, String videoId) {

    Video v = videoLibrary.getVideo(videoId);

     VideoPlaylist vPlaylist = VideoPlaylist.getPlayListByName(playlistName);

    if(v.getFlag() != null){
      System.out.println("Cannot add video to "+playlistName+": Video is currently flagged (reason: "+v.getFlag()+")");
      return;
    }

    if(vPlaylist == null){
        System.out.println("Cannot add video to "+playlistName+": Playlist does not exist");
        return;
      }
      if(v == null){
        System.out.println("Cannot add video to "+playlistName+": Video does not exist");
        return;
      }

      if(VideoPlaylist.getPlayListByName(playlistName).checkVideoExists(v)){
        System.out.println("Cannot add video to "+playlistName+": Video already added");
      }else{
          vPlaylist.addVideo(v);
          System.out.println("Added video to "+playlistName+": "+v.getTitle());
      }

  }

  public void showAllPlaylists() {
    if(VideoPlaylist.getAllPlayListsSort().size() == 0){
      System.out.println("No playlists exist yet");
      return;
    }

    System.out.println("Showing all playlists:");
    int counter = 1;
    for(VideoPlaylist vPlayList: VideoPlaylist.getAllPlayListsSort()){
      System.out.println(counter+") "+vPlayList.getNamePlaylist());
      counter++;
    }

  }

  public void showPlaylist(String playlistName) {
   VideoPlaylist vPlayList = VideoPlaylist.getPlayListByName(playlistName);
   if(vPlayList == null){
     System.out.println("Cannot show playlist "+playlistName+": Playlist does not exist");
     return;
   }
    System.out.println("Showing playlist: "+playlistName);

   if(vPlayList.getPlayListVideos().size() == 0){
     System.out.println("  No videos here yet");
     return;
   }

   for(Video v:  vPlayList.getPlayListVideos()){
     String flagged = v.getFlag() == null? "": " - FLAGGED (reason: "+v.getFlag()+")";
     System.out.println(" "+v.getTitle()+" ("+v.getVideoId()+") "+ v.getTags()+flagged);
   }

  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    VideoPlaylist vPlayList = VideoPlaylist.getPlayListByName(playlistName);
    Video v = videoLibrary.getVideo(videoId);
    if(vPlayList == null){
      System.out.println("Cannot remove video from "+playlistName+": Playlist does not exist");
      return;
    }

    if(v ==null){
      System.out.println("Cannot remove video from "+playlistName+": Video does not exist");
      return;
    }

    if(!vPlayList.checkVideoExists(v)){
      System.out.println("Cannot remove video from "+playlistName+": Video is not in playlist");
      return;
    }

    vPlayList.removeVideo(videoId);
    System.out.println("Removed video from "+playlistName+": "+v.getTitle());

  }

  public void clearPlaylist(String playlistName) {
    VideoPlaylist vPlayList = VideoPlaylist.getPlayListByName(playlistName);
    if(vPlayList == null){
      System.out.println("Cannot clear playlist "+playlistName+": Playlist does not exist");
      return;
    }

    vPlayList.clearPlayList();
    System.out.println("Successfully removed all videos from "+playlistName);

  }

  public void deletePlaylist(String playlistName) {
    VideoPlaylist vPlayList = VideoPlaylist.getPlayListByName(playlistName);
    if(vPlayList == null){
      System.out.println("Cannot delete playlist "+playlistName+": Playlist does not exist");
      return;
    }

    vPlayList.removePlayList(playlistName);
    System.out.println("Deleted playlist: "+playlistName);

  }


  public void searchVideos(String searchTerm) {
    searchTerm.replaceAll("\\s+","");
    List<Video> videosContainingTerm;

    videosContainingTerm =  searchByTagOrName(videoLibrary, searchTerm);

     if(videosContainingTerm.size() == 0){
       System.out.println("No search results for "+searchTerm);
       return;
     }

      System.out.println("Here are the results for "+searchTerm+":");

     int numberOfResult = 0;

     for(int i = 0; i < videosContainingTerm.size();i++){
       Video v = videosContainingTerm.get(i);
       if(v.getFlag() == null) {
         System.out.println(numberOfResult+ 1 + ") " + v.getTitle() + " (" + v.getVideoId() + ") " + v.getTags());
         numberOfResult++;
       }

     }


    Scanner scanner = new Scanner(System.in);

    System.out.println("Would you like to play any of the above? If yes, specify the number of the video.\n" +
            "If your answer is not a valid number, we will assume it's a no.");

    int userChoice = 0;
    try{
      userChoice = scanner.nextInt();

    }catch (Exception e){
      return;
    }

    if(userChoice <= videosContainingTerm.size() && userChoice != 0) {
      System.out.println("Playing video: " + videosContainingTerm.get(userChoice - 1).getTitle());
      this.currentVideoPlaying = videosContainingTerm.get(userChoice - 1);
      this.isVideoPlaying = true;
    }

  }

  public void searchVideosWithTag(String videoTag) {
    //it call the searchVidoes method which can handel tags or name to search for the video
    this.searchVideos(videoTag);

  }

  public void flagVideo(String videoId) {
    Video video = videoLibrary.getVideo(videoId);
    if(video == null){
      System.out.println("Cannot flag video: Video does not exist");
      return;
    }

    if(video.getFlag() != null){
      System.out.println("Cannot flag video: Video is already flagged");
      return;
    }

    video.setFlag();
    this.checkIfPlayIngVideoIsFlagged(video);
    System.out.println("Successfully flagged video: "+video.getTitle()+" (reason: "+video.getFlag()+")");



  }

  public void flagVideo(String videoId, String reason) {
    Video video = videoLibrary.getVideo(videoId);
    if(video == null){
      System.out.println("Cannot flag video: Video does not exist");
      return;
    }

    if(video.getFlag() != null){
      System.out.println("Cannot flag video: Video is already flagged");
      return;
    }

    reason =  reason.replaceAll("\\s", "");
    video.setFlag(reason);
    this.checkIfPlayIngVideoIsFlagged(video);
    System.out.println("Successfully flagged video: "+video.getTitle()+" (reason: "+video.getFlag()+")");
  }

  public void allowVideo(String videoId) {
    Video video = videoLibrary.getVideo(videoId);
    if(video == null){
      System.out.println("Cannot remove flag from video: Video does not exist");
      return;
    }

    if(video.getFlag() == null){
      System.out.println("Cannot remove flag from video: Video is not flagged");
      return;
    }

    video.removeFlag();
    System.out.println("Successfully removed flag from video: "+video.getTitle());


  }





  //helper methods
//method that helps to find video by search term using tags or name

  public List<Video> searchByTagOrName(VideoLibrary vLibrary, String searchTerm){
    List<Video> videosContainingTerm = new ArrayList<>(vLibrary.getVideos().size());
//if the search term hash a # it will run the first condition and search by tag
//or in it will search by name
    if(searchTerm.contains("#")){

      searchTerm = searchTerm.substring(1);
      for(Video v: vLibrary.getVideoSort()){
        List<String> tags = v.getTags();

        for(String tag: tags) {
          if (tag.toLowerCase().contains(searchTerm.toLowerCase()))
            videosContainingTerm.add(v);
        }
      }

    }else{
      for(Video v: vLibrary.getVideoSort()){
        String title = v.getTitle().toLowerCase();

        if(title.contains(searchTerm)){
          videosContainingTerm.add(v);
        }
      }

    }
    return videosContainingTerm;
  }


  void checkIfPlayIngVideoIsFlagged(Video v){
    if(currentVideoPlaying == null)
      return;
    if(v.getTitle().equals(currentVideoPlaying.getTitle())) {
      currentVideoPlaying = null;
      isVideoPlaying = false;
      System.out.println("Stopping video: "+v.getTitle());
    }

  }







}

