package com.thbs.usercreation.controllerTest;



import com.thbs.usercreation.dto.UserDTO;
import com.thbs.usercreation.controller.UserController;
import com.thbs.usercreation.entity.User;
import com.thbs.usercreation.enumerate.Role;
import com.thbs.usercreation.exception.UserManagementException;
import com.thbs.usercreation.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll_Success() {
        // Mock data
        List<User> userList = Arrays.asList(
            new User("John", "Doe", "john@example.com","password",1001L, "Sales",Role.ADMIN, true),
            new User("Jane", "Doe", "jane@example.com", "password",1002L, "Marketing", Role.USER,true)
        );

        // Stubbing the behavior of userService.findAll() to return the mock data
        when(userService.findAll()).thenReturn(userList);

        // Call the method being tested
        List<User> result = userController.getAll();

        // Verify that userService.findAll() was called exactly once
        verify(userService, times(1)).findAll();

        // Check if the result matches the expected mock data
        assertEquals(userList.size(), result.size());
        assertEquals(userList, result);
    }

    @Test
    public void testGetAll_EmptyList() {
        // Stubbing the behavior of userService.findAll() to return an empty list
        when(userService.findAll()).thenReturn(Collections.emptyList());

        // Call the method being tested
        List<User> result = userController.getAll();

        // Verify that userService.findAll() was called exactly once
        verify(userService, times(1)).findAll();

        // Check if the result is an empty list
        assertEquals(0, result.size());
    }

    @Test
    public void testUploadUserExcel_IOException() throws IOException, UserManagementException {
        // Mock the MultipartFile
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.xlsx", "text/plain", "mock data".getBytes());

        // Stubbing the behavior of userService.saveUser() to throw an IOException
        doThrow(new IOException("File processing error")).when(userService).saveUser(mockFile);

        // Call the method being tested
        ResponseEntity<Object> response = userController.uploadUserExcel(mockFile);

        // Verify the response status and message
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error: IOException occurred while processing the file.", response.getBody());
    }

    @Test
    public void testUploadUserExcel_UserManagementException() throws IOException, UserManagementException {
        // Mock the MultipartFile
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.xlsx", "text/plain", "mock data".getBytes());

        // Stubbing the behavior of userService.saveUser() to throw a UserManagementException
        doThrow(new UserManagementException("Invalid user data")).when(userService).saveUser(mockFile);

        // Call the method being tested
        ResponseEntity<Object> response = userController.uploadUserExcel(mockFile);

        // Verify the response status and message
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: Invalid user data", response.getBody());
    }
    
    @Test
    public void testGetUserByEmployeeId_UserFound() {
        // Mock data
        Long employeeId = 123L;
        User mockUser = new User();
        mockUser.setEmployeeId(employeeId);

        // Mock UserService to return the mockUser when getUserByEmployeeId is called with employeeId
        when(userService.getUserByEmployeeId(employeeId)).thenReturn(mockUser);

        // Call the method being tested
        ResponseEntity<User> response = userController.getUserByEmployeeId(employeeId);

        // Verify the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUser, response.getBody());
    }

    @Test
    public void testGetUserByEmployeeId_UserNotFound() {
        // Mock data
        Long employeeId = 123L;

        // Mock UserService to return null when getUserByEmployeeId is called with employeeId
        when(userService.getUserByEmployeeId(employeeId)).thenReturn(null);

        // Call the method being tested
        ResponseEntity<User> response = userController.getUserByEmployeeId(employeeId);

        // Verify the response status and body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
    
    @Test
    public void testGetUsersByRoleId() {
        // Mock data
        Role role = Role.USER;
        List<UserDTO> userList = Arrays.asList(
        		new UserDTO(1001L, "John", "Doe", "john@example.com", Role.USER, "Sales"),
                new UserDTO(1002L, "Jane", "Doe", "jane@example.com", Role.ADMIN, "Marketing")
        );

        // Stubbing the behavior of userService.getUsersByRole() to return the mock data
        when(userService.getUsersByRole(role)).thenReturn(userList);

        // Call the method being tested
        List<UserDTO> result = userController.getUsersByRoleId();

        // Verify that userService.getUsersByRole() was called exactly once
        verify(userService, times(1)).getUsersByRole(role);

        // Check if the result matches the expected mock data
        assertEquals(userList.size(), result.size());
        assertEquals(userList, result);
    }

    @Test
    public void testGetAllUserEmployeeId() {
        // Mock data
        List<Long> userIds = Arrays.asList(1001L, 1002L, 1003L);

        // Stubbing the behavior of userService.findUserEmployeeIds() to return the mock data
        when(userService.findUserEmployeeIds()).thenReturn(userIds);

        // Call the method being tested
        List<Long> result = userController.getAllUserEmployeeId();

        // Verify that userService.findUserEmployeeIds() was called exactly once
        verify(userService, times(1)).findUserEmployeeIds();

        // Check if the result matches the expected mock data
        assertEquals(userIds.size(), result.size());
        assertEquals(userIds, result);
    }

    @Test
    public void testGetUsersByBusinessUnit() {
        // Mock data
        String businessUnit = "Sales";
        List<Long> userIds = Arrays.asList(1001L, 1002L, 1003L);

        // Stubbing the behavior of userService.findEmployeeIdsByBusinessUnit() to return the mock data
        when(userService.findEmployeeIdsByBusinessUnit(businessUnit)).thenReturn(userIds);

        // Call the method being tested
        ResponseEntity<List<Long>> response = userController.getUsersByBusinessUnit(businessUnit);

        // Verify the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userIds, response.getBody());
    }

    @Test
    public void testGetUsersByBusinessUnit_NoUsersFound() {
        // Mock data
        String businessUnit = "Finance";

        // Stubbing the behavior of userService.findEmployeeIdsByBusinessUnit() to return an empty list
        when(userService.findEmployeeIdsByBusinessUnit(businessUnit)).thenReturn(Collections.emptyList());

        // Call the method being tested
        ResponseEntity<List<Long>> response = userController.getUsersByBusinessUnit(businessUnit);

        // Verify the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.emptyList(), response.getBody()); // Change the assertion to expect an empty list
    }


    @Test
    public void testFindAllUsers() {
        // Mock data
        List<UserDTO> userDTOList = Arrays.asList(
            new UserDTO(1001L, "John", "Doe", "john@example.com", Role.USER, "Sales"),
            new UserDTO(1002L, "Jane", "Doe", "jane@example.com", Role.ADMIN, "Marketing")
        );

        // Stubbing the behavior of userService.findAllUsers() to return the mock data
        when(userService.findAllUsers()).thenReturn(userDTOList);

        // Call the method being tested
        List<UserDTO> result = userController.findAllUsers();

        // Verify that userService.findAllUsers() was called exactly once
        verify(userService, times(1)).findAllUsers();

        // Check if the result matches the expected mock data
        assertEquals(userDTOList.size(), result.size());
        assertEquals(userDTOList, result);
    }
   
}
