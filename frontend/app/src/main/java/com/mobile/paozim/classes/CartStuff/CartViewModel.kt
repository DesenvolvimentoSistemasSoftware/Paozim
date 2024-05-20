package com.mobile.paozim.classes.CartStuff

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CartViewModel : ViewModel() {
    val triggerUpdateInfo = MutableLiveData<Boolean>()
}
