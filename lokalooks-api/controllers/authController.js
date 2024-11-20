const bcrypt = require('bcryptjs');
const Pengguna = require('../models/penggunaModel');

exports.register = async (request, h) => {
    const { nama_lengkap, email, alamat, password, username } = request.payload;
    const hashedPassword = await bcrypt.hash(password, 10);

    const id = await Pengguna.create({
        nama_lengkap,
        email,
        alamat,
        password: hashedPassword,
        username
    });

    return h.response({ id }).code(201);
};

exports.login = async (request, h) => {
    const { email, password } = request.payload;

    const user = await Pengguna.findByEmail(email);

    if (!user) {
        return h.response({ error: 'User not found' }).code(404);
    }

    const isValid = await bcrypt.compare(password, user.password);

    if (!isValid) {
        return h.response({ error: 'Invalid password' }).code(401);
    }

    return h.response({ message: 'Login successful' }).code(200);
};