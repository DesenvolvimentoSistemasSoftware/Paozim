package com.mobile.paozim.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CartViewModel : ViewModel() {
    val triggerUpdateInfo = MutableLiveData<Boolean>()
}
