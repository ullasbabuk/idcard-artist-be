package com.idcard.artists.apiservice.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FacultyDto {
  String facultySchoolName;
  String facultyName;
  String facultyAdmissionNumber;
  String facultyClass;
  String facultyDivision;
  String facultyDateOfBirth;
  String facultyContactNumber;
  String facultyGuardianName;
  String facultyAddressLine1;
  String facultyAddressLine2;
  String facultyBloodGroup;
}
