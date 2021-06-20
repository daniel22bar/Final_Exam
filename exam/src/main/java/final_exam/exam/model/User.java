package final_exam.exam.model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    int id;
    String name;
    String lastName;
    String countryOfOrigin;
    String email;
}

