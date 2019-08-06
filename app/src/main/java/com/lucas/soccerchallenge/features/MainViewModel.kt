package com.lucas.soccerchallenge.features

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucas.soccerchallenge.data.model.Competition
import javax.inject.Inject

class MainViewModel @Inject
constructor() : ViewModel() {

    val filter = MutableLiveData<HashSet<Competition>>()
}
