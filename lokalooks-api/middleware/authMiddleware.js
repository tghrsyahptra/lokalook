const jwt = require('jsonwebtoken');

const authMiddleware = async (request, h) => {
    try {
        const authHeader = request.headers.authorization;

        if (!authHeader) {
            return h.response({ error: 'Authorization header missing' }).code(401).takeover();
        }

        const token = authHeader.split(' ')[1];

        if (!token) {
            return h.response({ error: 'Token missing' }).code(401).takeover();
        }

        const decoded = jwt.verify(token, process.env.JWT_SECRET);

        request.user = decoded;
        return h.continue;
    } catch (error) {
        console.error('Authentication error:', error);
        return h.response({ error: 'Invalid token' }).code(401).takeover();
    }
};

module.exports = authMiddleware;