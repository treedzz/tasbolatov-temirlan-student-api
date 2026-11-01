package com.tasbolatov.studentapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    private static final String TASBOLATOV_JSON = """
            {"firstName":"Temirlan","lastName":"Tasbolatov","email":"temirlan@example.com","course":2}
            """;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void infoShowsStudentName() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("Tasbolatov Temirlan"));
    }

    @Test
    void fullCrudCycle() throws Exception {
        // create
        MvcResult created = mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON).content(TASBOLATOV_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.lastName").value("Tasbolatov"))
                .andReturn();
        String body = created.getResponse().getContentAsString();
        long id = Long.parseLong(body.replaceAll(".*\"id\":(\\d+).*", "$1"));

        // read one
        mockMvc.perform(get("/api/students/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Temirlan"));

        // read all
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk());

        // update
        mockMvc.perform(put("/api/students/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TASBOLATOV_JSON.replace("\"course\":2", "\"course\":3")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.course").value(3));

        // delete
        mockMvc.perform(delete("/api/students/" + id))
                .andExpect(status().isNoContent());

        // nonexistent after delete
        mockMvc.perform(get("/api/students/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void nonexistentStudentReturns404() throws Exception {
        mockMvc.perform(get("/api/students/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Student with id 99999 not found"));
    }

    @Test
    void invalidStudentReturns400() throws Exception {
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON).content("{\"firstName\":\"\"}"))
                .andExpect(status().isBadRequest());
    }
}
