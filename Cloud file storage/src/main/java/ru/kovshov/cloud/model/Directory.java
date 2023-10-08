package ru.kovshov.cloud.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "directory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Directory {
    @Id
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "hightDirectoryId")
    private int hightDirectoryId;
    @Column(name = "downDirectoryId")
    private int downDirectoryId;

    @Column(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private long userId;


    @Transient // не отражать в бд
    List<Integer> files = new ArrayList<>();

    public List<Integer> getListFiles(){
        String sql = "";
        Query query = session.
        // TODO write sql request
        return null;
    }




}
