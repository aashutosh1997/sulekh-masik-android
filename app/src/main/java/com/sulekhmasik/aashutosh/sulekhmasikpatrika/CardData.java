package com.sulekhmasik.aashutosh.sulekhmasikpatrika;

import android.os.Parcel;
import android.os.Parcelable;

public class CardData implements Parcelable{
    String title;
    int id;
    String img_url;
    String exc;
    int img_id;
    String content;
    public CardData(String t,int i,String e,String media,int img, String cont){
        this.title = t;
        this.id = i;
        this.exc = e;
        this.img_url = media;
        this.img_id = img;
        this.content = cont;
    }
    protected CardData(Parcel in){
        this.title = in.readString();
        this.id = in.readInt();
        this.img_url = in.readString();
        this.exc = in.readString();
        this.img_id = in.readInt();
        this.content = in.readString();
    }
    public String getTitle(){
        return title;
    }
    public int getId(){
        return id;
    }
    public String getImg(){
        return img_url;
    }
    public String getExcerpt(){
        return exc;
    }
    public int getImgId(){
        return img_id;
    }
    public String getContent(){
        return content;
    }

    @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel out,int flags){
        out.writeString(title);
        out.writeInt(id);
        out.writeString(img_url);
        out.writeString(exc);
        out.writeInt(img_id);
        out.writeString(content);
    }

    public static final Parcelable.Creator<CardData> CREATOR = new Parcelable.Creator<CardData>(){
        public CardData createFromParcel(Parcel in){
            return new CardData(in);
        }

        @Override
        public CardData[] newArray(int i) {
            return new CardData[i];
        }
    };
}
