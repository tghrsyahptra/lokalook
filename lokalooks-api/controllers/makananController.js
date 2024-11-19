exports.createMakanan = async (request, h) => {
    const { nama_toko, alamat, deskripsi, image, no_wa, jam_operasional, harga } = request.payload;

    const [result] = await request.server.app.db.execute(
        'INSERT INTO makanans (nama_toko, alamat, deskripsi, image, no_wa, jam_operasional, harga) VALUES (?, ?, ?, ?, ?, ?, ?)',
        [nama_toko, alamat, deskripsi, image, no_wa, jam_operasional, harga]
    );

    return h.response({ id: result.insertId }).code(201);
};

// Implement other CRUD operations similarly