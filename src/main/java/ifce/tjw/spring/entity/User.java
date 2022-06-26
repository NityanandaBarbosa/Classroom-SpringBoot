package ifce.tjw.spring.entity;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table (name = "tbl_user")
public class User {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column (name = "user_name")
	private String nome;
	
	@Column(unique = true, name = "user_email")
	private String  email; 
	
	@Column(name = "user_password")
	private String password;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
