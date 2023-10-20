package models;

public class Status {
    private Integer id;
    private String status;

    public Status(){}

    public Status(Integer id, String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString(){
        return """
            Status: {
               "id": %d,
               "status" : %s
            }    
        """.formatted(this.id, this.status);
    }
}