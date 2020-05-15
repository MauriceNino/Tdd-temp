package entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "removed_book")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RemovedBook implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "RemovedBookId", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "RemovedBookId", sequenceName = "removed_book_id")
    private Long id;

    private String name;
    private String author;
    private Date releaseDate;
}
