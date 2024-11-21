const Hapi = require('@hapi/hapi');
const db = require('./config');

const init = async () => {
    const server = Hapi.server({
        port: 3000,
        host: 'localhost'
    });

    server.app.db = db;

    server.route(require('./routes/authRoutes'));
    server.route(require('./routes/wisataRoutes'));
    server.route(require('./routes/makananRoutes'));

    await server.start();
    console.log('Server running on %s', server.info.uri);
};

process.on('unhandledRejection', (err) => {
    console.log(err);
    process.exit(1);
});

init();