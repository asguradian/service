package org.asguard.service;


import io.dropwizard.hibernate.UnitOfWork;
import lombok.AllArgsConstructor;
import org.asguard.service.daoimpl.EmployeeDaoImpl;
import org.asguard.service.model.Employee;
import org.joda.time.DateTime;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

@Path("/service")
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class EmployeeService {

    private EmployeeDaoImpl employeeDao;

    @GET
    @UnitOfWork
    @Path("/employees")
    public Response getEmployees() {
        return Response.ok(employeeDao.findAll()).build();
    }

    @GET
    @UnitOfWork
    @Path("/{id}")
    public Response getEmployee(@PathParam("id") Long id) {
        return Response.ok(employeeDao.findById(id)).build();
    }

    @POST
    @UnitOfWork
    @Path("/create")
    public Response create( Employee employee) {
         employee = employeeDao.create(employee);
        return Response.ok(employee).cookie( new NewCookie("time", new DateTime().toString())).build();
    }

    @POST
    @Path("/employeeName/{id}")
    @Consumes(value = "text/plain")
    @UnitOfWork
    public Response mediaTypeText( String body, @PathParam("id") Long id) {
        Employee employee =employeeDao.findById(id);
        String response = new StringBuilder(body).append("\n").append(employee.getFirstName()).toString();
        return Response.ok(response, MediaType.TEXT_PLAIN_TYPE).build();
    }

}
