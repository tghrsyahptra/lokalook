<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Facades\Hash;
class pengguna extends Model
{
    protected $fillable = [
        'nama_lengkap',
        'email',
        'alamat',
        'password',
        'hasil_personalisasi',
        'username',
        'image_pp',
    ];

    // Setter untuk mengenkripsi password sebelum disimpan ke database
    public function setPasswordAttribute($value)
    {
        $this->attributes['password'] = Hash::make($value);
    }

    // Getter untuk menampilkan password asli (tidak disarankan)
    public function getPasswordAttribute($value)
    {
        return session('plain_password', $value);
    }
}
