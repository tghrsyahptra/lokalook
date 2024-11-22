const makananController = require('../controllers/makananController');
const authMiddleware = require('../middleware/authMiddleware');

module.exports = [
    {
        method: 'POST',
        path: '/add-food',
        handler: makananController.createMakanan,
        options: {
            pre: [authMiddleware]
        }
    },
    {
        method: 'GET',
        path: '/get-food',
        handler: makananController.getAllMakanan,
        options: {
            pre: [authMiddleware]
        }
    },
    {
        method: 'PUT',
        path: '/update-food/{id}',
        handler: makananController.updateMakanan,
        options: {
            pre: [authMiddleware]
        }
    },
];