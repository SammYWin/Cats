package com.evantemplate.cats.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cat(

    val id: String,
    @Json(name = "url")val imgUrl: String
): Parcelable {}