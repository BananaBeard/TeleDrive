package com.kovalenko.teledrive.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Load() {

    var uid: String = ""
    var loadId: String = ""
    var customer: String = ""
    var customerRate: Double = 0.0
    var driverRate: Double = 0.0
    var type: LoadType = LoadType.REEFER
    var pieces: Int = 0
    var shipper: Facility? = null
    var consignee: Facility? = null
    var driver: Driver? = null

    constructor( uid: String = "",
                 loadId: String = "",
                 customer: String = "",
                 customerRate: Double = 0.0,
                 driverRate: Double = 0.0,
                 type: LoadType = LoadType.REEFER,
                 pieces: Int = 0,
                 shipper: Facility? = null,
                 consignee: Facility? = null,
                 driver: Driver? = null) : this()

    fun toMap(): HashMap<String, Any?> {
        var result = HashMap<String, Any?>()

        result["uid"] = uid
        result["loadId"] = loadId
        result["customer"] = customer
        result["customerRate"] = customerRate
        result["driverRate"] = driverRate
        result["type"] = type
        result["pieces"] = pieces

        result["shipper"] = shipper
        result["consignee"] = consignee
        result["driver"] = driver

        return result
    }
}