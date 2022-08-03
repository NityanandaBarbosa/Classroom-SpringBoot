package ifce.tjw.spring.dto;

import java.util.List;

public class DisciplineGetDTO {
    private Long id;
    private String name;
    private String ownerName;
    private String section;
    private String room;
    private String subject;

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getOwnerName() {
        return ownerName;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    public String getSection() {
        return section;
    }
    public void setSection(String section) {
        this.section = section;
    }
    public String getRoom() {
        return room;
    }
    public void setRoom(String room) {
        this.room = room;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
}
