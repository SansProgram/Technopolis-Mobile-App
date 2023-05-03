package com.example.technopolis_mobileapp.ui.Apply;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ApplyViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ApplyViewModel() {
        mText = new MutableLiveData<>();
       mText.setValue("This is Apply fragment");
    }

   public LiveData<String> getText() {
        return mText;
   }


}