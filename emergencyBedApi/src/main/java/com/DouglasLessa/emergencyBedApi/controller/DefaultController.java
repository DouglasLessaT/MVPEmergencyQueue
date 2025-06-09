package com.DouglasLessa.emergencyBedApi.controller;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.http.HttpServletRequest;

public abstract class DefaultController {

 @Autowired
 HttpServletRequest request;
}