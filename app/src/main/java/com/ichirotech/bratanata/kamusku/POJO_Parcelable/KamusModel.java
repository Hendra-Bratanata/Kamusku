package com.ichirotech.bratanata.kamusku.POJO_Parcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class KamusModel implements Parcelable {
    private int id;
    private String abjad;
    private String desc;

    public KamusModel() {
    }

    public KamusModel(int id, String abjad, String desc) {
        this.id = id;
        this.abjad = abjad;
        this.desc = desc;
    }

    public KamusModel(String abjad, String desc) {
        this.abjad = abjad;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAbjad() {
        return abjad;
    }

    public void setAbjad(String abjad) {
        this.abjad = abjad;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.abjad);
        dest.writeString(this.desc);
    }

    protected KamusModel(Parcel in) {
        this.id = in.readInt();
        this.abjad = in.readString();
        this.desc = in.readString();
    }

    public static final Parcelable.Creator<KamusModel> CREATOR = new Parcelable.Creator<KamusModel>() {
        @Override
        public KamusModel createFromParcel(Parcel source) {
            return new KamusModel(source);
        }

        @Override
        public KamusModel[] newArray(int size) {
            return new KamusModel[size];
        }
    };
}
