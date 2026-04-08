/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.c.s.coursework;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import jakarta.ws.rs.PathParam;


@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource 
{
    
    @GET
    public List<Sensor> getAllSensors(@QueryParam("type") String type)
    {
        List<Sensor> allSensors = new ArrayList<>(DataStore.sensors.values());
        
        if (type == null || type.isBlank())
        {
            return allSensors;
        }
        
        List<Sensor> filteredSensors = new ArrayList<>();
        for (Sensor sensor : allSensors)
        {
            if (sensor.getType() != null && sensor.getType().equalsIgnoreCase(type))
            {
                filteredSensors.add(sensor);
            }
        }
        return filteredSensors;
    }
    
    @POST
    public Response createSensor(Sensor sensor)
    {
        if (sensor == null || sensor.getId() == null || sensor.getId().isBlank())
        {
                return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", "Sensor id is required")).build();
        }
        
        if (DataStore.sensors.containsKey(sensor.getId()))
        {
            return Response.status(Response.Status.CONFLICT).entity(Map.of("error", "Sensor with this id already exists")).build();
        }
        
        if (sensor.getRoomId() == null || sensor.getRoomId().isBlank())
        {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", "room id is required")).build(); 
        }
        
        Room room = DataStore.rooms.get(sensor.getRoomId());
        if (room == null) 
        {
            throw new LinkedResourceNotFoundException("Referenced room does not exist: " + sensor.getRoomId());
        }   
        
        DataStore.sensors.put(sensor.getId(), sensor);
        room.getSensorIds().add(sensor.getId());
        DataStore.readings.put(sensor.getId(), new ArrayList<>());
        
        return Response.status(Response.Status.CREATED).entity(sensor).build();
    }
    
    @Path("/{sensorId}/readings")
    public SensorReadingResource getSensorReadingResource(@PathParam("sensorId") String sensorId)
    {
        return new SensorReadingResource(sensorId);
    }
}
