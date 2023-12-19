package donggukthon.volunmate.interceptor.pre;

import donggukthon.volunmate.annotation.SocialId;
import donggukthon.volunmate.constant.Constants;
import donggukthon.volunmate.exception.CustomException;
import donggukthon.volunmate.exception.ErrorCode;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class SocialIdArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(String.class)
                && parameter.hasParameterAnnotation(SocialId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        final Object socialId = webRequest.getAttribute(Constants.SOCIAL_ID_CLAIM_NAME, WebRequest.SCOPE_REQUEST);

        if (socialId == null) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }
        return socialId.toString();
    }
}
