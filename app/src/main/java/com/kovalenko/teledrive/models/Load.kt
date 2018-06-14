package com.kovalenko.teledrive.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Load(
        var loadId: String = "",
        var customer: String = "",
        var customerRate: Double = 0.0,
        var driverRate: Double = 0.0,
        var type: LoadType = LoadType.REEFER,
        var pieces: Int = 0,
        var shipper: String = "",
        var consignee: String = "",
        var driver: String = "",
        var truck: String = ""
)