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
        const filteredRows = rows.map((row) => {
            const { created_at, updated_at, ...rest } = row;
            rest.image = `http://127.0.0.1:8000/storage/${rest.image}`;
            return rest;
        });
        return h.response(filteredRows).code(200);
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

const axios = require('axios');

exports.recommendDestinations = async (request, h) => {
    try {
        const token = request.headers.authorization;
        const { budget, people, keyword } = request.payload; // Assuming budget, people, and keyword are passed in the request payload

        const response = await axios.get('http://localhost:3000/get-wisata', {
            headers: {
                Authorization: token
            }
        });
        const wisataData = response.data;

        const suitableDestinations = [];
        for (const wisata of wisataData) {
            const totalCost = wisata.harga * people;
            if (totalCost <= budget) {
                if (!keyword || wisata.nama_wisata.toLowerCase().includes(keyword.toLowerCase()) || wisata.deskripsi.toLowerCase().includes(keyword.toLowerCase())) {
                    suitableDestinations.push({
                        nama_wisata: wisata.nama_wisata,
                        alamat: wisata.alamat,
                        deskripsi: wisata.deskripsi,
                        image: `http://127.0.0.1:8000/storage/foto_wisata/${wisata.image}`,
                        no_wa: wisata.no_wa,
                        jam_operasional: wisata.jam_operasional,
                        kategori: wisata.kategori,
                        harga: wisata.harga,
                        total_cost: totalCost
                    });
                }
            }
        }

        if (suitableDestinations.length === 0) {
            return h.response({ message: 'Maaf, tidak ada destinasi yang sesuai dengan anggaran Anda.' }).code(200);
        }

        const sortedDestinations = suitableDestinations.sort((a, b) => a.total_cost - b.total_cost);

        return h.response(sortedDestinations).code(200);
    } catch (error) {
        console.error(error);
        return h.response({ error: 'Internal Server Error', message: error.message }).code(500);
    }
};