package ifce.tjw.spring.entity;

import java.io.IOException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.io.FilenameUtils;
import ifce.tjw.spring.utils.ReadLob;

@Entity
@Table (name = "tbl_attachment")
public class Attachment {
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private Activity activity;
	
	@OneToOne
	private User user;
	
	@Column
	private String fileName;
	
	@Column
	private String fileType;

	@Column
	private String fileNameDB;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public String getFileNameDB() {
		return fileNameDB;
	}

	public void setFileNameDB(String fileNameDB) {
		this.fileNameDB = fileNameDB;
	}

	public void setFileType(String path) throws IOException {
		this.fileType = FilenameUtils.getExtension(path);
	}
		
}
