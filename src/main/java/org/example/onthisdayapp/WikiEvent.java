package org.example.onthisdayapp;

public class WikiEvent {
    private String title;
    private String desc;
    private String thumbnail;

    public WikiEvent(String title, String desc, String thumbnail) {
        setTitle(title);
        setDesc(desc);
        setThumbnail(thumbnail);
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "WikiEvent{" +
                "title='" + getTitle() + '\'' +
                ", desc='" + getDesc() + '\'' +
                ", thumbnail='" + getDesc() + '\'' +
                '}';
    }
}
