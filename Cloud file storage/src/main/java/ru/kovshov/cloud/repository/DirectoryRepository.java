package ru.kovshov.cloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kovshov.cloud.model.Directory;
import ru.kovshov.cloud.model.File;

import java.util.List;

public interface DirectoryRepository extends JpaRepository<Directory, Long> {

    @Query("from File file where file.directoryId = :id")
    List<File> listFiles(@Param("id")Long id); //получение файлов по id directory
}
