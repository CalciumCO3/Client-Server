/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.c.s.coursework;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource 
{
    private final String sensorId;
    
    public SensorReadingResource(String sensorId)
    {
        this.sensorId = sensorId;
    }
    
    @GET
    public Response getReadings()
    {
        Sensor sensor = DataStore.sensors.get(sensorId);
        
        if (sensor == null)
        {
            return Response.status(Response.Status.NOT_FOUND).entity(Map.of("error", "Sensor not found")).build();
        }
        List<SensorReading> sensorReadings = DataStore.readings.get(sensorId);
        
        if (sensorReadings == null)
        {
            sensorReadings = new ArrayList<>();
        }
        return Response.ok(sensorReadings).build();
    }
    @POST
    public Response addReading(SensorReading reading)
    {
        Sensor sensor = DataStore.sensors.get(sensorId);
        
        if (sensor == null)
        {
            return Response.status(Response.Status.NOT_FOUND).entity(Map.of("error", "Sensor not found")).build();
        }
        
        if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())) 
        {
            throw new SensorUnavailableException("Sensor " + sensorId + " is in maintenance and cannot accept new readings.");
        }
        if (reading == null || reading.getId() == null || reading.getId().isBlank())
        {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", "Reading id required")).build();
        }
        
        List<SensorReading> sensorReadings = DataStore.readings.get(sensorId);
        if (sensorReadings == null)
        {
            sensorReadings = new ArrayList<>();
            DataStore.readings.put(sensorId, sensorReadings);
        }
        
        sensorReadings.add(reading);
        
        sensor.setCurrentValue(reading.getValue());
        
        return Response.status(Response.Status.CREATED).entity(reading).build();
    }
    
}
