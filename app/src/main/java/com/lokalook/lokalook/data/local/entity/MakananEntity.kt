package com.lokalook.lokalook.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "makanan")
data class MakananEntity(
    @PrimaryKey val id: Int, // Maps to "id" from API
    val namaToko: String?, // Maps to "nama_toko"
    val alamat: String?, // Maps to "alamat"
    val deskripsi: String?, // Maps to "deskripsi"
    val image: String?, // Maps to "image"
    val noWa: String?, // Maps to "no_wa"
    val jamOperasional: String?, // Maps to "jam_operasional"
    val harga: String? // Maps to "harga"
) : Parcelable