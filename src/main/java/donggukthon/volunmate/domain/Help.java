package donggukthon.volunmate.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "help")
public class Help {
    @Id
    @Column(name = "help_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "emergency")
    private Boolean emergency;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // ============================= Many To One Relationship =============================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @Builder
    public Help(String title, String content, String imageUrl, Double latitude, Double longitude,
                Boolean emergency, User user) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.emergency = emergency;
        this.status = false;
        this.createdAt = LocalDateTime.now();
        this.user = user;
    }

    public void update(String title, String content, String imageUrl, Double latitude, Double longitude,
                       Boolean emergency) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.emergency = emergency;
    }

}
