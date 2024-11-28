const { Storage } = require('@google-cloud/storage');
const path = require('path');

const storage = new Storage({
    keyFilename: path.join(__dirname, '../service-account.json'),
    projectId: 'lokalook-id',
});

const bucketName = 'lokalook-web-api';
const bucket = storage.bucket(bucketName);

module.exports = bucket;