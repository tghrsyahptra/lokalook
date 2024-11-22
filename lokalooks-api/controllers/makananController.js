const db = require('../config'); // Pastikan ini ada di bagian atas file
const Makanan = require('../models/makananModel');

exports.createMakanan = async (request, h) => {
    try {
        const { nama_toko, alamat, deskripsi, image, no_wa, jam_operasional, harga } = request.payload;
        const id = await Makanan.create({ nama_toko, alamat, deskripsi, image, no_wa, jam_operasional, harga });
        return h.response({ message: 'Data Makanan Berhasil Ditambahkan' }).code(200);
    } catch (error) {
        console.error(error);
        return h.response({ error: 'Internal Server Error' }).code(500);
    }
};

exports.getAllMakanan = async (request, h) => {
    try {
        const [rows] = await db.execute("SELECT * FROM makanans");
        const filteredRows = rows.map((row) => {
            const { created_at, updated_at, ...rest } = row;
            return rest;
        });
        return h.response(filteredRows).code(200);
    } catch (error) {
        console.error(error);
        return h.response({ error: "Internal Server Error", message: error.message }).code(500);
    }
};

exports.updateMakanan = async (request, h) => {
    try {
        const { id } = request.params;
        const { nama_toko, alamat, deskripsi, image, no_wa, jam_operasional, harga } = request.payload;

        const [result] = await db.execute(
            "UPDATE makanans SET nama_toko = ?, alamat = ?, deskripsi = ?, image = ?, no_wa = ?, jam_operasional = ?, harga = ? WHERE id = ?",
            [nama_toko, alamat, deskripsi, image, no_wa, jam_operasional, harga, id]
        );

        if (result.affectedRows === 0) {
            return h.response({ error: 'Makanan Tidak Ditemukan' }).code(404);
        }

        return h.response({ message: 'Makanan Telah Diupdate' }).code(200);
    } catch (error) {
        console.error(error);
        return h.response({ error: 'Internal Server Error', message: error.message }).code(500);
    }
};