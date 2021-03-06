package ifce.tjw.spring.dto;

import java.util.List;

public class ActivityCompleteDTO {
    Long id;
    String tittle;
    String description;
    String creatorName;
    DisciplineCreateDTO discipline;
    List<CommentCreatedDTO> comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public List<CommentCreatedDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentCreatedDTO> comments) {
        this.comments = comments;
    }

    public DisciplineCreateDTO getDiscipline() {
        return discipline;
    }

    public void setDiscipline(DisciplineCreateDTO discipline) {
        this.discipline = discipline;
    }
}
