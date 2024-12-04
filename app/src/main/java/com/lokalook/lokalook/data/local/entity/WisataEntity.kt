package com.lokalook.lokalook.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "wisata")
data class WisataEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val nama_wisata: String?, // Maps to "nama_wisata" from API
    val alamat: String?, // Maps to "alamat"
    val deskripsi: String?, // Maps to "deskripsi"
    val image: String?, // Maps to "image"
    val no_wa: String?, // Maps to "no_wa"
    val jam_operasional: String?, // Maps to "jam_operasional"
    val kategori: String?, // Maps to "kategori"
    val harga: String?, // Maps to "harga"
    var isFavorite: Boolean? = false // Local flag for marking as favorite
) : Parcelable