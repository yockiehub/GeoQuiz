package com.android.yockie.geoquiz;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by palme_000 on 02/20/16.
 */
public class TrueFalse implements Parcelable {

    //This is an int because it will hold the ID of the actual quiestion, not the question itself
    private int mQuestion;
    private boolean mTrueQuestion;
    private boolean mCheatedThisQuestion;

    public TrueFalse(int question, boolean trueQuestion, boolean cheatedThisQuestion){
        mQuestion = question;
        mTrueQuestion = trueQuestion;
        mCheatedThisQuestion = cheatedThisQuestion;
    }

    public TrueFalse(Parcel in){
        readFromParcel(in);
    }

    public int getmQuestion() {
        return mQuestion;
    }

    public void setmQuestion(int mQuestion) {
        this.mQuestion = mQuestion;
    }

    public boolean ismTrueQuestion() {
        return mTrueQuestion;
    }

    public void setmTrueQuestion(boolean mTrueQuestion) {
        this.mTrueQuestion = mTrueQuestion;
    }

    public boolean getmCheatedThisQuestion(){
        return mCheatedThisQuestion;
    }

    public void setmCheatedThisQuestion(boolean mCheatedThisQuestion){
        this.mCheatedThisQuestion = mCheatedThisQuestion;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //boolean[]myBool = {mCheatedThisQuestion};
        //dest.writeBooleanArray(new boolean[] {mCheatedThisQuestion});
        dest.writeInt(mCheatedThisQuestion ? 0 : 1 );
        dest.writeInt(mTrueQuestion ? 0 : 1 );
        dest.writeInt(mQuestion);
    }

    public void readFromParcel(Parcel in){
        //mCheatedThisQuestion = in.readBooleanArray(new boolean[1]);
        mCheatedThisQuestion = in.readInt() == 0;
        mTrueQuestion = in.readInt() == 0;
        mQuestion = in.readInt();
    }

    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() {
                public TrueFalse createFromParcel(Parcel in) {
                    return new TrueFalse(in);
                }

                /**
                 * Create a new array of the Parcelable class.
                 *
                 * @param size Size of the array.
                 * @return Returns an array of the Parcelable class, with every entry
                 * initialized to null.
                 */
                @Override
                public TrueFalse[] newArray(int size) {
                    return new TrueFalse[size];
                }
            };
}
