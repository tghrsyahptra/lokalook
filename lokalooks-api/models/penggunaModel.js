const db = require('../config');

const Pengguna = {
    create: async (data) => {
        const [result] = await db.execute(
            'INSERT INTO penggunas (nama_lengkap, email, alamat, password, username) VALUES (?, ?, ?, ?, ?)',
            [data.nama_lengkap, data.email, data.alamat, data.password, data.username]
        );
        return result.insertId;
    },
    findByEmail: async (email) => {
        const [rows] = await db.execute(
            'SELECT * FROM penggunas WHERE email = ?',
            [email]
        );
        return rows[0];
    },
    // Tambahkan metode lain sesuai kebutuhan
};

module.exports = Pengguna;