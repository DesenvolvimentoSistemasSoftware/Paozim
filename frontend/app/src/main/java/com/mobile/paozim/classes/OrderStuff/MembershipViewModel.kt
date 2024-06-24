package com.mobile.paozim.classes.OrderStuff

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MembershipViewModel: ViewModel() {
    val triggerUpdateInfo = MutableLiveData<Boolean>()
}
