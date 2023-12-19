package donggukthon.volunmate.domain;

import donggukthon.volunmate.type.ELoginProvider;
import donggukthon.volunmate.type.EUserType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "social_id", unique = true)
    private String socialId;

    @Column(name = "user_name", nullable = true)
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    private EUserType userType;

    @Column(name = "provider")
    @Enumerated(EnumType.STRING)
    private ELoginProvider provider;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "is_login")
    private Boolean isLogin;

    @Column(name = "kakao_id", nullable = true)
    private String kakaoId;

    @Column(name = "degree")
    private Float degree;

    @Column(name = "lattitude")
    private Double lattitude;

    @Column(name = "longitude")
    private Double longitude;

    // ============================= One To Many Relationship =============================

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Volunteer> volunteers = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Volunmate> volunmates = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Help> helps = new ArrayList<>();

    // ====================================== Builder ======================================

    @Builder
    public User(String socialId, String password, EUserType userType, ELoginProvider provider
                ,double lattitude, double longitude) {
        this.socialId = socialId;
        this.password = password;
        this.userType = userType;
        this.provider = provider;
        this.createdAt = LocalDateTime.now();
        this.refreshToken = null;
        this.isLogin = false;
        this.degree = 36.5f;
        this.lattitude = lattitude;
        this.longitude = longitude;
    }

    public void updateRefreshToken(String refreshToken) {
        this.isLogin = true;
        this.refreshToken = refreshToken;
    }

    public void userSignUp(String userName, String kakaoId) {
        this.userName = userName;
        this.kakaoId = kakaoId;
        this.userType = EUserType.USER;
    }

    public void updateDegree(Float degree) {
        this.degree = degree;
    }

    public void logout() {
        this.isLogin = false;
        this.refreshToken = null;
    }

}
