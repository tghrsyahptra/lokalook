const db = require('../config');

const Makanan = {
    create: async (data) => {
        const [result] = await db.execute(
            'INSERT INTO makanans (nama_toko, alamat, deskripsi, image, no_wa, jam_operasional, harga) VALUES (?, ?, ?, ?, ?, ?, ?)',
            [data.nama_toko, data.alamat, data.deskripsi, data.image, data.no_wa, data.jam_operasional, data.harga]
        );
        return result.insertId;
    },
    // Tambahkan metode lain sesuai kebutuhan
};

module.exports = Makanan;