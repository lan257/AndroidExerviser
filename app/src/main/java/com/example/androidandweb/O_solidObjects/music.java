package com.example.androidandweb.O_solidObjects;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class music {
    UUID uid= UUID.randomUUID();
    String name;
    String src;
    LocalDate create=LocalDate.now();
    String author;
    @Override
    public String toString() {
        return "music{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", src='" + src + '\'' +
                ", create=" + create +
                ", author='" + author + '\'' +
                '}';
    }

    public music() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        music music = (music) o;
        return uid.equals(music.uid) && name.equals(music.name) && src.equals(music.src) && create.equals(music.create) && author.equals(music.author);
    }


    public music(String name, String src,  String author) {
        this.name = name;
        this.src = src;
        this.author = author;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public LocalDate getCreate() {
        return create;
    }

    public void setCreate(LocalDate create) {
        this.create = create;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public void update(music wen){
        this.name= wen.name==null ?this.name: wen.name;
        this.src= wen.src==null? this.src :wen.src;
        this.create= wen.create==null ?this.create: wen.create;
        this.author= wen.author==null ?this.author: wen.author;
    }
    public boolean select(music wen){
        if (this.uid==wen.uid){
            return true;
        }
        if (!Objects.equals(this.name, wen.name)&&wen.name!=null){
            return false;
        }
        if (!Objects.equals(this.src, wen.src)&&wen.src!=null){
            return false;
        }
        if (!Objects.equals(this.author, wen.author)&&wen.author!=null){
            return false;
        }
        return true;
    }
}
