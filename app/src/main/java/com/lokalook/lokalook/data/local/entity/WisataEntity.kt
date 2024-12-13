package com.lokalook.lokalook.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "wisata")
data class WisataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?, // Maps directly to "id" from API

    val nama_wisata: String?, // Maps directly to "nama_wisata"
    val alamat: String?, // Maps directly to "alamat"
    val deskripsi: String?, // Maps directly to "deskripsi"
    val image: String?, // Maps directly to "image"
    val no_wa: String?, // Maps directly to "no_wa"
    val jam_operasional: String?, // Maps directly to "jam_operasional"
    val kategori: String?, // Maps directly to "kategori"
    val harga: String?, // Maps directly to "harga"
    val createdAt: String?, // Maps directly to "created_at"
    val updatedAt: String?, // Maps directly to "updated_at"
    var isFavorite: Boolean? = false // Local flag for marking as favorite
) : Parcelable