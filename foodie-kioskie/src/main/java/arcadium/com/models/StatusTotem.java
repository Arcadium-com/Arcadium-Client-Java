package arcadium.com.models;

import java.util.ArrayList;
import java.util.List;

public class StatusTotem {
    private Integer id;
    private String status;
    private List<Totem> totens;

    public StatusTotem(){}

    public StatusTotem(Integer id, String status) {
        this.id = id;
        this.status = status;
        this.totens = new ArrayList<>();
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
        Id: %d
        Status: %s
        """.formatted(
        this.id,
        this.status
        );
    }
}