package be.kdg.programming5.musicwebsite.api.controller;

import be.kdg.programming5.musicwebsite.api.dto.WebsiteUserDTO;
import be.kdg.programming5.musicwebsite.api.dto.post.WebsiteUserPostDTO;
import be.kdg.programming5.musicwebsite.domain.user.WebsiteUser;
import be.kdg.programming5.musicwebsite.service.WebsiteUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegistrationController {
    private final WebsiteUserService websiteUserService;
    private final ModelMapper mapper;

    @Autowired
    public RegistrationController(WebsiteUserService websiteUserService, ModelMapper mapper) {
        this.websiteUserService = websiteUserService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<WebsiteUserDTO> registerUser(@RequestBody @Valid WebsiteUserPostDTO websiteUserPostDTO, HttpServletRequest httpServletRequest) throws ServletException {
        WebsiteUser registeredUser = websiteUserService.registerUser(websiteUserPostDTO.getUsername(), websiteUserPostDTO.getPassword());
        httpServletRequest.login(websiteUserPostDTO.getUsername(), websiteUserPostDTO.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(registeredUser, WebsiteUserDTO.class));
    }
}
