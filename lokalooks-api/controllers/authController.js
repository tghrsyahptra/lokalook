const bcrypt = require('bcryptjs');

exports.register = async (request, h) => {
    const { nama_lengkap, email, alamat, password, username } = request.payload;
    const hashedPassword = await bcrypt.hash(password, 10);

    const [result] = await request.server.app.db.execute(
        'INSERT INTO penggunas (nama_lengkap, email, alamat, password, username) VALUES (?, ?, ?, ?, ?)',
        [nama_lengkap, email, alamat, hashedPassword, username]
    );

    return h.response({ id: result.insertId }).code(201);
};

exports.login = async (request, h) => {
    const { email, password } = request.payload;

    const [rows] = await request.server.app.db.execute(
        'SELECT * FROM penggunas WHERE email = ?',
        [email]
    );

    if (rows.length === 0) {
        return h.response({ error: 'User not found' }).code(404);
    }

    const user = rows[0];
    const isValid = await bcrypt.compare(password, user.password);

    if (!isValid) {
        return h.response({ error: 'Invalid password' }).code(401);
    }

    return h.response({ message: 'Login successful' }).code(200);
};