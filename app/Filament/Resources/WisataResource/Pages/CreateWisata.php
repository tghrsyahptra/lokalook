<?php

namespace App\Filament\Resources\WisataResource\Pages;

use Filament\Actions;
use GuzzleHttp\Client;
use App\Filament\Resources\WisataResource;
use Filament\Resources\Pages\CreateRecord;

class CreateWisata extends CreateRecord
{
    protected static string $resource = WisataResource::class;
    public static function afterCreate($record)
    {
        $client = new Client();
        $response = $client->post('http://localhost:3000/add-wisata', [
            'json' => [
                'nama_wisata' => $record->nama_wisata,
                'alamat' => $record->alamat,
                'deskripsi' => $record->deskripsi,
                'image' => $record->image,
                'no_wa' => $record->no_wa,
                'jam_operasional' => $record->jam_operasional,
                'harga' => $record->harga,
                'kategori' => $record->kategori,
            ]
        ]);

        if ($response->getStatusCode() !== 201) {
            throw new \Exception('Failed to add wisata in Node.js API');
        }
    }
}
