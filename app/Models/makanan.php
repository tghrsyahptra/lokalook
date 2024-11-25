<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Facades\Storage;

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

    public function getImageUrlAttribute()
    {
        if ($this->image) {
            return Storage::disk('public')->url($this->image);
        }
        return null;
    }
}
