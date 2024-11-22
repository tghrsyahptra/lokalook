const wisataController = require('../controllers/wisataController');
const authMiddleware = require('../middleware/authMiddleware');

module.exports = [
    {
        method: 'POST',
        path: '/add-wisata',
        handler: wisataController.createWisata,
        options: {
            pre: [authMiddleware]
        }
    },
    {
        method: 'GET',
        path: '/get-wisata',
        handler: wisataController.getAllWisata,
        options: {
            pre: [authMiddleware]
        }
    },
    {
        method: 'PUT',
        path: '/update-wisata/{id}',
        handler: wisataController.updateWisata,
        options: {
            pre: [authMiddleware]
        }
    },
    // Add other routes for CRUD operations
];