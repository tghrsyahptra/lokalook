const wisataController = require('../controllers/wisataController');

module.exports = [
    {
        method: 'POST',
        path: '/add-wisata',
        handler: wisataController.createWisata
    },
    {
        method: 'GET',
        path: '/get-wisatas',
        handler: wisataController.getAllWisata
    },
    {
        method: 'PUT',
        path: '/update-wisata/{id}',
        handler: wisataController.updateWisata
    },
    // Add other routes for CRUD operations
];