const authController = require('../controllers/authController');

module.exports = [
    {
        method: 'POST',
        path: '/register',
        handler: authController.register
    },
    {
        method: 'POST',
        path: '/login',
        handler: authController.login
    }
];