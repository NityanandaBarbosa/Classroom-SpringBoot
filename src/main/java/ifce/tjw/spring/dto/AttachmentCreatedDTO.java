package ifce.tjw.spring.dto;

public class AttachmentCreatedDTO {
    Long id;
    String activity;
    String sender;
    String fileName;
    String fileType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String senderName) {
        this.sender = senderName;
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

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
