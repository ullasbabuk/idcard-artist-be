package com.idcard.artists.apiservice.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StudentDto {
  String studentSchoolName;
  String studentSchoolId;
  String studentName;
  String studentAdmissionNumber;
  String studentClass;
  String studentDivision;
  String studentDateOfBirth;
  String studentContactNumber;
  String studentGuardianName;
  String studentAddressLine1;
  String studentAddressLine2;
  String studentBloodGroup;
}
