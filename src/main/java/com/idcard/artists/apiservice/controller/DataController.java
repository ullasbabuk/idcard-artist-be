package com.idcard.artists.apiservice.controller;

import com.idcard.artists.apiservice.data.dto.StudentDto;
import com.idcard.artists.apiservice.service.StudentService;
import com.idcard.artists.apiservice.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("v1")
@RequiredArgsConstructor
@CrossOrigin
public class DataController {

  private final StudentService dataService;
  private final TokenService tokenService;

  @PostMapping(
          value = "/students",
          consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE)
  public StudentDto saveStudent(
          @RequestHeader(value="token") String token,
          @RequestPart(name = "profilePicture") MultipartFile file,
          @RequestParam Map<String, String> metaInformation
  ) throws IOException {

    if (tokenService.verifyToken(token)) {
      Map<String, String> metaInfo = new HashMap<>();
      StudentDto student = StudentDto.builder().build();

      metaInformation.forEach((key, val) -> {
        if (key.equals("studentSchoolName")) {
          student.setStudentSchoolName(val);
        }
        if (key.equals("studentName")) {
          student.setStudentName(val);
        }
        if (key.equals("studentSchoolId")) {
          student.setStudentSchoolId(val);
        }
        if (key.equals("studentAdmissionNumber")) {
          student.setStudentAdmissionNumber(val);
        }
        if (key.equals("studentClass")) {
          student.setStudentClass(val);
        }
        if (key.equals("studentDivision")) {
          student.setStudentDivision(val);
        }
        if (key.equals("studentContactNumber")) {
          student.setStudentContactNumber(val);
        }
        if (key.equals("studentGuardianName")) {
          student.setStudentGuardianName(val);
        }
        if (key.equals("studentDateOfBirth")) {
          student.setStudentDateOfBirth(val);
        }
        if (key.equals("studentAddressLine1")) {
          student.setStudentAddressLine1(val);
        }
        if (key.equals("studentAddressLine2")) {
          student.setStudentAddressLine2(val);
        }
        if (key.equals("studentBloodGroup")) {
          student.setStudentBloodGroup(val);
        }
      });

      return dataService.saveStudent(student, file);
    } else {
      throw new InvalidTokenException();
    }
  }

}
