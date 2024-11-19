exports.createWisata = async (request, h) => {
    const { nama_wisata, alamat, deskripsi, image, no_wa, jam_operasional, kategori, harga } = request.payload;

    const [result] = await request.server.app.db.execute(
        'INSERT INTO wisatas (nama_wisata, alamat, deskripsi, image, no_wa, jam_operasional, kategori, harga) VALUES (?, ?, ?, ?, ?, ?, ?, ?)',
        [nama_wisata, alamat, deskripsi, image, no_wa, jam_operasional, kategori, harga]
    );

    return h.response({ id: result.insertId }).code(201);
};

// Implement other CRUD operations similarly