const jwt = require('jsonwebtoken');
const bcrypt = require('bcryptjs');
const crypto = require('crypto');
const Pengguna = require('../models/penggunaModel');
const nodemailer = require('nodemailer');
const mailConfig = require('../config/mail');

exports.register = async (request, h) => {
    try {
        const { nama_lengkap, email, alamat, password, username, hasil_personalisasi, image_pp } = request.payload;
        const hashedPassword = await bcrypt.hash(password, 10);

        const id = await Pengguna.create({
            nama_lengkap,
            email,
            alamat,
            password: hashedPassword,
            username,
            hasil_personalisasi,
            image_pp
        });

        return h.response({ id }).code(201);
    } catch (error) {
        console.error(error);
        return h.response({ error: 'Internal Server Error', message: error.message }).code(500);
    }
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

exports.sendResetPasswordEmail = async (request, h) => {
    try {
        const { email } = request.payload;

        const user = await Pengguna.findByEmail(email);

        if (!user) {
            return h.response({ error: 'User not found' }).code(404);
        }

        const token = crypto.randomBytes(20).toString('hex');
        await Pengguna.setPasswordResetToken(email, token);

        const transporter = nodemailer.createTransport({
            host: '103-127-133-117.cprapid.com',
            port: 465,
            secure: true,
            auth: {
                user: 'baehaqi@sapphiregrup.com',
                pass: 'Koperb1ru'
            },
            debug: true,
            logger: true,
            tls: {
                rejectUnauthorized: false,
                minVersion: 'TLSv1'
            }
        });

        // Logging untuk debug
        transporter.on('log', console.log);

        try {
            // Verifikasi koneksi SMTP
            await transporter.verify();
            console.log('SMTP connection verified successfully');

            const mailOptions = {
                from: {
                    name: 'lokalook',
                    address: 'baehaqi@sapphiregrup.com'
                },
                to: email,
                subject: 'Password Reset',
                text: `Anda menerima ini karena Anda (atau orang lain) telah meminta pengaturan ulang kata sandi untuk akun Anda.\n\n
                Silakan klik tautan berikut ini, atau tempelkan tautan ini ke dalam browser Anda untuk menyelesaikan prosesnya:\n\n
                http://localhost:3000/reset-password/${token}\n\n
                Jika Anda tidak meminta hal ini, harap abaikan email ini dan kata sandi Anda tidak akan berubah.\n\n`
            };

            const info = await transporter.sendMail(mailOptions);
            console.log('Message sent: %s', info.messageId);

            return h.response({ 
                message: 'Password reset email sent',
                messageId: info.messageId 
            }).code(200);

        } catch (smtpError) {
            console.error('SMTP Error:', smtpError);
            return h.response({ 
                error: 'Email sending failed', 
                details: smtpError.message 
            }).code(500);
        }

    } catch (error) {
        console.error('General Error:', error);
        return h.response({ 
            error: 'Internal Server Error', 
            message: error.message 
        }).code(500);
    }
};

exports.resetPassword = async (request, h) => {
    try {
        const { token, newPassword } = request.payload;

        const user = await Pengguna.findByResetToken(token);

        if (!user) {
            return h.response({ error: 'Invalid token' }).code(400);
        }

        const hashedPassword = await bcrypt.hash(newPassword, 10);
        await Pengguna.resetPassword(user.email, hashedPassword);

        return h.response({ message: 'Password has been reset' }).code(200);
    } catch (error) {
        console.error(error);
        return h.response({ error: 'Internal Server Error', message: error.message }).code(500);
    }
};

exports.generateToken = async (request, h) => {
    try {
        const payload = {
            user: 'api-user'
        };

        console.log('JWT_SECRET:', process.env.JWT_SECRET); // Tambahkan log ini untuk debug

        const token = jwt.sign(payload, process.env.JWT_SECRET, { expiresIn: '50m' });

        return h.response({ token }).code(200);
    } catch (error) {
        console.error('Token Generation Error:', error);
        return h.response({ error: 'Internal Server Error', message: error.message }).code(500);
    }
};

exports.home = (request, h) => {
    return h.response({ message: 'SEMUA AMAN' }).code(200);
};