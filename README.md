# Smart Campus API CourseWork

Module: 5COSC022W – Client-Server Architectures  
Coursework: Smart Campus Sensor & Room Management API

## Overview
This project is a RESTful API built in Java using JAX-RS (Jersey) and an embedded Grizzly HTTP server.

It simulates a university Smart Campus backend system that manages:
- Rooms
- Sensors assigned to rooms
- Sensor readings linked to sensors

The API supports:
- room creation, retrieval, listing, and deletion
- sensor creation and retrieval
- filtering sensors by type
- nested sensor reading resources
- custom exception handling
- request/response logging

Base URL: `http://localhost:8081/api/v1`

## API Design Overview

This API was designed as a simple RESTful service for a Smart Campus system. The idea is to model the campus using three main resources: rooms, sensors, and sensor readings. Rooms and sensors are top-level resources, readings are nested under a sensor because a reading belongs to a specific sensor.

The API uses a versioned base path, `/api/v1`. There is a discovery endpoint at the root of the API which returns basic metadata and links to the main resource collections. 

The design follows standard REST conventions by using `GET` to retrieve data, `POST` to create new data, and `DELETE` to remove resources. All responses are returned in JSON.

A room cannot be deleted if it still has sensors assigned to it, a sensor cannot be created unless its `roomId` refers to an existing room, and a sensor in `MAINTENANCE` cannot accept new readings. Sensor readings are linked back to the parent sensor by updating the sensor’s `currentValue` whenever a new reading is posted.

The project uses in-memory Java collections to store data instead of a database. 

Custom exception mappers and a logging filter were added so that the API gives structured error responses and logs incoming requests and outgoing status codes.

## Build and Run

1. Clone the repository:
   `git clone https://github.com/CalciumCO3/Client-Server.git`

2. Open the project folder:
   `cd Client-Server`

3. Build the project with Maven:
   `mvn clean install`

4. Run the server:
   `mvn exec:java`

5. When the server starts, it should print:
   `Server started at: http://localhost:8081/api/v1`

6. The API can then be accessed at:
   `http://localhost:8081/api/v1`

## Sample curl Commands

### Get API discovery information
`curl http://localhost:8081/api/v1`

### Create a Room
`curl -X POST http://localhost:8081/api/v1/rooms -H "Content-Type: application/json" -d "{\"id\":\"LIB-001\",\"name\":\"Library\",\"capacity\":40}"`

### Get All Rooms
`curl http://localhost:8081/api/v1/rooms`

### Get Singular Room
`curl http://localhost:8081/api/v1/rooms/LIB-001`

### Create Sensor
`curl -X POST http://localhost:8081/api/v1/sensors -H "Content-Type: application/json" -d "{\"id\":\"TEMP-001\",\"type\":\"Temperature\",\"status\":\"ACTIVE\",\"currentValue\":21.5,\"roomId\":\"LIB-001\"}"`

### Get All Sensors
`curl http://localhost:8081/api/v1/sensors`

### Get Filtered Sensors
`curl "http://localhost:8081/api/v1/sensors?type=Temperature"`

### Add Sensor Reading
`curl -X POST http://localhost:8081/api/v1/sensors/TEMP-001/readings -H "Content-Type: application/json" -d "{\"id\":\"READ-001\",\"timestamp\":1712600000000,\"value\":23.4}"`

### Delete Room
`curl -X DELETE http://localhost:8081/api/v1/rooms/LIB-001`
