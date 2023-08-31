package com.team.gallexiv.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "captcha_info")
public class CaptchaInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer captcha_id;
    @Column(nullable = false, name = "key")
    private String captcha_key;
    @Column(nullable = false, name = "code")
    private String captcha_code;

    @Override
    public String toString() {
        return "CaptchaInfo{" +
                "captcha_key='" + captcha_key + '\'' +
                ", captcha_code='" + captcha_code + '\'' +
                '}';
    }

    public CaptchaInfo() {
    }

    public CaptchaInfo(String captcha_key, String captcha_code) {
        this.captcha_key = captcha_key;
        this.captcha_code = captcha_code;
    }
}
