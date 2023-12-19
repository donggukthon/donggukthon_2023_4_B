package donggukthon.volunmate.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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
    private Integer volunCount;

    @Column(name = "cur_count")
    private Integer curCount;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // ============================= One To Many Relationship =============================

    @OneToMany(mappedBy = "volunteer", cascade = CascadeType.ALL)
    private List<Volunmate> volunmates = new ArrayList<>();

    @OneToMany(mappedBy = "volunteer", cascade = CascadeType.ALL)
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "volunteer", cascade = CascadeType.ALL)
    private List<Tag> tags = new ArrayList<>();

    @Builder
    public Volunteer(String title, String content, String imageUrl, Double latitude, Double longitude,List<Tag> tags,
                     Integer volunCount, LocalDateTime startDate, LocalDateTime endDate,LocalDateTime dueDate, User user) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.volunCount = volunCount;
        this.curCount = 0;
        this.latitude = latitude;
        this.longitude = longitude;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dueDate = dueDate;
        this.user = user;
        this.tags = tags;
        this.createdAt = LocalDateTime.now();
    }
// ============================= Many To One Relationship =============================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void update (String title, String content, String imageUrl, Double latitude, Double longitude,
                        Integer volunCount, LocalDateTime startDate, LocalDateTime endDate,LocalDateTime dueDate) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.volunCount = volunCount;
        this.latitude = latitude;
        this.longitude = longitude;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dueDate = dueDate;
    }

    public void updateCurCount() {
        this.curCount++;
    }
}
