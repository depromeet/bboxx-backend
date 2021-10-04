package bboxx.infrastructure.socialproviderfetcher.kakao;

//@DisplayName("KakaoFetcher")
//@ActiveProfiles("wiremock")
//@AutoConfigureWireMock(port = 0)
//@SpringBootTest(
//        classes = {FeignClientProperties.FeignClientConfiguration.class}
//)
//public class KakaoFetcherTest {
//
//
//
//    @Autowired
//    private KakaoUserClient client;
//
//    private final String url = "https://kapi.kakao.com/v2/user/me";
//
//    @Test
////    @Disabled("카카오 토큰이 평상시에 없기 때문에 스킵한다.")
//    void kakao_로그인에_성공하면_kakao_유저를_반환한다() {
//        stubFor(get(urlEqualTo("/resource")).willReturn(aResponse()
//                .withHeader("Content-Type", "text/plain").withBody("Hello World!")));
//        // We're asserting if WireMock responded properly
//        assertThat(this.service.go()).isEqualTo("Hello World!");
//
////        // given
////        KakaoFetcher kakaoFetcher = new KakaoFetcher(client);
////        mockServer.expect(requestTo(url))
////                .andRespond(withSuccess(new ClassPathResource("/kakao_user.json", getClass()), MediaType.APPLICATION_JSON));
////
////        // when
////        SocialProvider socialProvider = kakaoFetcher.fetch(SocialProviderType.KAKAO, "123");
//
//
//        // then
//    }
//}
