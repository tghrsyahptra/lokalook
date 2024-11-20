<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class makanan extends Model
{
    protected $fillable = [
        'nama_toko',
        'alamat',
        'deskripsi',
        'image',
        'no_wa',
        'jam_operasional',
        'harga',
    ];
}
