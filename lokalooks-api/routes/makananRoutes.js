const makananController = require('../controllers/makananController');

module.exports = [
    {
        method: 'POST',
        path: '/add-food',
        handler: makananController.createMakanan
    },
    {
        method: 'GET',
        path: '/get-food',
        handler: makananController.getAllMakanan
    },
    {
        method: 'PUT',
        path: '/update-food/{id}',
        handler: makananController.updateMakanan
    },
];