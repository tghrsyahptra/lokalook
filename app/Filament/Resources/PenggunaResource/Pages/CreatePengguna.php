<?php

namespace App\Filament\Resources\PenggunaResource\Pages;

use Filament\Actions;
use GuzzleHttp\Client;
use Filament\Resources\Pages\CreateRecord;
use App\Filament\Resources\PenggunaResource;

class CreatePengguna extends CreateRecord
{
    protected static string $resource = PenggunaResource::class;
    public static function afterCreate($record)
    {
        $client = new Client();
        $response = $client->post('http://localhost:3000/register', [
            'json' => [
                'nama_lengkap' => $record->nama_lengkap,
                'email' => $record->email,
                'alamat' => $record->alamat,
                'password' => $record->password,
                'username' => $record->username,
            ]
        ]);

        if ($response->getStatusCode() !== 201) {
            throw new \Exception('Failed to register user in Node.js API');
        }
    }
}
