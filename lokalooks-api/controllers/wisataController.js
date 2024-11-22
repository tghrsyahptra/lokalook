const db = require('../config'); // Pastikan ini ada di bagian atas file
const Wisata = require('../models/wisataModel');

exports.createWisata = async (request, h) => {
    try {
        const { nama_wisata, alamat, deskripsi, image, no_wa, jam_operasional, kategori, harga } = request.payload;

        const [result] = await db.execute(
            'INSERT INTO wisatas (nama_wisata, alamat, deskripsi, image, no_wa, jam_operasional, kategori, harga) VALUES (?, ?, ?, ?, ?, ?, ?, ?)',
            [nama_wisata, alamat, deskripsi, image, no_wa, jam_operasional, kategori, harga]
        );

        return h.response({ message: 'Data Wisata Berhasil Ditambahkan' }).code(201);
    } catch (error) {
        console.error(error);
        return h.response({ error: 'Internal Server Error', message: error.message }).code(500);
    }
};

exports.getAllWisata = async (request, h) => {
    try {
        const [rows] = await db.execute('SELECT * FROM wisatas');
        return h.response(rows).code(200);
    } catch (error) {
        console.error(error);
        return h.response({ error: 'Internal Server Error', message: error.message }).code(500);
    }
};

exports.updateWisata = async (request, h) => {
    try {
        const { id } = request.params;
        const { nama_wisata, alamat, deskripsi, image, no_wa, jam_operasional, kategori, harga } = request.payload;

        const [result] = await db.execute(
            'UPDATE wisatas SET nama_wisata = ?, alamat = ?, deskripsi = ?, image = ?, no_wa = ?, jam_operasional = ?, kategori = ?, harga = ? WHERE id = ?',
            [nama_wisata, alamat, deskripsi, image, no_wa, jam_operasional, kategori, harga, id]
        );

        if (result.affectedRows === 0) {
            return h.response({ error: 'Wisata not found' }).code(404);
        }

        return h.response({ message: 'Wisata updated successfully' }).code(200);
    } catch (error) {
        console.error(error);
        return h.response({ error: 'Internal Server Error', message: error.message }).code(500);
    }
};