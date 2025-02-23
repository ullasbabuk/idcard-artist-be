package com.idcard.artists.apiservice.service;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.idcard.artists.apiservice.data.dto.StudentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {
  public StudentDto saveStudent(StudentDto student, MultipartFile file) throws IOException {

    Storage storage = StorageOptions.newBuilder().setProjectId("idcard-artis").build().getService();
    BlobId blobId = BlobId.of("idcard-artist-bucket-1",
            student.getStudentAdmissionNumber()+"-"+String.valueOf(System.currentTimeMillis()));
    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

    Storage.BlobTargetOption precondition;
    if (storage.get("idcard-artist-bucket-1",
            student.getStudentAdmissionNumber()+"-"+String.valueOf(System.currentTimeMillis())) == null) {
      // For a target object that does not yet exist, set the DoesNotExist precondition.
      // This will cause the request to fail if the object is created before the request runs.
      precondition = Storage.BlobTargetOption.doesNotExist();
    } else {
      // If the destination already exists in your bucket, instead set a generation-match
      // precondition. This will cause the request to fail if the existing object's generation
      // changes before the request runs.
      precondition =
              Storage.BlobTargetOption.generationMatch(
                      storage.get("idcard-artist-bucket-1",
                              student.getStudentAdmissionNumber()+"-"+String.valueOf(System.currentTimeMillis())).getGeneration());
      //precondition = Storage.BlobTargetOption.generationNotMatch();

    }

    storage.create(blobInfo,
            file.getBytes(),
            precondition);

    DatastoreOptions datastoreOptions =
            DatastoreOptions.newBuilder()
                    .setProjectId("idcard-artist")
                    .setDatabaseId("idcard-artist-datastore")
                    .setNamespace("student")
                    .build();

    Datastore datastore = datastoreOptions.getService();

    String kind = "student";
    String id = UUID.randomUUID().toString();

    Key taskKey = datastore.newKeyFactory().setKind(kind).newKey(id);
    Entity task = Entity.newBuilder(taskKey)
            .set("student_name", student.getStudentName())
            .set("school_name", student.getStudentSchoolName())
            .set("guardian_name", student.getStudentGuardianName())
            .set("contact_number", student.getStudentContactNumber())
            .set("date_of_birth", student.getStudentDateOfBirth())
            .set("admission_no", student.getStudentAdmissionNumber())
            .set("class", student.getStudentClass())
            .set("division", student.getStudentDivision())
            .set("address_line1", student.getStudentAddressLine1())
            .set("address_line2", student.getStudentAddressLine2())
            .set("blood_group", student.getStudentBloodGroup())
            .build();
    datastore.put(task);
    Entity retrieved = datastore.get(taskKey);

    return StudentDto.builder()
            .studentName(retrieved.getString("student_name"))
            .studentSchoolName(retrieved.getString("school_name"))
            .studentGuardianName(retrieved.getString("guardian_name"))
            .studentContactNumber(retrieved.getString("contact_number"))
            .studentDateOfBirth(retrieved.getString("date_of_birth"))
            .studentAdmissionNumber(retrieved.getString("admission_no"))
            .studentClass(retrieved.getString("class"))
            .studentDivision(retrieved.getString("division"))
            .studentAddressLine1(retrieved.getString("address_line1"))
            .studentAddressLine2(retrieved.getString("address_line2"))
            .studentBloodGroup(retrieved.getString("blood_group"))
            .build();
  }
}
