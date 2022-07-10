package ifce.tjw.spring.dto;

import java.util.List;

public class DisciplineWithActivities extends DisciplineGetDTO {
    private List<ActivityCreatedDTO> list;

    public List<ActivityCreatedDTO> getList() {
        return list;
    }

    public void setList(List<ActivityCreatedDTO> list) {
        this.list = list;
    }
}
