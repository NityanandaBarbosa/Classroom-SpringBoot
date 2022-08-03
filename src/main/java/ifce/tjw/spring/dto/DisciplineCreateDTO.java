package ifce.tjw.spring.dto;

import javax.persistence.Column;

public class DisciplineCreateDTO {
	private Long id;
	private String name;
	private Long ownerId;
	private String section;
	private String room;
	private String subject;
	
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
	public Long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
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
