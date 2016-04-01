package com.java.store.web.oauth.server.controller;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author ltao
 *
 */
@Controller
@RequestMapping("oauth2.0")
public class OauthAuthorizeController {

	private static final Logger LOG = LoggerFactory.getLogger(OauthAuthorizeController.class);
 
	public OauthAuthorizeController() {
		System.out.println("OauthAuthorizeController create .......");
	}
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("authorize")
    public Object authorize(HttpServletRequest request, HttpServletResponse response) throws Exception {

    	System.out.println("============================================");
        try {
            OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
            //生成授权码
            String authorizationCode = null;
            //responseType目前仅支持CODE，另外还有TOKEN
            String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
            if (responseType.equals(ResponseType.CODE.toString())) {
                OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
                authorizationCode = oauthIssuerImpl.authorizationCode();
                System.out.println(authorizationCode);
               // oAuthService.addAuthCode(authorizationCode, username);
            }
            //进行OAuth响应构建
            OAuthASResponse.OAuthAuthorizationResponseBuilder builder =
                    OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND);
            //设置授权码
            builder.setCode(authorizationCode);
            //得到到客户端重定向地址
            String redirectURI = oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);
            //构建响应
            final OAuthResponse resp = builder.location(redirectURI).buildQueryMessage();
            //根据OAuthResponse返回ResponseEntity响应
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(new URI(resp.getLocationUri()));
            return new ResponseEntity(headers, HttpStatus.valueOf(resp.getResponseStatus()));
        } catch (OAuthProblemException e) {
        	  //出错处理
            String redirectUri = e.getRedirectUri();
            if (OAuthUtils.isEmpty(redirectUri)) {
                //告诉客户端没有传入redirectUri直接报错
                return new ResponseEntity("OAuth callback url needs to be provided by client!!!", HttpStatus.NOT_FOUND);
            }
            //返回错误消息（如?error=）
            final OAuthResponse resp =
                    OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND)
                            .error(e).location(redirectUri).buildQueryMessage();
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(new URI(resp.getLocationUri()));
            return new ResponseEntity(headers, HttpStatus.valueOf(resp.getResponseStatus()));
        }


    }

//    private boolean checkAndHandleAuthHeaderUsed(HttpServletResponse response, OAuthAuthxRequest oauthRequest) throws OAuthSystemException {
//        if (oauthRequest.isClientAuthHeaderUsed()) {
//            OAuthResponse oAuthResponse = OAuthResponse.status(HttpServletResponse.SC_FOUND)
//                    .location(oauthRequest.getRedirectURI())
//                    .setParam("client_id", oauthRequest.getClientId())
//                    .buildJSONMessage();
//
//            LOG.debug("Auth header used by client: {}, return directly", oauthRequest.getClientId());
//            WebUtils.writeOAuthJsonResponse(response, oAuthResponse);
//            return true;
//        }
//        return false;
//    }

//    private void unsupportResponseType(OAuthAuthxRequest oauthRequest, HttpServletResponse response) throws OAuthSystemException {
//        final String responseType = oauthRequest.getResponseType();
//        LOG.debug("Unsupport response_type '{}' by client_id '{}'", responseType, oauthRequest.getClientId());
//
//        OAuthResponse oAuthResponse = OAuthResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
//                .setError(OAuthError.CodeResponse.UNSUPPORTED_RESPONSE_TYPE)
//                .setErrorDescription("Unsupport response_type '" + responseType + "'")
//                .buildJSONMessage();
//        WebUtils.writeOAuthJsonResponse(response, oAuthResponse);
//    }


  /*  @RequestMapping(value = "oauth_login")
    public String oauthLogin() {
        return "oauth/oauth_login";
    }


    @RequestMapping(value = "oauth_approval")
    public String oauthApproval() {
        return "oauth/oauth_approval";
    }*/
}
