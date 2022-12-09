package com.example.photographerApp.controller;

import com.example.photographerApp.model.Authority;
import com.example.photographerApp.model.User;
import com.example.photographerApp.repository.UserRepository;
import com.example.photographerApp.request.UserCreateRequest;
import com.example.photographerApp.request.UserUpdateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "server-port = 0"
})
@RunWith(SpringRunner.class)
@DirtiesContext
class UserControllerTest 
{
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext webApplicationContext;


    @BeforeEach
    public void setup()
    {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        userRepository.deleteAll();
    }

    @Test//valid authority
    @WithMockUser(authorities = "admin")
    public void testCreateUserWithAValidAuthority_ShouldCreateUserAndReturnUser()
            throws Exception
    {
        UserCreateRequest request = new UserCreateRequest();
        request.setEmail("email");
        request.setFirstName("firstName");
        request.setLastName("lastName");
        request.setPassword("password");
        request.setAuthorityId(1L);
        this.mockMvc
                .perform(post("/api/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email", is("email")))
                .andExpect(jsonPath("$.firstName", is("firstName")))
                .andExpect(jsonPath("$.lastName", is("lastName")));

    }

    @Test//invalid authority
    @WithMockUser(authorities = "manager")
    public void testCreateUserWithAnInvalidAuthority_ShouldReturnHTTPStatus_Unauthorized()
            throws Exception
    {
        UserCreateRequest request = new UserCreateRequest();
        request.setEmail("email");
        request.setFirstName("firstName");
        request.setLastName("lastName");

        this.mockMvc
                .perform(post("/api/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test//valid authority
    @WithMockUser(authorities = "admin")
    public void testUpdateUserWithAValidAuthority_ShouldUpdateUserAndReturnUser()
            throws Exception
    {
        User user = userRepository.save(new User(1L,"email", "password",
                "firstname", "lastname", new Authority(1L, "admin"), null));
        UserUpdateRequest request = new UserUpdateRequest();
        request.setEmail("new email");
        request.setFirstName("new firstName");
        request.setLastName("new lastName");
        request.setPassword("new password");
        request.setAuthorityId(1L);

        this.mockMvc
                .perform(put("/api/users/update/{userId}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email", is("new email")))
                .andExpect(jsonPath("$.firstName", is("new firstName")))
                .andExpect(jsonPath("$.lastName", is("new lastName")));
    }

    @Test//invalid authority
    @WithMockUser(authorities = "manager")
    public void testUpdateUserWithAnInvalidAuthority_ShouldReturnHTTPStatus_Unauthorized()
            throws Exception
    {

        User user = userRepository.save(new User(1L,"email", "password",
                "firstname", "lastname", new Authority(1L, "admin"), null));
        UserUpdateRequest request = new UserUpdateRequest();
        request.setEmail("new email");
        request.setFirstName("new firstName");
        request.setLastName("new lastName");
        request.setPassword("new password");

        this.mockMvc
                .perform(put("/api/users/update/{userId}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isForbidden());

    }

    @Test//valid authority
    @WithMockUser(authorities = "admin")
    public void testDeleteUserWithAValidAuthority_ShouldDeleteUser()
            throws Exception
    {
        User user = userRepository.save(new User(1L,"email", "password",
                "firstname", "lastname", new Authority(1L, "admin"), null));

        this.mockMvc
                .perform(delete("/api/users/delete/{userId}", user.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test//valid authority
    @WithMockUser(authorities = "manager")
    public void testDeleteUserWithAnInvalidAuthority_ShouldReturnHTTPStatus_Unauthorized()
            throws Exception
    {
        User user = userRepository.save(new User(1L,"email", "password",
                "firstname", "lastname", new Authority(1L, "admin"), null));

        this.mockMvc
                .perform(delete("/api/users/delete/{userId}", user.getId()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }


    @Test//valid authority
    @WithMockUser(authorities = "admin")
    public void testGetAllUsersWhenRequested_ShouldReturnListOfUserResponses()
            throws Exception
    {
        List<User> Users = new ArrayList<>();
        Users.add(new User(1L,"email", "password",
                "firstName", "lastname", new Authority(1L, "admin"), null));
        Users.add(new User(1L,"new email", "new password",
                "new firstName", "new lastName", new Authority(1L, "admin"), null));
        userRepository.saveAll(Users);

        this.mockMvc.perform(get("/api/users/all"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test//valid id
    @WithMockUser(authorities = "admin")
    public void testGetUserWithAValidId_ShouldReturnUserResponse()
            throws Exception
    {
        User user = userRepository.save(new User(1L,"email", "password",
                "firstName", "lastName", new Authority(1L, "admin"), null));

        this.mockMvc.perform(get("/api/users/get/{userId}", user.getId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is("firstName")))
                .andExpect(jsonPath("$.lastName", is("lastName")));
    }

    @Test//invalid id
    @WithMockUser(authorities = "admin")
    public void testGetUserWithAnInvalidId_ShouldReturnHttpStatus_NotFound()
            throws Exception
    {
        this.mockMvc.perform(get("/api/users/get/{userId}", 0L))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}