package com.example.usersData;


import javax.persistence.*;

@Entity
public class FileUpload {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String name;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

  //  private boolean security;
   // private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
