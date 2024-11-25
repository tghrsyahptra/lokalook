<?php

namespace App\Filament\Resources\MakananResource\Pages;

use App\Filament\Resources\MakananResource;
use Filament\Resources\Pages\CreateRecord;
use GuzzleHttp\Client;
use App\Models\Makanan;
use Filament\Actions\Action;
use Filament\Actions\CreateAction;

class CreateMakanan extends CreateRecord
{
    protected static string $resource = MakananResource::class;

//     protected function afterCreate(): void
// {
//     $record = $this->record;

//     if (!$record->image) {
//         throw new \Exception('Image upload failed.');
//     }

//     try {
//         $client = new Client();
//         $response = $client->post('http://localhost:3000/add-food', [
//             'json' => [
//                 'nama_toko' => $record->nama_toko,
//                 'alamat' => $record->alamat,
//                 'deskripsi' => $record->deskripsi,
//                 'image' => $record->image,
//                 'no_wa' => $record->no_wa,
//                 'jam_operasional' => $record->jam_operasional,
//                 'harga' => $record->harga,
//             ]
//         ]);

//         if ($response->getStatusCode() !== 201) {
//             throw new \Exception('Failed to add food in Node.js API');
//         }
//     } catch (\Exception $e) {
//         // Log error atau tampilkan notification
//         \Filament\Notifications\Notification::make()
//             ->title('Error')
//             ->body('Failed to sync with external API: ' . $e->getMessage())
//             ->danger()
//             ->send();
//     }
// }
}