package org.carranza.msvc.gestion.ventas.api.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

public class PageUtil {

    public static Page<?> paginate(List<?> l, Pageable pageable, Long t) {
        return PageableExecutionUtils.getPage(l, pageable, () -> t);
    }
}
