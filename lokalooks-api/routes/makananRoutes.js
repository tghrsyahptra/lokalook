const makananController = require('../controllers/makananController');

module.exports = [
    {
        method: 'POST',
        path: '/makanan',
        handler: makananController.createMakanan
    },
    // Add other routes for CRUD operations
];