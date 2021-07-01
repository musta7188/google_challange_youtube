package com.google;

import java.util.Collections;
import java.util.List;

/** A class used to represent a video. */
class Video {

  private final String title;
  private final String videoId;
  private final List<String> tags;
  private String flag;

  Video(String title, String videoId, List<String> tags) {
    this.title = title;
    this.videoId = videoId;
    this.tags = Collections.unmodifiableList(tags);
  }

  /** Returns the title of the video. */
  String getTitle() {
    return title;
  }

  /** Returns the video id of the video. */
  String getVideoId() {
    return videoId;
  }

  //if argument is provided
  void setFlag(String reason){
    flag = reason;
  }
  //if no reason is provided
  void setFlag(){
    this.setFlag("Not supplied");
  }

  String getFlag(){
    return this.flag;
  }

  void removeFlag(){
    this.flag = null;
  }


  /** Returns a readonly collection of the tags of the video. */
  List<String> getTags() {

    String tagsString = "";

    for(String tag: tags){

      tagsString = tagsString.length() == 0?tagsString+tag : tagsString+" "+tag;
    }
    return Collections.singletonList(tagsString);
  }

}
