package com.spaceplorer.spaceplorerweb.filter;

/*@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter  {

    private final TokenUtil tokenUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("method=attemptAuthentication");
        String socialToken_ = request.getHeader(TokenUtil.AUTHORIZATION_HEADER);
        if (tokenUtil.isPresent(socialToken_)) {
            String socialToken = tokenUtil.substringToken(socialToken_);
            log.info("method=attemptAuthentication socialToken={}", socialToken);
        }
        log.error("method=attemptAuthentication");


        return null;
    }
}*/

