const db = require('../config');

const Pengguna = {
    create: async (data) => {
        const [result] = await db.execute(
            'INSERT INTO penggunas (nama_lengkap, email, alamat, password, username, hasil_personalisasi, image_pp) VALUES (?, ?, ?, ?, ?, ?, ?)',
            [data.nama_lengkap, data.email, data.alamat, data.password, data.username, data.hasil_personalisasi, data.image_pp]
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
    setPasswordResetToken: async (email, token) => {
        await db.execute(
            'UPDATE penggunas SET password_reset_token = ? WHERE email = ?',
            [token, email]
        );
    },
    findByResetToken: async (token) => {
        const [rows] = await db.execute(
            'SELECT * FROM penggunas WHERE password_reset_token = ?',
            [token]
        );
        return rows[0];
    },
    resetPassword: async (email, newPassword) => {
        await db.execute(
            'UPDATE penggunas SET password = ?, password_reset_token = NULL WHERE email = ?',
            [newPassword, email]
        );
    },
    // Tambahkan metode lain sesuai kebutuhan
};

module.exports = Pengguna;