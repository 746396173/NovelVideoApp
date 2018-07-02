package com.lwlizhe.novelvideoapp.video.entity.jsoup;

import android.os.Parcel;
import android.os.Parcelable;

import com.fcannizzaro.jsoup.annotations.interfaces.Attr;
import com.fcannizzaro.jsoup.annotations.interfaces.Items;
import com.fcannizzaro.jsoup.annotations.interfaces.Selector;
import com.fcannizzaro.jsoup.annotations.interfaces.Text;

import java.util.List;

/**
 * @author Rabtman 追番信息
 */
@Selector("div.update div.update_list ul[class~=week?]")
public class ScheduleWeek implements Parcelable {

  public static final Creator<ScheduleWeek> CREATOR = new Creator<ScheduleWeek>() {
    @Override
    public ScheduleWeek createFromParcel(Parcel source) {
      return new ScheduleWeek(source);
    }

    @Override
    public ScheduleWeek[] newArray(int size) {
      return new ScheduleWeek[size];
    }
  };
  @Items
  private List<ScheduleItem> scheduleItems;

  public ScheduleWeek() {
  }

  protected ScheduleWeek(Parcel in) {
    this.scheduleItems = in.createTypedArrayList(ScheduleItem.CREATOR);
  }

  public List<ScheduleItem> getScheduleItems() {
    return scheduleItems;
  }

  public void setScheduleItems(List<ScheduleItem> scheduleItems) {
    this.scheduleItems = scheduleItems;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeTypedList(this.scheduleItems);
  }

  @Override
  public String toString() {
    return "ScheduleWeek{" +
        "scheduleItems=" + scheduleItems +
        '}';
  }

  @Selector("li")
  public static class ScheduleItem implements Parcelable {

    public static final Creator<ScheduleItem> CREATOR = new Creator<ScheduleItem>() {
      @Override
      public ScheduleItem createFromParcel(Parcel source) {
        return new ScheduleItem(source);
      }

      @Override
      public ScheduleItem[] newArray(int size) {
        return new ScheduleItem[size];
      }
    };
    @Text("a")
    private String name;
    @Text("span a")
    private String episode;
    @Attr(query = "a", attr = "href")
    private String animeLink;
    @Attr(query = "span a", attr = "href")
    private String episodeLink;

    public ScheduleItem() {
    }

    protected ScheduleItem(Parcel in) {
      this.name = in.readString();
      this.episode = in.readString();
      this.animeLink = in.readString();
      this.episodeLink = in.readString();
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getEpisode() {
      return episode;
    }

    public void setEpisode(String episode) {
      this.episode = episode;
    }

    public String getAnimeLink() {
      return animeLink;
    }

    public void setAnimeLink(String animeLink) {
      this.animeLink = animeLink;
    }

    public String getEpisodeLink() {
      return episodeLink;
    }

    public void setEpisodeLink(String episodeLink) {
      this.episodeLink = episodeLink;
    }

    @Override
    public int describeContents() {
      return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(this.name);
      dest.writeString(this.episode);
      dest.writeString(this.animeLink);
      dest.writeString(this.episodeLink);
    }

    @Override
    public String toString() {
      return "ScheduleItem{" +
          "name='" + name + '\'' +
          ", episode='" + episode + '\'' +
          ", animeLink='" + animeLink + '\'' +
          ", episodeLink='" + episodeLink + '\'' +
          '}';
    }
  }
}
