<?php

namespace App\Filament\Resources\MakananResource\Pages;

use Filament\Actions;
use GuzzleHttp\Client;
use Filament\Resources\Pages\CreateRecord;
use App\Filament\Resources\MakananResource;

class CreateMakanan extends CreateRecord
{
    protected static string $resource = MakananResource::class;
    public static function afterCreate($record)
    {
        $client = new Client();
        $response = $client->post('http://localhost:3000/add-food', [
            'json' => [
                'nama_toko' => $record->nama_toko,
                'alamat' => $record->alamat,
                'deskripsi' => $record->deskripsi,
                'image' => $record->image,
                'no_wa' => $record->no_wa,
                'jam_operasional' => $record->jam_operasional,
                'harga' => $record->harga,
            ]
        ]);

        if ($response->getStatusCode() !== 201) {
            throw new \Exception('Failed to add food in Node.js API');
        }
    }
}
