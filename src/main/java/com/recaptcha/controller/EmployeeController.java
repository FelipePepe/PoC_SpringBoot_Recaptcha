package com.recaptcha.controller;

import com.recaptcha.controller.dto.EmployeeDTO;
import com.recaptcha.entities.EmployeeEntity;
import com.recaptcha.service.EmployeeService;
import com.recaptcha.service.RecaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class EmployeeController {

   @Autowired
   private EmployeeService employeeService;

   @Autowired
   private RecaptchaService recaptchaService;

   @GetMapping(path={"/", "/all"})
   public String showAll(Model model) {
      List<EmployeeEntity> employeeEntityList = employeeService.findAll();

      model.addAttribute("employees", employeeEntityList);

      return "index";
   }

   @GetMapping(path={"/create/form"})
   public String createEmployee(Model model) {

      model.addAttribute("employee", new EmployeeEntity());

      return "form";
   }

   @PostMapping("/create/process")
   public String createProcess(@ModelAttribute(name = "employee") EmployeeDTO dto, @RequestParam(name ="g-recaptcha-response") String captcha, Model model) {

      boolean captchaValid = recaptchaService.validateRecaptcha(captcha);

      if (captchaValid) {
         EmployeeEntity employeeEntity = EmployeeEntity.builder()
                 .name(dto.getName())
                 .lastName(dto.getLastName())
                 .dateOfBirth(dto.getDateOfBirth())
                 .build();

         employeeService.createEmployee(employeeEntity);
         return "redirect:/all";
      } else {
         model.addAttribute("message", "Captcha invalido");
         return "error";
      }


   }

}
