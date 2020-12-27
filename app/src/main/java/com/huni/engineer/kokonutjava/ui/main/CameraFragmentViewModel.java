package com.huni.engineer.kokonutjava.ui.main;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.huni.engineer.kokonutjava.common.data.DailyFoodInfo;

import java.util.HashMap;
import java.util.List;


public class CameraFragmentViewModel extends AndroidViewModel {
    public static final String TAG = CameraFragmentViewModel.class.getSimpleName();

    public MutableLiveData<List<DailyFoodInfo>> myCurrentData = new MutableLiveData<>();

    private HashMap<Integer, List<DailyFoodInfo>> myItemHash = new HashMap<>();

    //생성자
    public CameraFragmentViewModel(@NonNull Application application) {
        super(application);
    }


    public void makeHash(List<DailyFoodInfo> inputList) {
        
    }


}
