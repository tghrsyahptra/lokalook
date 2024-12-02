const jwt = require('jsonwebtoken');

const authMiddleware = async (request, h) => {
    const authorization = request.headers.authorization;

    if (!authorization) {
        return h.response({ error: 'Authorization header missing' }).code(401).takeover();
    }

    const token = authorization.split(' ')[1];

    try {
        const decoded = jwt.verify(token, process.env.JWT_SECRET);
        request.auth = decoded;
        return h.continue;
    } catch (error) {
        return h.response({ error: 'Invalid token' }).code(401).takeover();
    }
};

module.exports = authMiddleware;