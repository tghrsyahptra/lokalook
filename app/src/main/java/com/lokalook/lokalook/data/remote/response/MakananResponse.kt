package com.lokalook.lokalook.data.remote.response

import com.google.gson.annotations.SerializedName

data class MakananResponse(
    @SerializedName("makanan")
    val makanan: List<MakananItem>
)

data class MakananItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("nama_toko")
    val namaToko: String,
    @SerializedName("alamat")
    val alamat: String,
    @SerializedName("deskripsi")
    val deskripsi: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("no_wa")
    val noWa: String,
    @SerializedName("jam_operasional")
    val jamOperasional: String,
    @SerializedName("harga")
    val harga: String
)