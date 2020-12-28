package com.huni.engineer.kokonutjava.ui.main;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.huni.engineer.kokonutjava.common.data.DailyFoodInfo;

import java.util.HashMap;
import java.util.List;


public class CameraFragmentViewModel extends AndroidViewModel {
    public static final String TAG = CameraFragmentViewModel.class.getSimpleName();

    public MutableLiveData<List<DailyFoodInfo>> myCurrentData = new MutableLiveData<>();

    private HashMap<String, DailyFoodInfo> myItemHash = new HashMap<>();

    public MutableLiveData<HashMap<String, DailyFoodInfo>> myAllData = new MutableLiveData<>();

    //생성자
    public CameraFragmentViewModel(@NonNull Application application) {
        super(application);
    }


    public void makeHash(List<DailyFoodInfo> inputList) {
        myItemHash = new HashMap<>();

        for (DailyFoodInfo test : inputList) {
            myItemHash.put(test.getDate() + "-" + test.getConsumeTime(), test);
        }

        Log.d(TAG, "test : " + myItemHash.toString());

        myAllData = new MutableLiveData<>();

        myAllData.postValue(myItemHash);
    }


}
