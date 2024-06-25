package com.millerr.bmisideprojectapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BmiViewModel : ViewModel() {
    var bmiResult = MutableLiveData<Float>()
    var status = MutableLiveData<BmiStatus>()

    init {
        bmiResult.value = 0F
        status.value = BmiStatus.INIT
    }

    fun calBmi(weight: Float, height: Float) {
        var bmi = weight / (height * height)
        bmiResult.value = bmi
        status.value = when (bmi) {
            in 0F..<18.5F -> BmiStatus.過輕
            in 18.5F..< 24F -> BmiStatus.健康體重
            in 24F..< 27F -> BmiStatus.過重
            in 27F..< 30F -> BmiStatus.輕度肥胖
            in 30F..< 35F -> BmiStatus.中度肥胖
            else -> BmiStatus.重度肥胖
        }
    }
}

enum class BmiStatus {
    INIT, 過輕, 健康體重, 過重, 輕度肥胖, 中度肥胖, 重度肥胖
}