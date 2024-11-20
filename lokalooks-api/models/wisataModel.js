const db = require('../config');

const Wisata = {
    create: async (data) => {
        const [result] = await db.execute(
            'INSERT INTO wisatas (nama_wisata, alamat, deskripsi, image, no_wa, jam_operasional, kategori, harga) VALUES (?, ?, ?, ?, ?, ?, ?, ?)',
            [data.nama_wisata, data.alamat, data.deskripsi, data.image, data.no_wa, data.jam_operasional, data.kategori, data.harga]
        );
        return result.insertId;
    },
    // Tambahkan metode lain sesuai kebutuhan
};

module.exports = Wisata;