package ifce.tjw.spring.dto;

public class DisciplineCreateDTO {
	private Long id;
	private String name;
	private Long ownerId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getOwner_id() {
		return ownerId;
	}
	public void setOwner_id(Long owner_id) {
		this.ownerId = owner_id;
	}
	
}
