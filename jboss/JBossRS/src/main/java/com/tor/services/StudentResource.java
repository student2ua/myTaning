package com.tor.services;

import com.tor.domine.Student;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: tor
 * Date: 16.12.13
 * Time: 14:58
 * To change this template use File | Settings | File Templates.
 */
@Path("/student")
public class StudentResource {
    private Map<Integer, Student> studentMap = new ConcurrentHashMap<Integer, Student>();
    private AtomicInteger idCounter = new AtomicInteger();

    public StudentResource() {
    }

    @POST
    @Consumes("application/xml")
    public Response createStudent(InputStream inputStream) {
        Student student = readIS(inputStream);
        student.setId(idCounter.incrementAndGet());
        studentMap.put(student.getId(), student);
        return Response.created(URI.create("/student/" + student.getId())).build();
    }

    @GET
    @Path("{id}")
    @Produces("application/xml")
    public StreamingOutput getStudent(@PathParam("id") int id) {
        final Student student = studentMap.get(id);
        return new StreamingOutput() {
            @Override
            public void write(OutputStream outputStream) throws IOException, WebApplicationException {
                writeStudent(outputStream, student);
            }
        };
    }

    @PUT
    @Path("{id}")
    @Consumes("application/xml")
    public void updateStudent(@PathParam("id") int id, InputStream inputStream) {
        Student updateStudent = readIS(inputStream);
        Student curentStudent = studentMap.get(id);
        if (curentStudent == null) throw new WebApplicationException(Response.Status.NOT_FOUND);

        curentStudent.setFirstName(updateStudent.getFirstName());
        curentStudent.setMiddleName(updateStudent.getMiddleName());
        curentStudent.setLastName(updateStudent.getLastName());
    }

    private void writeStudent(OutputStream outputStream, Student student) {
        PrintStream writer = new PrintStream(outputStream);
        writer.println("<student id=\"" + student.getId() + "\">");
        writer.println("   <first-name>" + student.getFirstName() + "</first-name>");
        writer.println("   <last-name>" + student.getLastName() + "</last-name>");
        writer.println("   <middle-name>" + student.getMiddleName() + "</middle-name>");

        writer.println("</student>");
    }

    private Student readIS(InputStream inputStream) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(inputStream);
            Element root = doc.getDocumentElement();
            Student student = new Student();
            if (root.getAttribute("id") != null && !root.getAttribute("id").trim().isEmpty())
                student.setId(Integer.valueOf(root.getAttribute("id")));
            NodeList nodes = root.getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
                if (element.getTagName().equals("first-name")) {
                    student.setFirstName(element.getTextContent());
                } else if (element.getTagName().equals("last-name")) {
                    student.setLastName(element.getTextContent());
                } else if (element.getTagName().equals("middle-name")) {
                    student.setMiddleName(element.getTextContent());
                }
            }
            return student;
        } catch (Exception e) {
            throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
        }
    }
}
