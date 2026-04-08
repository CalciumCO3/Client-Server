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
public class LinkedResourceNotFoundExceptionMapper implements ExceptionMapper<LinkedResourceNotFoundException> 
{

    @Override
    public Response toResponse(LinkedResourceNotFoundException exception) 
    {
        return Response.status(422).type(MediaType.APPLICATION_JSON).entity(Map.of("error", "Linked resource not found","message", exception.getMessage())).build();
    }
}
