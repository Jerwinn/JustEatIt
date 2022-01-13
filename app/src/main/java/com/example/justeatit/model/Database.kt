package com.example.justeatit.model

/**
 * @author jerwinesguerra
 *
 * This class is made as a model for retrieving data from the database.
 */
data class Database(
    var reviews: String = "",
    var timeOfVisit: String = "",
    var Rating: Int = 0,
    var address: String =""
)