package donggukthon.volunmate.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "volunteer")
public class Volunteer {
    @Id
    @Column(name = "volun_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "volun_count")
    private Integer volunCounet;

    @Column(name = "cur_count")
    private Integer curCount;

    @Column(name = "lattitude")
    private Float lattitude;

    @Column(name = "longitude")
    private Float longitude;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // ============================= One To Many Relationship =============================

    @OneToMany(mappedBy = "volunteer")
    private List<Volunmate> volunmates = new ArrayList<>();

    @OneToMany(mappedBy = "volunteer")
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "volunteer")
    private List<Tag> tags = new ArrayList<>();

    // ============================= Many To One Relationship =============================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
