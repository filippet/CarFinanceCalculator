package com.ntangent.carfinancecalculator.widget;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.Preference;
import android.support.v4.os.ParcelableCompat;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.ViewGroup;

//TODO: delete this file
// This is just the code from the YouTube talk:
//      Deep Dive Into State Restoration (en) - Cyril Mottier, Capitaine Train - Droidcon Paris 2014
//      YouTube : https://www.youtube.com/watch?v=ekN2zvFytZk
//      Slides  : https://cyrilmottier.com/2014/09/25/deep-dive-into-android-state-restoration/
//
public class MyView extends ViewGroup {

    private int myValue;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public Parcelable onSaveInstanceState() {
        final Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.childrenStates = new SparseArray<Parcelable>();
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).saveHierarchyState(ss.childrenStates);
        }
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).restoreHierarchyState(ss.childrenStates);
        }
    }

    @Override
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        dispatchFreezeSelfOnly(container);
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        dispatchThawSelfOnly(container);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    static class SavedState extends Preference.BaseSavedState {

        SparseArray childrenStates;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel source, ClassLoader loader) {
            super(source);
            childrenStates = source.readSparseArray(loader);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags );
            dest.writeSparseArray(childrenStates);
        }


        public static final Creator<SavedState> CREATOR = ParcelableCompat
                .newCreator(new ParcelableCompatCreatorCallbacks<SavedState>() {
                                @Override
                                public SavedState createFromParcel(Parcel in, ClassLoader loader) {
                                    return new SavedState(in, loader);
                                }

                                @Override
                                public SavedState[] newArray(int size) {
                                    return new SavedState[size];
                                }
                            });
    }
}
