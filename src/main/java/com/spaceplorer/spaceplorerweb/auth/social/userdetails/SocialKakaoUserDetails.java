package com.spaceplorer.spaceplorerweb.auth.social.userdetails;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
//요청하지 않은 JSON데이터는 무시
@JsonIgnoreProperties(ignoreUnknown = true)
public class SocialKakaoUserDetails implements SocialUserDetails{

    private final Long socialId;
    private final String userName;
    private final String email;
    private final String phone;
    private final String thumbnail;
    private final String profileImage;
    private final String socialProvider = "kakao";

    //objectMapper용
        @JsonCreator
        public SocialKakaoUserDetails(
                @JsonProperty("id") Long socialId,
                @JsonProperty("kakao_account") KakaoAccount kakaoAccount) {
            this.socialId = socialId;
            //테스트 계정은 false여도 데이터는 들어온다. 임시로 둘다 데이터 담아놓게끔 하였다. 후에 수정해주기!!
            this.email = kakaoAccount.emailNeedsAgreement? kakaoAccount.email : kakaoAccount.email;
            this.phone = kakaoAccount.phoneNeedsAgreement? kakaoAccount.phone : kakaoAccount.phone;
            this.thumbnail = (kakaoAccount.profile != null)? kakaoAccount.profile.thumbnailImageUrl : null;
            this.profileImage = (kakaoAccount.profile != null)? kakaoAccount.profile.profileImageUrl : null;
            this.userName = this.email != null? getConvertUserName(email) : null;
        }

    private String getConvertUserName(String email) {
        int index = email.indexOf("@");
        return email.substring(0,index);
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class KakaoAccount {
            private final String email;
            private final String phone;
            private final boolean emailNeedsAgreement;
            private final boolean phoneNeedsAgreement;
            private final Profile profile;

            @JsonCreator
            public KakaoAccount(
                    @JsonProperty("email") String email,
                    @JsonProperty("phone_number") String phone,
                    @JsonProperty("email_needs_agreement") boolean emailNeedsAgreement,
                    @JsonProperty("phone_number_needs_agreement") boolean phoneNeedsAgreement,
                    @JsonProperty("profile") Profile profile) {
                this.email = email;
                this.phone = phone;
                this.emailNeedsAgreement = emailNeedsAgreement;
                this.phoneNeedsAgreement = phoneNeedsAgreement;
                this.profile = profile;
            }
        }
    @JsonIgnoreProperties(ignoreUnknown = true)
        private static class Profile {
            private final String thumbnailImageUrl;
            private final String profileImageUrl;

            @JsonCreator
            public Profile(
                    @JsonProperty("thumbnail_image_url") String thumbnailImageUrl,
                    @JsonProperty("profile_image_url") String profileImageUrl) {
                this.thumbnailImageUrl = thumbnailImageUrl;
                this.profileImageUrl = profileImageUrl;
            }
        }
    }
