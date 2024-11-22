require('dotenv').config();
const Hapi = require('@hapi/hapi');
const authRoutes = require('./routes/authRoutes');
const wisataRoutes = require('./routes/wisataRoutes');
const makananRoutes = require('./routes/makananRoutes');

// Check for required environment variables
const requiredEnvVars = ['JWT_SECRET'];
const missingEnvVars = requiredEnvVars.filter(envVar => !process.env[envVar]);

if (missingEnvVars.length > 0) {
    console.warn(`Missing required environment variables: ${missingEnvVars.join(', ')}. Using default values.`);
    process.env.JWT_SECRET = process.env.JWT_SECRET || 'default_jwt_secret';
}

const init = async () => {
    const server = Hapi.server({
        port: process.env.PORT || 3000,
        host: process.env.HOST || 'localhost',
        routes: {
            cors: {
                origin: ['*'], // Configure according to your needs
                headers: ['Accept', 'Authorization', 'Content-Type', 'If-None-Match'],
                additionalHeaders: ['X-Requested-With']
            }
        }
    });

    // Register plugins and middleware here if needed
    
    // Error handling for routes
    server.ext('onPreResponse', (request, h) => {
        const response = request.response;
        
        if (!response.isBoom) {
            return h.continue;
        }

        // Handle errors
        const error = response;
        return h.response({
            statusCode: error.output.statusCode,
            error: error.output.payload.error,
            message: error.output.payload.message
        }).code(error.output.statusCode);
    });

    console.log('Environment variables loaded:', {
        JWT_SECRET: process.env.JWT_SECRET ? 'Set' : 'Not set',
        PORT: process.env.PORT,
        HOST: process.env.HOST
    });

    // Register routes
    server.route([
        ...authRoutes,
        ...wisataRoutes,
        ...makananRoutes
    ]);

    try {
        await server.start();
        console.log('Server running on %s', server.info.uri);
    } catch (err) {
        console.error('Error starting server:', err);
        process.exit(1);
    }
};

process.on('unhandledRejection', (err) => {
    console.error('Unhandled rejection:', err);
    process.exit(1);
});

process.on('SIGINT', async () => {
    console.log('Stopping server...');
    try {
        await server.stop({ timeout: 10000 });
        console.log('Server stopped');
        process.exit(0);
    } catch (err) {
        console.error('Error stopping server:', err);
        process.exit(1);
    }
});

init();