/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.c.s.coursework;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Map;
import jakarta.ws.rs.DELETE;

@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource 
{
    @GET
    public ArrayList<Room> getAllRooms()
    {
        return new ArrayList<>(DataStore.rooms.values());
    }
    
    @POST
    public Response createRoom(Room room)
    {
        if (room == null || room.getId() == null || room.getId().isBlank())
        {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", "Room id is required")).build();
        }
        if (DataStore.rooms.containsKey(room.getId()))
        {
            return Response.status(Response.Status.CONFLICT).entity(Map.of("error", "Room with this id already exists")).build();
        }
        
        if (room.getSensorIds() == null)
        {
            room.setSensorIds(new ArrayList<>());
        }
        DataStore.rooms.put(room.getId(), room);
        
        return Response.status(Response.Status.CREATED).entity(room).build();
    }
    
    @GET
    @Path("/{roomId}")
    public Response getRoomById(@PathParam("roomId") String roomId)
    {
        Room room = DataStore.rooms.get(roomId);
        
        if (room == null)
        {
            return Response.status(Response.Status.NOT_FOUND).entity(Map.of("error", "Room not found")).build();
        }
        return Response.ok(room).build();
    }
    
    @DELETE
    @Path("/{roomId}")
    public Response deleteRoom(@PathParam("roomId") String roomId)
    {
        Room room = DataStore.rooms.get(roomId);
        
        if (room == null)
        {
            return Response.status(Response.Status.NOT_FOUND).entity(Map.of("error", "Room not found")).build();
        }
        
        if (room.getSensorIds() != null && !room.getSensorIds().isEmpty())
        {
            throw new RoomNotEmptyException("Cannot delete room because it still has sensors assigned.");
        }
        DataStore.rooms.remove(roomId);
        
        return Response.ok(Map.of("message", "Room deleted succesfully")).build();
    }
}
