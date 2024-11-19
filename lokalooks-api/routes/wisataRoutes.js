const wisataController = require('../controllers/wisataController');

module.exports = [
    {
        method: 'POST',
        path: '/wisata',
        handler: wisataController.createWisata
    },
    // Add other routes for CRUD operations
];