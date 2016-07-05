#!/bin/bash

# Get required packages
echo "Getting required packages"
mkdir ./node_modules
npm install body-parser --save
npm install express --save
npm install mongodb --save

# Shutdown exisiting mongo instance
mongo 127.0.0.1/admin --eval "db.shutdownServer()"

# Start the DB
echo "Starting mongo -- tail -F ./logs/mongo.log for output tail -F ./logs/mongo_error.log for errors"
mkdir ./logs
mkdir -p ~/dev/data/db
mongod --dbpath ~/dev/data/db > logs/mongo.log 2> logs/mongo_error.log &

# Start the server
echo "Starting node server -- tail -F ./logs/node_server.log for output"
node index.js > logs/node_server.log &

echo "Ready to hit api at localhost:3000 or run test script my executing mocha"
echo "DB will not be populated with users/follows until executing mocha suite once"
