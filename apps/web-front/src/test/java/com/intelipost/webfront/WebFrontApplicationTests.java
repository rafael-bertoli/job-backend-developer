package com.intelipost.webfront;

import com.intelipost.webfront.dto.User;
import com.intelipost.webfront.exception.UserNotFoundException;
import com.intelipost.webfront.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebFrontApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class WebFrontApplicationTests {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private UserService userService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void indexTest() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void invalidSessionTest() throws Exception {
        this.mockMvc.perform(get("/user"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void invalidLoginTest() throws Exception {
        when(userService.doLogin("admin@admin.com", "12433")).thenThrow(new UserNotFoundException());
        this.mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("username", "admin@admin.com")
                .param("password", "12433"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(flash().attribute("failure", "Login inválido, verifique seus dados."));
    }
    
    @Test
    public void successLoginTest() throws Exception {
        when(userService.doLogin("operator@system.com", "123456")).thenReturn(new User());
        this.mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("username", "operator@system.com")
                .param("password", "123456"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user"));
    }

    @Test
    public void logoutTest() throws Exception {
        this.mockMvc.perform(get("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(flash().attribute("success", "Você deslogou do sistema"));
    }
}
