<?php

namespace App\Filament\Widgets;

use Filament\Widgets\StatsOverviewWidget as BaseWidget;
use Filament\Widgets\StatsOverviewWidget\Stat;
use App\Models\wisata;
use App\Models\makanan;
use App\Models\pengguna;

class overallDashboard extends BaseWidget
{
    protected static bool $isLazy = false;
    protected function getStats(): array
    {
        $totalWisata = wisata::count();
        $totalMakanan = makanan::count();
        $totalPengguna = pengguna::count();

        return [
            Stat::make('Total Merchant Wisata', $totalWisata),
            Stat::make('Total Merchant Makanan', $totalMakanan),
            Stat::make('Total Pengguna', $totalPengguna),
        ];
    }
}
