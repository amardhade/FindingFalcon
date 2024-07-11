package com.gt.findingfalcon.model

data class DropDownItem<T : Any>(
    var id: Int = -1, var title: String = "", var subTile: Double = 0.0, var details: T? = null
)
