package com.lokalook.lokalook.data.remote.response

import com.google.gson.annotations.SerializedName

data class WisataResponse(
    @SerializedName("wisata")
    val wisata: List<WisataItem>
)

data class WisataItem(
    @SerializedName("id")
    val id: Int, // Ensure that ID is non-nullable as per the API response.

    @SerializedName("nama_wisata")
    val nama_wisata: String, // Non-nullable String field.

    @SerializedName("alamat")
    val alamat: String, // Non-nullable String field.

    @SerializedName("deskripsi")
    val deskripsi: String, // Non-nullable String field.

    @SerializedName("image")
    val image: String, // Non-nullable String field.

    @SerializedName("no_wa")
    val no_wa: String, // Non-nullable String field.

    @SerializedName("jam_operasional")
    val jam_operasional: String, // Non-nullable String field.

    @SerializedName("kategori")
    val kategori: String, // Non-nullable String field.

    @SerializedName("harga")
    val harga: String, // Non-nullable String field.

    @SerializedName("created_at")
    val createdAt: String, // Non-nullable String field.

    @SerializedName("updated_at")
    val updatedAt: String // Non-nullable String field.
)