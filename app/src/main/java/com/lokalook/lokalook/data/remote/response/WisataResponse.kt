package com.lokalook.lokalook.data.remote.response

import com.google.gson.annotations.SerializedName

data class WisataResponse(
    @SerializedName("wisata")
    val wisata: List<WisataItem>
)

data class WisataItem(
    @SerializedName("id")
    val id: Int,

    @SerializedName("nama_wisata")
    val nama_wisata: String,

    @SerializedName("alamat")
    val alamat: String,

    @SerializedName("deskripsi")
    val deskripsi: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("harga")
    val harga: String,

    @SerializedName("jam_operasional")
    val jam_operasional: String,

    @SerializedName("no_wa")
    val no_wa: String,

    @SerializedName("kategori")
    val kategori: String
)