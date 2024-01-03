package plus.maa.backend.filter;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class ParameterSnakeCaseToLowerCamelFilter extends OncePerRequestFilter {

    private static final Converter<String, String> snakeCaseToLowerCamelConverter = CaseFormat.LOWER_UNDERSCORE
            .converterTo(CaseFormat.LOWER_CAMEL);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var paramNames = request.getParameterNames();
        var extendParameter = new LinkedHashMap<String, String[]>();

        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();

            // 如果参数是 SnakeCase 则为参数添加小驼峰
            if (paramName.contains("_")) {
                String[] values = request.getParameterValues(paramName);
                if (values != null) {
                    extendParameter.put(snakeCaseToLowerCamelConverter.convert(paramName), values);
                }
            }
        }

        if (!extendParameter.isEmpty()) {
            request = new ExtendParameterRequest(request, extendParameter);
        }

        filterChain.doFilter(request, response);
    }

    private static class ExtendParameterRequest extends HttpServletRequestWrapper {

        private final Map<String, String[]> parameter;

        public ExtendParameterRequest(HttpServletRequest request, Map<String, String[]> extendParameter) {
            super(request);
            var parameter = new LinkedHashMap<String, String[]>();
            parameter.putAll(request.getParameterMap());
            parameter.putAll(extendParameter);
            this.parameter = Collections.unmodifiableMap(parameter);
        }

        @Override
        @Nullable
        public String getParameter(String name) {
            var values = parameter.get(name);
            if (values != null) {
                if (values.length == 0) {
                    return "";
                }
                return values[0];
            }
            return null;
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            return parameter;
        }

        @Override
        public Enumeration<String> getParameterNames() {
            return Collections.enumeration(parameter.keySet());
        }

        @Override
        @Nullable
        public String[] getParameterValues(String name) {
            return parameter.get(name);
        }
    }
}
