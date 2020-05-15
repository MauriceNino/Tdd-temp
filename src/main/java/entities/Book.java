package entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "BookId", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "BookId", sequenceName = "book_id")
    private Long id;

    private String name;
    private String author;
    private Date releaseDate;
}
