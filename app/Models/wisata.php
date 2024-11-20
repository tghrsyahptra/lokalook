<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class wisata extends Model
{
    protected $fillable = [
        'nama_wisata',
        'alamat',
        'deskripsi',
        'image',
        'no_wa',
        'jam_operasional',
        'kategori',
        'harga',
    ];
}
