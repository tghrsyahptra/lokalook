exports.createWisata = async (request, h) => {
    try {
        const { nama_wisata, alamat, deskripsi, image, no_wa, jam_operasional, kategori, harga } = request.payload;

        const [result] = await request.server.app.db.execute(
            'INSERT INTO wisatas (nama_wisata, alamat, deskripsi, image, no_wa, jam_operasional, kategori, harga) VALUES (?, ?, ?, ?, ?, ?, ?, ?)',
            [nama_wisata, alamat, deskripsi, image, no_wa, jam_operasional, kategori, harga]
        );

        return h.response({ message: 'Data Wisata Berhasil Ditambahkan' }).code(201);
    } catch (error) {
        console.error(error);
        return h.response({ error: 'Internal Server Error' }).code(500);
    }
};

exports.getAllWisata = async (request, h) => {
    try {
        const [rows] = await request.server.app.db.execute('SELECT * FROM wisatas');
        const filteredRows = rows.map(row => {
            const { created_at, updated_at, ...rest } = row;
            return rest;
        });
        return h.response(filteredRows).code(200);
    } catch (error) {
        console.error(error);
        return h.response({ error: 'Internal Server Error' }).code(500);
    }
};

exports.updateWisata = async (request, h) => {
    try {
        const { id } = request.params;
        const { nama_wisata, alamat, deskripsi, image, no_wa, jam_operasional, kategori, harga } = request.payload;

        const [result] = await request.server.app.db.execute(
            'UPDATE wisatas SET nama_wisata = ?, alamat = ?, deskripsi = ?, image = ?, no_wa = ?, jam_operasional = ?, kategori = ?, harga = ? WHERE id = ?',
            [nama_wisata, alamat, deskripsi, image, no_wa, jam_operasional, kategori, harga, id]
        );

        if (result.affectedRows === 0) {
            return h.response({ message: 'Data Wisata Tidak Ditemukan' }).code(404);
        }

        return h.response({ message: 'Data Wisata Berhasil Diperbarui' }).code(200);
    } catch (error) {
        console.error(error);
        return h.response({ error: 'Internal Server Error' }).code(500);
    }
};