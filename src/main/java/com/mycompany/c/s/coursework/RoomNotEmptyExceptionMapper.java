/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.c.s.coursework;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.Map;

@Provider
public class RoomNotEmptyExceptionMapper implements ExceptionMapper<RoomNotEmptyException>
{
    
    @Override
    public Response toResponse(RoomNotEmptyException exception)
    {
        return Response.status(Response.Status.CONFLICT).type(MediaType.APPLICATION_JSON).entity(Map.of("error", "Room is not empty", "message", exception.getMessage())).build();
    }
}
