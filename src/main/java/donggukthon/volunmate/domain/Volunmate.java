package donggukthon.volunmate.domain;

import donggukthon.volunmate.type.EStatusType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "volunmate")
public class Volunmate {
    @Id
    @Column(name = "volunmate_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EStatusType status;

    // ============================= Many To One Relationship =============================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volun_id")
    Volunteer volunteer;

    @Builder
    public Volunmate(String username, String phoneNumber, String content, EStatusType status, Volunteer volunteer, User user) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.content = content;
        this.status = status;
        this.volunteer = volunteer;
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    public boolean updateStatus(EStatusType status) {
        this.volunteer.updateCurCount();
        this.status = status;
        return true;
    }
}
