package ifce.tjw.spring.dto;

import java.util.List;

public class DisciplineGetDTO {
    private Long id;
    private String name;
    private String ownerName;

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
}
