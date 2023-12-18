package donggukthon.volunmate.domain;

import donggukthon.volunmate.type.EStatusType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "volunmate")
public class Volunmate {
    @Id
    @Column(name = "volunmate_id")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;
}
