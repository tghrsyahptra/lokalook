<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class pengguna extends Model
{
    protected $fillable = [
        'nama_lengkap',
        'email',
        'alamat',
    ];
}
