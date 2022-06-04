package ifce.tjw.spring.dto;

import java.util.ArrayList;
import java.util.List;

import ifce.tjw.spring.entity.Activity;
import ifce.tjw.spring.entity.User;

public class DisciplineDTO {
	private Long id;
	private String name;
	private String ownerName;
	private List<User> students = new ArrayList<User>();
	private List<Activity> activities = new ArrayList<Activity>();
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
	public List<User> getStudents() {
		return students;
	}
	public void setStudents(List<User> students) {
		this.students = students;
	}
	public List<Activity> getActivities() {
		return activities;
	}
	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
}
