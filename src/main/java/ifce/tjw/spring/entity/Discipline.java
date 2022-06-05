package ifce.tjw.spring.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table (name = "tbl_discipline")
public class Discipline {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "class_name")
	private String name;
	
	@OneToOne
	private User owner;
	
	@ManyToMany
	private List<User> user = new ArrayList<User>();
	
	@OneToMany
	private Collection<Activity> activities = new ArrayList<Activity>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<User> getUser() {
		return user;
	}

	public void setUser(List<User> students) {
		this.user = students;
	}

	public Long getId() {
		return id;
	}

	public Collection<Activity> getActivities() {
		return activities;
	}

	public void setActivities(Collection<Activity> activities) {
		this.activities = activities;
	}
}
